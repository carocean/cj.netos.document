package cj.netos.document.ports;

import cj.netos.document.IGeoCategoryService;
import cj.netos.document.IGeoReceptorService;
import cj.netos.document.openports.entities.BackgroundMode;
import cj.netos.document.openports.entities.ForegroundMode;
import cj.netos.document.openports.entities.LatLng;
import cj.netos.document.openports.entities.geo.*;
import cj.netos.document.openports.entities.netflow.GeosphereMedia;
import cj.netos.document.openports.ports.IGeoReceptorPorts;
import cj.studio.ecm.CJSystem;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

import java.util.List;

@CjService(name = "/geo/receptor.service")
public class DefaultGeoReceptorPorts implements IGeoReceptorPorts {
    @CjServiceRef
    IGeoCategoryService geoCategoryService;
    @CjServiceRef
    IGeoReceptorService geoReceptorService;

    private void _demandAdminRights(ISecuritySession securitySession) throws CircuitException {
        boolean hasRights = false;
        for (int i = 0; i < securitySession.roleCount(); i++) {
            String role = securitySession.role(i);
            if (role.startsWith("platform:administrators")) {
                hasRights = true;
                break;
            }
        }
        if (!hasRights) {
            throw new CircuitException("801", "无权访问");
        }
    }

    @Override
    public void emptyReceptors(ISecuritySession securitySession) throws CircuitException {
        _demandAdminRights(securitySession);
        List<GeoCategory> list = geoCategoryService.listAllCategory();
        for (GeoCategory category : list) {
            geoReceptorService.emptyCategory(category);
        }
    }

    @Override
    public boolean existsGeoReceptorOnTown(ISecuritySession securitySession, String title, String townCode) throws CircuitException {
        return geoReceptorService.existsTitleOnTownCode(title,townCode);
    }

    @Override
    public void addGeoReceptor(ISecuritySession securitySession, String id, String title,String townCode, String channel, String category, String brand, String leading, LatLng location, double radius, int uDistance) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get(category);
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:%s", category));
        }
        if (geoReceptorService.exists(id)) {
            throw new CircuitException("500", String.format("已存在地理感知器:%s", id));
        }
        if (geoReceptorService.existsTitleOnTownCode(title,townCode)) {
            throw new CircuitException("500", String.format("已存在地理感知器:%s，在乡镇或街道：%s", title,townCode));
        }
        GeoReceptor receptor = new GeoReceptor();
        receptor.setCreator(securitySession.principal());
        receptor.setRadius(radius);
        receptor.setTownCode(townCode);
        receptor.setId(id);
        receptor.setCtime(System.currentTimeMillis());
        receptor.setChannel(channel);
        receptor.setCategory(category);
        receptor.setBrand(brand);
        receptor.setDevice((String) securitySession.property("device"));
        receptor.setLeading(leading);
        receptor.setLocation(location);
        receptor.setTitle(title);
        receptor.setuDistance(uDistance);
        receptor.setMoveMode(geoCategory.getMoveMode());//将分类的移动模式拷贝过来，一般用于移动感知器，在移动模式下有且仅有类别才有此模式
        geoReceptorService.add(receptor);
    }

    @Override
    public void geoReindex(ISecuritySession securitySession) throws CircuitException {
        if (!securitySession.roleIn("platform:administrators") && !securitySession.roleIn("tenant:administrators") && !securitySession.roleIn("app:administrators")) {
            throw new CircuitException("801", String.format("无权执行"));
        }
        geoReceptorService.createGeoIndex();
    }

    @Override
    public List<GeoReceptor> getAllMyReceptor(ISecuritySession securitySession) throws CircuitException {
        return geoReceptorService.getAllMyReceptor(securitySession.principal());
    }

    @Override
    public void removeGeoReceptor(ISecuritySession securitySession, String id) throws CircuitException {
        geoReceptorService.remove(securitySession.principal(),id);
    }

    @Override
    public List<GeosphereDocument> pageDocument(ISecuritySession securitySession, String id, String creator, long limit, long skip) throws CircuitException {
        return geoReceptorService.pageDocument(id, creator, limit, skip);
    }

    @Override
    public List<GeosphereDocument> pageDocument2(ISecuritySession securitySession, String id, long limit, long skip) throws CircuitException {
        return geoReceptorService.pageDocument2(id, limit, skip);
    }

    @Override
    public List<GeosphereDocument> findGeoDocuments(ISecuritySession securitySession, List<String> docids) throws CircuitException {
        return geoReceptorService.findGeoDocuments(docids);
    }

    @Override
    public void updateLocation(ISecuritySession securitySession, String id, LatLng location) throws CircuitException {
        geoReceptorService.updateLocation(securitySession.principal(), id, location);
    }

    @Override
    public void updateRadius(ISecuritySession securitySession, String id, double radius) throws CircuitException {
        geoReceptorService.updateRadius(securitySession.principal(), id, radius);
    }

    @Override
    public void updateLeading(ISecuritySession securitySession, String id, String leading) throws CircuitException {
        geoReceptorService.updateLeading(securitySession.principal(), id, leading);
    }

    @Override
    public void updateBackground(ISecuritySession securitySession, String id, BackgroundMode mode, String background) throws CircuitException {
        geoReceptorService.updateBackground(securitySession.principal(), id, mode, background);
    }

    @Override
    public void emptyBackground(ISecuritySession securitySession, String id) throws CircuitException {
        geoReceptorService.emptyBackground(securitySession.principal(), id);
    }

    @Override
    public void updateForeground(ISecuritySession securitySession, String id, ForegroundMode mode) throws CircuitException {
        geoReceptorService.updateForeground(securitySession.principal(), id, mode);
    }

    @Override
    public GeoReceptor getMobileGeoReceptor(ISecuritySession securitySession) throws CircuitException {
        GeoReceptor receptor = geoReceptorService.getMobileGeoReceptor(securitySession.principal(), securitySession.property("device") + "");
        return receptor;
    }

    @Override
    public GeoReceptor getGeoReceptor(ISecuritySession securitySession, String id) throws CircuitException {
        GeoReceptor receptor = geoReceptorService.get(id);
        return receptor;
    }

    @Override
    public void updateMobileLocation(ISecuritySession securitySession, LatLng location) throws CircuitException {
        geoReceptorService.updateMobileLocation(securitySession.principal(), securitySession.property("device") + "", location);
    }

    @Override
    public void updateMobileRadius(ISecuritySession securitySession, double radius) throws CircuitException {
        geoReceptorService.updateMobileRadius(securitySession.principal(), securitySession.property("device") + "", radius);
    }
//
//    @Override
//    public List<GeoObjectResponse> recept(ISecuritySession securitySession, String category, long limit, long offset) throws CircuitException {
//        GeoCategory geoCategory = geoCategoryService.get(category);
//        if (geoCategory == null) {
//            return new ArrayList<>();
//        }
//        return geoReceptorService.recept(category, limit, offset);
//    }

    @Override
    public void addObserver(ISecuritySession securitySession, String id) throws CircuitException {
        this.geoReceptorService.addObserver(id, securitySession.principal());
    }

    @Override
    public void removeObserver(ISecuritySession securitySession, String id) throws CircuitException {
        this.geoReceptorService.removeObserver(id, securitySession.principal());
    }

    @Override
    public List<GeoObserver> pageObserver(ISecuritySession securitySession, String id, long limit, long offset) throws CircuitException {
        return this.geoReceptorService.pageObserver(id, limit, offset);
    }

    @Override
    public void publishArticle(ISecuritySession securitySession, GeosphereDocument document) throws CircuitException {
        GeoReceptor receptor = geoReceptorService.get(document.getReceptor());
        if (receptor == null) {
            CJSystem.logging().warn(getClass(), String.format("不存在感知器:%s, 因此被忽略", document.getReceptor()));
            return;
        }
        document.setCategory(receptor.getCategory());
        document.setChannel(receptor.getChannel());
        document.setBrand(receptor.getBrand());
        this.geoReceptorService.publishArticle(securitySession.principal(), document);
    }

    @Override
    public void removeArticle(ISecuritySession securitySession, String receptor, String docid) throws CircuitException {
        this.geoReceptorService.removeArticle(securitySession.principal(), receptor, docid);
    }

    @Override
    public GeosphereDocument getGeoDocument(ISecuritySession securitySession, String docid) throws CircuitException {
        return this.geoReceptorService.getGeoDocument(docid);
    }

    @Override
    public void like(ISecuritySession securitySession, String receptor, String docid) throws CircuitException {
        this.geoReceptorService.like(securitySession.principal(), receptor, docid);
    }

    @Override
    public void unlike(ISecuritySession securitySession, String receptor, String docid) throws CircuitException {
        this.geoReceptorService.unlike(securitySession.principal(), receptor, docid);
    }

    @Override
    public void addComment(ISecuritySession securitySession, String receptor, String docid, String commentid, String content) throws CircuitException {
        this.geoReceptorService.addComment(securitySession.principal(), receptor, docid, commentid, content);
    }

    @Override
    public void removeComment(ISecuritySession securitySession, String receptor, String docid, String commentid) throws CircuitException {
        this.geoReceptorService.removeComment(securitySession.principal(), receptor, docid, commentid);
    }

    @Override
    public void addMedia(ISecuritySession securitySession, String receptor, String docid, String id, String type, String src, String text, String leading) throws CircuitException {
        boolean exists = geoReceptorService.exists(receptor);
        if (!exists) {
            throw new CircuitException("500", String.format("不存在地理感知器:%s", receptor));
        }
        GeoDocumentMedia media = new GeoDocumentMedia();
        media.setCtime(System.currentTimeMillis());
        media.setDocid(docid);
        media.setId(id);
        media.setLeading(leading);
        media.setReceptor(receptor);
        media.setSrc(src);
        media.setText(text);
        media.setType(type);
        media.setCreator(securitySession.principal());
        this.geoReceptorService.addMedia(media);
    }

    @Override
    public List<GeosphereMedia> listExtraMedia(ISecuritySession securitySession, String docid) throws CircuitException {

        return geoReceptorService.listExtraMedia(docid);
    }

    @Override
    public List<GeoDocumentLike> pageLike(ISecuritySession securitySession, String docid, long limit, long skip) throws CircuitException {
        return geoReceptorService.pageLike(docid,limit,skip);
    }

    @Override
    public List<GeoDocumentComment> pageComment(ISecuritySession securitySession, String docid, long limit, long skip) throws CircuitException {
        return geoReceptorService.pageComment(docid,limit,skip);
    }
}
