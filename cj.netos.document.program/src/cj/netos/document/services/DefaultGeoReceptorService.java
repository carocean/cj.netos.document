package cj.netos.document.services;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.document.IGeoCategoryService;
import cj.netos.document.IGeoReceptorService;
import cj.netos.document.openports.entities.BackgroundMode;
import cj.netos.document.openports.entities.ForegroundMode;
import cj.netos.document.openports.entities.LatLng;
import cj.netos.document.openports.entities.geo.*;
import cj.netos.document.openports.entities.netflow.GeosphereMedia;
import cj.studio.ecm.CJSystem;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.ultimate.gson2.com.google.gson.Gson;
import com.mongodb.client.ListIndexesIterable;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CjService(name = "geoReceptorService")
public class DefaultGeoReceptorService implements IGeoReceptorService {
    @CjServiceRef(refByName = "mongodb.netos.home")
    ICube home;
    @CjServiceRef
    IGeoCategoryService geoCategoryService;

    String _getReceptorColName() {
        return String.format("geo.receptors");
    }

    String _getDocumentColName() {
        return String.format("geo.receptor.docs");
    }

    String _getLikeColName() {
        return String.format("geo.receptor.likes");
    }

    String _getCommentColName() {
        return String.format("geo.receptor.comments");
    }

    String _getFollowColName() {
        return String.format("geo.receptor.follows");
    }

    String _getMediaColName() {
        return String.format("geo.receptor.medias");
    }

    @Override
    public void createGeoIndex() {
        //为感知器和文档创建地理索引
        ListIndexesIterable<Document> itReceptorIndexes = home.listIndexes(_getReceptorColName());
        boolean hasIndex = false;
        for (Document doc : itReceptorIndexes) {
            //是否存在索引，如果不存在则建立
            Map map = (Map) doc.get("key");
            Object v = map.get("tuple.location");
//                System.out.println(doc);
            if (v != null) {
//                    home.dropIndex(_getReceptorColName(category.getId()), Document.parse(String.format("{'tuple.location':1}")));
                CJSystem.logging().info(getClass(), String.format("发现感知器<%s>的索引：tuple.location=2dsphere", _getReceptorColName()));
                hasIndex = true;
                break;
            }
        }
        if (!hasIndex) {
            home.createIndex(_getReceptorColName(), Document.parse(String.format("{'tuple.location':'2dsphere'}")));
            CJSystem.logging().info(getClass(), String.format("已为感知器<%s>创建索引：tuple.location=2dsphere", _getReceptorColName()));
        }
        hasIndex = false;
        ListIndexesIterable<Document> itDocumentIndexes = home.listIndexes(_getDocumentColName());
        for (Document doc : itDocumentIndexes) {
            //是否存在索引，如果不存在则建立
            Map map = (Map) doc.get("key");
            Object v = map.get("tuple.location");
//                System.out.println(doc);
            if (v != null) {
//                    home.dropIndex(_getReceptorColName(category.getId()), Document.parse(String.format("{'tuple.location':1}")));
                CJSystem.logging().info(getClass(), String.format("发现文档<%s>的索引：tuple.location=2dsphere", _getDocumentColName()));
                hasIndex = true;
                break;
            }
        }
        if (!hasIndex) {
            home.createIndex(_getDocumentColName(), Document.parse(String.format("{'tuple.location':'2dsphere'}")));
            CJSystem.logging().info(getClass(), String.format("已为文档<%s>创建索引：tuple.location=2dsphere", _getDocumentColName()));
        }
    }

    @Override
    public void emptyCategory(GeoCategory category) {
        home.dropTuple(String.format(_getReceptorColName()));
        home.dropTuple(String.format(_getDocumentColName()));
        home.dropTuple(String.format(_getCommentColName()));
        home.dropTuple(String.format(_getLikeColName()));
        home.dropTuple(String.format(_getFollowColName()));
        home.dropTuple(String.format(_getMediaColName()));
    }

    @Override
    public boolean exists(String id) {
        return home.tupleCount(_getReceptorColName(), String.format("{'tuple.id':'%s'}", id)) > 0;
    }

    @Override
    public boolean existsTitleOnTownCode(String title, String townCode) {
        return home.tupleCount(_getReceptorColName(), String.format("{'tuple.title':'%s','tuple.townCode':'%s'}", title, townCode)) > 0;
    }

    @Override
    public void add(GeoReceptor receptor) {
        home.saveDoc(_getReceptorColName(), new TupleDocument<>(receptor));
    }

    @Override
    public void remove(String principal, String id) {
//        home.deleteDocOne(_getReceptorColName(), String.format("{'tuple.id':'%s','tuple.creator':'%s'}", id, principal));
        home.updateDocOne(_getReceptorColName(),
                Document.parse(String.format("{'tuple.id':'%s','tuple.creator':'%s'}", id, principal)),
                Document.parse("{'$set':{'tuple.delFlag':1}}"));
    }

    @Override
    public void recoverReceptor(String principal, String id) {
        home.updateDocOne(_getReceptorColName(),
                Document.parse(String.format("{'tuple.id':'%s','tuple.creator':'%s'}", id, principal)),
                Document.parse("{'$set':{'tuple.delFlag':0}}"));
    }
    @Override
    public GeoReceptor get(String id) {
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple ?(colname) ?(clazz) where {'tuple.id':'?(id)'}");
        IQuery<GeoReceptor> query = home.createQuery(cjql);
        query.setParameter("colname", _getReceptorColName());
        query.setParameter("clazz", GeoReceptor.class.getName());
        query.setParameter("id", id);
        IDocument<GeoReceptor> doc = query.getSingleResult();
        if (doc == null) {
            return null;
        }
        return doc.tuple();
    }

    @Override
    public List<GeoReceptor> pageMyDeletedReceptor(String principal, long limit, long skip) {
        String cjql;
        IQuery<GeoReceptor> query;
        cjql = String.format("select {'tuple':'*'}.limit(%s).skip(%s) from tuple ?(colname) ?(clazz) where {'tuple.creator':'?(creator)','tuple.delFlag':1}",limit,skip);
        query = home.createQuery(cjql);
        query.setParameter("colname", _getReceptorColName());
        query.setParameter("clazz", GeoReceptor.class.getName());
        query.setParameter("creator", principal);
        List<IDocument<GeoReceptor>> docs = query.getResultList();
        List<GeoReceptor> list = new ArrayList<>();
        for (IDocument<GeoReceptor> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }



    @Override
    public List<GeoReceptor> getAllMyReceptor(String person) {
        List<GeoReceptor> receptors = new ArrayList<>();
        List<GeoReceptor> list = _listReceptor(person);
        if (!list.isEmpty()) {
            receptors.addAll(list);
        }
        return receptors;
    }

    @Override
    public List<GeosphereDocument> pageDocument(String receptor, String creator, long limit, long skip) {
        String cjql = String.format("select {'tuple':'*'}.sort({'tuple.ctime':-1}).limit(%s).skip(%s) from tuple ?(colname) ?(clazz) where {'tuple.receptor':'?(receptor)','tuple.creator':'?(creator)'}", limit, skip);
        IQuery<GeosphereDocument> query = home.createQuery(cjql);
        query.setParameter("colname", _getDocumentColName());
        query.setParameter("clazz", GeosphereDocument.class.getName());
        query.setParameter("receptor", receptor);
        query.setParameter("creator", creator);
        List<IDocument<GeosphereDocument>> docs = query.getResultList();
        List<GeosphereDocument> list = new ArrayList<>();
        for (IDocument<GeosphereDocument> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    @Override
    public List<GeosphereDocument> pageDocument2(String receptor, long limit, long skip) {
        String cjql = String.format("select {'tuple':'*'}.sort({'tuple.ctime':-1}).limit(%s).skip(%s) from tuple ?(colname) ?(clazz) where {'tuple.receptor':'?(receptor)'}", limit, skip);
        IQuery<GeosphereDocument> query = home.createQuery(cjql);
        query.setParameter("colname", _getDocumentColName());
        query.setParameter("clazz", GeosphereDocument.class.getName());
        query.setParameter("receptor", receptor);
        List<IDocument<GeosphereDocument>> docs = query.getResultList();
        List<GeosphereDocument> list = new ArrayList<>();
        for (IDocument<GeosphereDocument> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    private List<GeoReceptor> _listReceptor(String person) {
        String cjql;
        IQuery<GeoReceptor> query;
        cjql = String.format("select {'tuple':'*'} from tuple ?(colname) ?(clazz) where {'tuple.creator':'?(creator)','tuple.delFlag':{'$ne':1}}");
        query = home.createQuery(cjql);
        query.setParameter("colname", _getReceptorColName());
        query.setParameter("clazz", GeoReceptor.class.getName());
        query.setParameter("creator", person);
        List<IDocument<GeoReceptor>> docs = query.getResultList();
        List<GeoReceptor> list = new ArrayList<>();
        for (IDocument<GeoReceptor> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    @Override
    public void updateLocation(String creator, String id, LatLng location) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s','tuple.creator':'%s'}", id, creator));
        Bson update = Document.parse(String.format("{'$set':{'tuple.location':%s}}", new Gson().toJson(location)));
        home.updateDocOne(_getReceptorColName(), filter, update);
    }

    @Override
    public void updateRadius(String creator, String id, double radius) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s','tuple.creator':'%s'}", id, creator));
        Bson update = Document.parse(String.format("{'$set':{'tuple.radius':'%s'}}", radius));
        home.updateDocOne(_getReceptorColName(), filter, update);
    }

    @Override
    public void updateLeading(String creator, String id, String leading) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s','tuple.creator':'%s'}", id, creator));
        Bson update = Document.parse(String.format("{'$set':{'tuple.leading':'%s','tuple.isChanged':2}}", leading));
        home.updateDocOne(_getReceptorColName(), filter, update);
    }

    @Override
    public void updateBackground(String creator, String id, BackgroundMode mode, String background) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s','tuple.creator':'%s'}", id, creator));
        Bson update = Document.parse(String.format("{'$set':{'tuple.backgroundMode':'%s','tuple.background':'%s'}}", mode, background));
        home.updateDocOne(_getReceptorColName(), filter, update);
    }

    @Override
    public void emptyBackground(String creator, String id) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s','tuple.creator':'%s'}", id, creator));
        Bson update = Document.parse(String.format("{'$set':{'tuple.backgroundMode':'%s'},'$unset':{'tuple.background':''}}", BackgroundMode.none));
        home.updateDocOne(_getReceptorColName(), filter, update);
    }

    @Override
    public void updateForeground(String creator, String id, ForegroundMode mode) {
        Bson filter = Document.parse(String.format("{'tuple.id':'%s','tuple.creator':'%s'}", id, creator));
        Bson update = Document.parse(String.format("{'$set':{'tuple.foregroundMode':'%s'}}", mode));
        home.updateDocOne(_getReceptorColName(), filter, update);
    }

    @Override
    public GeoReceptor getMobileGeoReceptor(String person, String device) {
        //'tuple.device':'?(device)',注释掉的原因是：在集成第三方通知会经常变动device，而行人的device未及时同步，导致在按新的device查时查不到。目前先注释了，但会存在这样的问题：
        //如果用户在其它设备上同时登录，这种只按用户取的移动感知器可能会取到非当前设备的感知器位置，这会影响到搜附近的猫准确性。
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple ?(colname) ?(clazz) where {'tuple.creator':'?(person)','tuple.moveMode':'moveableSelf'}");
        IQuery<GeoReceptor> query = home.createQuery(cjql);
        query.setParameter("colname", _getReceptorColName());
        query.setParameter("clazz", GeoReceptor.class.getName());
        query.setParameter("person", person);
//        query.setParameter("device", device);
        IDocument<GeoReceptor> doc = query.getSingleResult();
        if (doc == null) {
            return null;
        }
        return doc.tuple();
    }

    @Override
    public void updateMobileLocation(String person, String device, LatLng location) {
        //'tuple.device':'%s',注释掉的原因是：在集成第三方通知会经常变动device，而行人的device未及时同步，导致在按新的device查时查不到。目前先注释了，但会存在这样的问题：
        //        //如果用户在其它设备上同时登录，这种只按用户取的移动感知器可能会取到非当前设备的感知器位置，这会影响到搜附近的猫准确性。
        Bson filter = Document.parse(String.format("{'tuple.creator':'%s','tuple.moveMode':'moveableSelf'}", person));
        Bson update = Document.parse(String.format("{'$set':{'tuple.location':%s,'tuple.device':'%s'}}", new Gson().toJson(location),device));
        home.updateDocs(_getReceptorColName(), filter, update);
    }

    @Override
    public void updateMobileRadius(String person, String device, double radius) {
        //'tuple.device':'%s',注释掉的原因是：在集成第三方通知会经常变动device，而行人的device未及时同步，导致在按新的device查时查不到。目前先注释了，但会存在这样的问题：
        //        //如果用户在其它设备上同时登录，这种只按用户取的移动感知器可能会取到非当前设备的感知器位置，这会影响到搜附近的猫准确性。
        Bson filter = Document.parse(String.format("{'tuple.creator':'%s','tuple.moveMode':'moveableSelf'}", person));
        Bson update = Document.parse(String.format("{'$set':{'tuple.radius':'%s','tuple.device':'%s'}}", radius,device));
        home.updateDocs(_getReceptorColName(), filter, update);
    }

    @Override
    public void addObserver(String id, String observer) {
        String colname = String.format("%s.observers", _getReceptorColName());
        GeoObserver geoObserver = new GeoObserver();
        geoObserver.setObserver(observer);
        geoObserver.setReceptor(id);
        geoObserver.setCtime(System.currentTimeMillis());
        home.saveDoc(colname, new TupleDocument<>(geoObserver));
    }

    @Override
    public void removeObserver(String id, String observer) {
        String colname = String.format("%s.observers", _getReceptorColName());
        home.deleteDocOne(colname, String.format("{'tuple.receptor':'%s','tuple.observer':'%s'}", id, observer));
    }

    @Override
    public List<GeoObserver> pageObserver(String id, long limit, long offset) {
        String colname = String.format("%s.observers", _getReceptorColName());
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

    @Override
    public void publishArticle(String creator, GeosphereDocument document) {
        String colname = _getDocumentColName();
        home.saveDoc(colname, new TupleDocument<>(document));
    }

    @Override
    public void removeArticle(String creator, String receptor, String docid) {
        String colname = _getDocumentColName();
        home.deleteDocOne(colname, String.format("{'tuple.id':'%s','tuple.receptor':'%s','tuple.creator':'%s'}", docid, receptor, creator));
        _emptyArticleExtra(docid);
    }

    @Override
    public GeosphereDocument getGeoDocument(String docid) {
        String colname = _getDocumentColName();
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple %s %s where {'tuple.id':'%s'}",
                colname,
                GeosphereDocument.class.getName(),
                docid
        );
        IQuery<GeosphereDocument> query = home.createQuery(cjql);
        IDocument<GeosphereDocument> documentIDocument = query.getSingleResult();
        if (documentIDocument == null) {
            return null;
        }
        return documentIDocument.tuple();
    }

    @Override
    public List<GeosphereDocument> findGeoDocuments(List<String> docids) {
        String colname = _getDocumentColName();
        String cjql = String.format("select {'tuple':'*'} from tuple %s %s where {'tuple.id':{'$in':%s}}",
                colname,
                GeosphereDocument.class.getName(),
                new Gson().toJson(docids)
        );
        IQuery<GeosphereDocument> query = home.createQuery(cjql);
        List<IDocument<GeosphereDocument>> docs = query.getResultList();
        List<GeosphereDocument> list = new ArrayList<>();
        for (IDocument<GeosphereDocument> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    @Override
    public List<GeosphereMedia> listExtraMedia(String docid) {
        String colname = _getMediaColName();
        String cjql = String.format("select {'tuple':'*'} from tuple %s %s where {'tuple.docid':'%s'}",
                colname,
                GeosphereMedia.class.getName(),
                docid
        );
        IQuery<GeosphereMedia> query = home.createQuery(cjql);
        List<IDocument<GeosphereMedia>> docs = query.getResultList();
        List<GeosphereMedia> list = new ArrayList<>();
        for (IDocument<GeosphereMedia> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    @Override
    public List<GeoDocumentLike> pageLike(String docid, long limit, long skip) {
        String colname = _getLikeColName();
        String cjql = String.format("select {'tuple':'*'}.sort({'tuple.ctime':-1}).limit(%s).skip(%s) from tuple %s %s where {'tuple.docid':'%s'}",
                limit,
                skip,
                colname,
                GeoDocumentLike.class.getName(),
                docid
        );
        IQuery<GeoDocumentLike> query = home.createQuery(cjql);
        List<IDocument<GeoDocumentLike>> docs = query.getResultList();
        List<GeoDocumentLike> list = new ArrayList<>();
        for (IDocument<GeoDocumentLike> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    @Override
    public List<GeoDocumentComment> pageComment(String docid, long limit, long skip) {
        String colname = _getCommentColName();
        String cjql = String.format("select {'tuple':'*'}.sort({'tuple.ctime':-1}).limit(%s).skip(%s) from tuple %s %s where {'tuple.docid':'%s'}",
                limit,
                skip,
                colname,
                GeoDocumentComment.class.getName(),
                docid
        );
        IQuery<GeoDocumentComment> query = home.createQuery(cjql);
        List<IDocument<GeoDocumentComment>> docs = query.getResultList();
        List<GeoDocumentComment> list = new ArrayList<>();
        for (IDocument<GeoDocumentComment> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    //清空互动，点赞、评论、多媒体
    private void _emptyArticleExtra(String docid) {
        String colname = _getLikeColName();
        home.deleteDocs(colname, String.format("{'tuple.docid':'%s'}", docid));
        colname = _getCommentColName();
        home.deleteDocs(colname, String.format("{'tuple.docid':'%s'}", docid));
        colname = _getMediaColName();
        home.deleteDocs(colname, String.format("{'tuple.docid':'%s'}", docid));
    }

    @Override
    public void like(String principal, String receptor, String docid) {
        String colname = _getLikeColName();
        GeoDocumentLike likePerson = new GeoDocumentLike();
        likePerson.setCtime(System.currentTimeMillis());
        likePerson.setDocid(docid);
        likePerson.setPerson(principal);
        likePerson.setReceptor(receptor);
        home.saveDoc(colname, new TupleDocument<>(likePerson));
    }

    @Override
    public void unlike(String principal, String receptor, String docid) {
        String colname = _getLikeColName();
        home.deleteDocOne(colname, String.format("{'tuple.docid':'%s','tuple.receptor':'%s','tuple.person':'%s'}", docid, receptor, principal));
    }

    @Override
    public void addComment(String principal, String receptor, String docid, String commentid, String content) {
        String colname = _getCommentColName();
        GeoDocumentComment comment = new GeoDocumentComment();
        comment.setCtime(System.currentTimeMillis());
        comment.setDocid(docid);
        comment.setPerson(principal);
        comment.setReceptor(receptor);
        comment.setId(commentid);
        comment.setContent(content);
        home.saveDoc(colname, new TupleDocument<>(comment));
    }

    @Override
    public void removeComment(String principal, String receptor, String docid, String commentid) {
        String colname = _getCommentColName();
        home.deleteDocOne(colname, String.format("{'tuple.docid':'%s','tuple.receptor':'%s','tuple.person':'%s','tuple.id':'%s'}", docid, receptor, principal, commentid));
    }

    @Override
    public void addMedia(GeoDocumentMedia media) {
        String colname = _getMediaColName();
        home.saveDoc(colname, new TupleDocument<>(media));
    }

    //https://blog.csdn.net/varyall/article/details/80308426
//    @Override
//    public List<GeoObjectResponse> recept(String category, long limit, long offset) {
    //$geoNear,设需要以当前坐标为原点，查询附近指定范围内的餐厅，并直接显示距离
//        这个需求用前面提到的$near是可以实现的，但是距离需要二次计算。这里我们用$geoNear这个命令查询。
//        $geoNear与$near功能类似，但提供更多功能和返回更多信息，官方文档是这么解释的：

//        return null;
//    }
}
