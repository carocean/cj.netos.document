package cj.netos.document.services;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.document.AbstractService;
import cj.netos.document.IGeoCategoryService;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoCategoryApp;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

@CjService(name = "geoCategoryService")
public class DefaultGeoCategoryService extends AbstractService implements IGeoCategoryService {
    final static String _KEY_CATEGORY_COL = "geo.categories";
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
        GeoCategory[] arr= cache.values().toArray(new GeoCategory[0]);
        Arrays.sort(arr,new DefaultComparator());
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
