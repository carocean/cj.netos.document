package cj.netos.document.services;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.document.IGeoCategoryService;
import cj.netos.document.IGeoReceptorService;
import cj.netos.document.openports.entities.GeoObjectResponse;
import cj.netos.document.openports.entities.LatLng;
import cj.netos.document.openports.entities.geo.GeoObserver;
import cj.netos.document.openports.entities.geo.GeoReceptor;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.ultimate.gson2.com.google.gson.Gson;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

@CjService(name = "geoReceptorService")
public class DefaultGeoReceptorService implements IGeoReceptorService {
    @CjServiceRef(refByName = "mongodb.netos.home")
    ICube home;
    @CjServiceRef
    IGeoCategoryService geoCategoryService;

    String _getColName(String category) {
        return String.format("geo.receptor.%s", category);
    }

    @Override
    public boolean exists(String category, String id) {
        return home.tupleCount(_getColName(category), String.format("{'tuple.id':'%s'}", id)) > 0;
    }

    @Override
    public void add(String category, GeoReceptor receptor) {
        home.saveDoc(_getColName(category), new TupleDocument<>(receptor));
    }

    @Override
    public void remove(String category, String id) {
        home.deleteDocOne(_getColName(category), String.format("{'tuple.id':'%s'}", id));
    }

    @Override
    public GeoReceptor get(String category, String id) {
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple ?(colname) ?(clazz) where {'tuple.id':'?(id)'}");
        IQuery<GeoReceptor> query = home.createQuery(cjql);
        query.setParameter("colname", _getColName(category));
        query.setParameter("clazz", GeoReceptor.class.getName());
        query.setParameter("id", id);
        IDocument<GeoReceptor> doc = query.getSingleResult();
        if (doc == null) {
            return null;
        }
        return doc.tuple();
    }

    @Override
    public void updateLocation(String creator,String category, String id, LatLng location) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s','tuple.category':'%s','tuple.creator':'%s'}", id, category, creator));
        Bson update = Document.parse(String.format("{'$set':{'tuple.location':%s}}", new Gson().toJson(location)));
        home.updateDocOne(_getColName(category), filter, update);
    }

    @Override
    public void updateRadius(String creator,String category, String id, double radius) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s','tuple.category':'%s','tuple.creator':'%s'}", id, category, creator));
        Bson update = Document.parse(String.format("{'$set':{'tuple.radius':'%s'}}", radius));
        home.updateDocOne(_getColName(category), filter, update);
    }

    @Override
    public void updateLeading(String creator, String category, String id, String leading) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s','tuple.category':'%s','tuple.creator':'%s'}", id, category, creator));
        Bson update = Document.parse(String.format("{'$set':{'tuple.leading':'%s'}}", leading));
        home.updateDocOne(_getColName(category), filter, update);
    }

    @Override
    public GeoReceptor getMobileGeoReceptor(String person, String device) {
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple ?(colname) ?(clazz) where {'tuple.entity.person':'?(person)','tuple.entity.device':'?(device)'}");
        IQuery<GeoReceptor> query = home.createQuery(cjql);
        query.setParameter("colname", _getColName("mobiles"));
        query.setParameter("clazz", GeoReceptor.class.getName());
        query.setParameter("person", person);
        query.setParameter("device", device);
        IDocument<GeoReceptor> doc = query.getSingleResult();
        if (doc == null) {
            return null;
        }
        return doc.tuple();
    }

    @Override
    public void updateMobileLocation(String person, String device, LatLng location) {
        Bson filter = Document.parse(String.format("{'tuple.entity.person':'%s','tuple.entity.device':'%s'}", person, device));
        Bson update = Document.parse(String.format("{'$set':{'tuple.location':%s}}", new Gson().toJson(location)));
        home.updateDocOne(_getColName("mobiles"), filter, update);
    }

    @Override
    public void updateMobileRadius(String person, String device, double radius) {
        Bson filter = Document.parse(String.format("{'tuple.entity.person':'%s','tuple.entity.device':'%s'}", person, device));
        Bson update = Document.parse(String.format("{'$set':{'tuple.radius':'%s'}}", radius));
        home.updateDocOne(_getColName("mobiles"), filter, update);
    }

    @Override
    public void addObserver(String id, String category, String observer) {
        String colname = String.format("%s.observers", _getColName(category));
        GeoObserver geoObserver = new GeoObserver();
        geoObserver.setObserver(observer);
        geoObserver.setReceptor(id);
        geoObserver.setCtime(System.currentTimeMillis());
        home.saveDoc(colname, new TupleDocument<>(geoObserver));
    }

    @Override
    public void removeObserver(String id, String category, String observer) {
        String colname = String.format("%s.observers", _getColName(category));
        home.deleteDocOne(colname, String.format("{'tuple.receptor':'%s','tuple.observer':'%s'}", id, observer));
    }

    @Override
    public List<GeoObserver> pageObserver(String id, String category, long limit, long offset) {
        String colname = String.format("%s.observers", _getColName(category));
        String cjql = String.format("select {'tuple':'*'}.limit(?(limit)).skip(?(skip)) from tuple ?(colname) ?(clazz) where {'tuple.receptor':'?(receptor)'}");
        IQuery<GeoObserver> query = home.createQuery(cjql);
        query.setParameter("colname", colname);
        query.setParameter("clazz", GeoObserver.class.getName());
        query.setParameter("receptor", id);
        query.setParameter("limit", limit);
        query.setParameter("skip", offset);
        List<IDocument<GeoObserver>> docs = query.getResultList();
        List<GeoObserver> list = new ArrayList<>();
        for (IDocument<GeoObserver> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    //https://blog.csdn.net/varyall/article/details/80308426
    @Override
    public List<GeoObjectResponse> recept(String category, long limit, long offset) {
        //$geoNear,设需要以当前坐标为原点，查询附近指定范围内的餐厅，并直接显示距离
//        这个需求用前面提到的$near是可以实现的，但是距离需要二次计算。这里我们用$geoNear这个命令查询。
//        $geoNear与$near功能类似，但提供更多功能和返回更多信息，官方文档是这么解释的：

        return null;
    }
}
