package cj.netos.document.services;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.document.AbstractService;
import cj.netos.document.IGeoCategoryService;
import cj.netos.document.openports.entities.bo.GeoCategoryBO;
import cj.netos.document.openports.entities.bo.GeoChannelBO;
import cj.netos.document.openports.entities.geo.GeoBrand;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoCategoryApp;
import cj.netos.document.openports.entities.geo.GeoChannel;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.openport.util.Encript;
import cj.ultimate.util.StringUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

@CjService(name = "geoCategoryService")
public class DefaultGeoCategoryService extends AbstractService implements IGeoCategoryService {
    final static String _KEY_CHANNEL_COL = "geo.channels";
    final static String _KEY_CATEGORY_COL = "geo.categories";
    final static String _KEY_BRAND_COL = "geo.brands";
    final static String _KEY_CATEGORY_APP_CLIENT_COL = "geo.app.onserved";
    final static String _KEY_CATEGORY_APP_SERVER_COL = "geo.app.onservice";
    @CjServiceRef(refByName = "mongodb.netos.home")
    ICube home;
    Map<String, GeoCategory> cache;

    @Override
    public boolean exists(String id) {
        if (cache.containsKey(id)) {
            return true;
        }
        return home.tupleCount(_KEY_CATEGORY_COL, String.format("{'tuple.id':'%s'}", id)) > 0;
    }

    @Override
    public void add(GeoCategory category) {
        home.saveDoc(_KEY_CATEGORY_COL, new TupleDocument<>(category));
        cache.put(category.getId(), category);
    }

    @Override
    public void remove(String id) {
        home.deleteDocOne(_KEY_CATEGORY_COL, String.format("{'tuple.id':'%s'}", id));
        cache.remove(id);
    }

    @Override
    public GeoCategory get(String id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple ?(colname) ?(clazz) where {'tuple.id':'?(id)'}");
        IQuery<GeoCategory> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_CATEGORY_COL);
        query.setParameter("clazz", GeoCategory.class.getName());
        query.setParameter("id", id);
        IDocument<GeoCategory> doc = query.getSingleResult();
        if (doc == null) {
            return null;
        }
        return doc.tuple();
    }

    @Override
    public GeoCategory[] listCategory() {
        GeoCategory[] arr = cache.values().toArray(new GeoCategory[0]);
        Arrays.sort(arr, new DefaultComparator());
        return arr;
    }

    @Override
    public void updateCategoryTitle(String id, String title) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s'}", id));
        Bson update = Document.parse(String.format("{'$set':{'tuple.title':'%s'}}", title));
        home.updateDocOne(_KEY_CATEGORY_COL, filter, update);
        GeoCategory category = cache.get(id);
        if (category != null) {
            category.setTitle(title);
        }
    }

    void loadCache() {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {}");
        IQuery<GeoCategory> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_CATEGORY_COL);
        query.setParameter("clazz", GeoCategory.class.getName());
        List<IDocument<GeoCategory>> docs = query.getResultList();
        for (IDocument<GeoCategory> doc : docs) {
            cache.put(doc.tuple().getId(), doc.tuple());
        }
    }

    @Override
    public void reloadCategories() {
        cache = new TreeMap();
        loadCache();
    }

    @Override
    public boolean existsApp(String on, String category, String id) {
        switch (on) {
            case "onservice":
                return home.tupleCount(_KEY_CATEGORY_APP_SERVER_COL, String.format("{'tuple.id':'%s','tuple.category':'%s'}", id, category)) > 0;
            case "onserved":
                return home.tupleCount(_KEY_CATEGORY_APP_CLIENT_COL, String.format("{'tuple.id':'%s','tuple.category':'%s'}", id, category)) > 0;
        }
        return false;
    }

    @Override
    public void addGeoCategoryApp(String on, GeoCategoryApp app) {
        switch (on) {
            case "onservice":
                home.saveDoc(_KEY_CATEGORY_APP_SERVER_COL, new TupleDocument<>(app));
                break;
            case "onserved":
                home.saveDoc(_KEY_CATEGORY_APP_CLIENT_COL, new TupleDocument<>(app));
                break;
        }
    }

    @Override
    public void removeGeoCategoryApp(String on, String category, String id) {
        switch (on) {
            case "onservice":
                home.deleteDocOne(_KEY_CATEGORY_APP_SERVER_COL, String.format("{'tuple.id':'%s','tuple.category':'%s'}", id, category));
                break;
            case "onserved":
                home.deleteDocOne(_KEY_CATEGORY_APP_CLIENT_COL, String.format("{'tuple.id':'%s','tuple.category':'%s'}", id, category));
                break;
        }
    }

    @Override
    public List<GeoCategoryApp> listGeoCategoryApp(String on, String category) {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {'tuple.category':'?(category)'}");
        IQuery<GeoCategoryApp> query = home.createQuery(cjql);
        query.setParameter("clazz", GeoCategoryApp.class.getName());
        query.setParameter("category", category);
        switch (on) {
            case "onservice":
                query.setParameter("colname", _KEY_CATEGORY_APP_SERVER_COL);
                break;
            case "onserved":
                query.setParameter("colname", _KEY_CATEGORY_APP_CLIENT_COL);
                break;
        }

        List<IDocument<GeoCategoryApp>> docs = query.getResultList();
        List<GeoCategoryApp> list = new ArrayList<>();
        for (IDocument<GeoCategoryApp> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    @Override
    public void config(String creator, List<GeoChannelBO> channels) {
        removeAllChannel();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
// UPPERCASE：大写 (ZHONG)
// LOWERCASE：小写 (zhong)
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
// WITHOUT_TONE：无音标 (zhong)
// WITH_TONE_NUMBER：1-4数字表示英标 (zhong4)
// WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常） (zhòng)
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
// WITH_V：用v表示ü (nv)
// WITH_U_AND_COLON：用"u:"表示ü (nu:)
// WITH_U_UNICODE：直接用ü (nü)
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);

        for (GeoChannelBO channelBo : channels) {
            List<GeoCategoryBO> categoryBOS = channelBo.getCategories();
            for (GeoCategoryBO categoryBO : categoryBOS) {
                categoryBO.setChannel(channelBo.getId());
                categoryBO.setCtime(System.currentTimeMillis());
                categoryBO.setCreator(creator);
                categoryBO.setDefaultRadius(categoryBO.getDefaultRadius() == 0 ? 500 : categoryBO.getDefaultRadius());
                if (StringUtil.isEmpty(categoryBO.getId())) {
                    String cate_pinyin = "";
                    try {
                        cate_pinyin = PinyinHelper.toHanYuPinyinString(categoryBO.getTitle(), format, "", false);
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                    String cate_id = Encript.md5(String.format("%s/%s", channelBo.getId(), cate_pinyin));
                    categoryBO.setId(cate_id);
                }
                List<GeoBrand> brands = categoryBO.getBrands();
                for (GeoBrand brand : brands) {
                    brand.setCategory(categoryBO.getId());
                    brand.setChannel(channelBo.getId());
                    if (StringUtil.isEmpty(brand.getId())) {
                        String br_pinyin = "";
                        try {
                            br_pinyin = PinyinHelper.toHanYuPinyinString(brand.getTitle(), format, "", false);
                        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                            badHanyuPinyinOutputFormatCombination.printStackTrace();
                        }
                        String br_id = Encript.md5(String.format("%s/%s/%s", channelBo.getId(), categoryBO.getId(), br_pinyin));
                        brand.setId(br_id);
                    }
                    home.saveDoc(_KEY_BRAND_COL, new TupleDocument<>(brand));
                }
                GeoCategory category = categoryBO.toCategory();
                home.saveDoc(_KEY_CATEGORY_COL, new TupleDocument<>(category));
            }
            if (StringUtil.isEmpty(channelBo.getId())) {
                String ch_pinyin = "";
                try {
                    ch_pinyin = PinyinHelper.toHanYuPinyinString(channelBo.getTitle(), format, "", false);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
                String ch_id = Encript.md5(String.format("%s", ch_pinyin));
                channelBo.setId(ch_id);
            }
            GeoChannel channel = channelBo.toChannel();
            home.saveDoc(_KEY_CHANNEL_COL, new TupleDocument<>(channel));
        }
        reloadCategories();
    }

    private void removeAllChannel() {
        home.dropTuple(_KEY_CHANNEL_COL);
        home.dropTuple(_KEY_CATEGORY_COL);
        home.dropTuple(_KEY_BRAND_COL);
    }

    @Override
    public List<GeoChannel> listChannel() {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {}");
        IQuery<GeoChannel> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_CHANNEL_COL);
        query.setParameter("clazz", GeoChannel.class.getName());
        List<IDocument<GeoChannel>> docs = query.getResultList();
        List<GeoChannel> channels = new ArrayList<>();
        for (IDocument<GeoChannel> document : docs) {
            channels.add(document.tuple());
        }
        return channels;
    }

    @Override
    public List<GeoCategory> listCategoryOf(String channel) {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {'tuple.channel':'%s'}", channel);
        IQuery<GeoCategory> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_CATEGORY_COL);
        query.setParameter("clazz", GeoCategory.class.getName());
        List<IDocument<GeoCategory>> docs = query.getResultList();
        List<GeoCategory> categories = new ArrayList<>();
        for (IDocument<GeoCategory> document : docs) {
            categories.add(document.tuple());
        }
        return categories;
    }

    @Override
    public List<GeoBrand> listBrandBy(String channel, String category) {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {'tuple.channel':'%s','tuple.category':'%s'}", channel, category);
        IQuery<GeoBrand> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_BRAND_COL);
        query.setParameter("clazz", GeoBrand.class.getName());
        List<IDocument<GeoBrand>> docs = query.getResultList();
        List<GeoBrand> categories = new ArrayList<>();
        for (IDocument<GeoBrand> document : docs) {
            categories.add(document.tuple());
        }
        return categories;
    }

    @Override
    public List<GeoBrand> listBrandByCategory(String category) {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {'tuple.category':'%s'}", category);
        IQuery<GeoBrand> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_BRAND_COL);
        query.setParameter("clazz", GeoBrand.class.getName());
        List<IDocument<GeoBrand>> docs = query.getResultList();
        List<GeoBrand> categories = new ArrayList<>();
        for (IDocument<GeoBrand> document : docs) {
            categories.add(document.tuple());
        }
        return categories;
    }

    @Override
    public List<GeoBrand> listBrandByChannel(String channel) {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {'tuple.channel':'%s'}", channel);
        IQuery<GeoBrand> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_BRAND_COL);
        query.setParameter("clazz", GeoBrand.class.getName());
        List<IDocument<GeoBrand>> docs = query.getResultList();
        List<GeoBrand> categories = new ArrayList<>();
        for (IDocument<GeoBrand> document : docs) {
            categories.add(document.tuple());
        }
        return categories;
    }

    @Override
    public List<GeoCategory> listHotCategories() {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {'tuple.isHot':true}");
        IQuery<GeoCategory> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_CATEGORY_COL);
        query.setParameter("clazz", GeoCategory.class.getName());
        List<IDocument<GeoCategory>> docs = query.getResultList();
        List<GeoCategory> categories = new ArrayList<>();
        for (IDocument<GeoCategory> document : docs) {
            categories.add(document.tuple());
        }
        return categories;
    }

    @Override
    public List<GeoBrand> listHotBrands() {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {'tuple.isHot':true}");
        IQuery<GeoBrand> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_BRAND_COL);
        query.setParameter("clazz", GeoBrand.class.getName());
        List<IDocument<GeoBrand>> docs = query.getResultList();
        List<GeoBrand> categories = new ArrayList<>();
        for (IDocument<GeoBrand> document : docs) {
            categories.add(document.tuple());
        }
        return categories;
    }

    @Override
    public List<GeoCategory> listAllCategory() {
        String cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {}");
        IQuery<GeoCategory> query = home.createQuery(cjql);
        query.setParameter("colname", _KEY_CATEGORY_COL);
        query.setParameter("clazz", GeoCategory.class.getName());
        List<IDocument<GeoCategory>> docs = query.getResultList();
        List<GeoCategory> categories = new ArrayList<>();
        for (IDocument<GeoCategory> document : docs) {
            categories.add(document.tuple());
        }
        return categories;
    }

    class DefaultComparator implements Comparator<GeoCategory> {

        @Override
        public int compare(GeoCategory o1, GeoCategory o2) {
            if (o1.getSort() == o2.getSort()) {
                return 0;
            }
            return o1.getSort() > o2.getSort() ? 1 : -1;
        }
    }
}
