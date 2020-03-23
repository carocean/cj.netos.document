package cj.netos.document.ports;

import cj.netos.document.IGeoCategoryService;
import cj.netos.document.IGeoReceptorService;
import cj.netos.document.openports.entities.GeoObjectResponse;
import cj.netos.document.openports.entities.Location;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoObserver;
import cj.netos.document.openports.entities.geo.GeoReceptor;
import cj.netos.document.openports.entities.geo.MobileGeoEntity;
import cj.netos.document.openports.ports.IGeoReceptorPorts;
import cj.studio.ecm.CJSystem;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

import java.util.ArrayList;
import java.util.List;

@CjService(name = "/geo/receptor.service")
public class DefaultGeoReceptorPorts implements IGeoReceptorPorts {
    @CjServiceRef
    IGeoCategoryService geoCategoryService;
    @CjServiceRef
    IGeoReceptorService geoReceptorService;

    @Override
    public void addGeoReceptor(ISecuritySession securitySession, String id, String category, Location location, double radius, String entity) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get(category);
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:%s", category));
        }
        if (geoReceptorService.exists(category, id)) {
            throw new CircuitException("500", String.format("已存在地理感知器:%s 在分类:%s", id, category));
        }
        GeoReceptor receptor = new GeoReceptor();
        receptor.setCtime(System.currentTimeMillis());
        receptor.setId(id);
        receptor.setLocation(location);
        receptor.setRadius(radius);
        receptor.setCreator(securitySession.principal());
        Object newEntity = geoCategory.createEntityByJson(entity);
        receptor.setEntity(newEntity);
        geoReceptorService.add(category, receptor);
    }

    @Override
    public void removeGeoReceptor(ISecuritySession securitySession, String id, String category) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get(category);
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:%s", category));
        }
        GeoReceptor receptor = geoReceptorService.get(category, id);
        if (receptor == null || !receptor.getCreator().equals(securitySession.principal())) {
            CJSystem.logging().warn(getClass(), String.format("不存在感知器:%s, 在分类:%s，因此被忽略", id, category));
            return;
        }
        geoReceptorService.remove(category, id);
    }

    @Override
    public void updateLocation(ISecuritySession securitySession, String id, String category, Location location) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get(category);
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:%s", category));
        }
        GeoReceptor receptor = geoReceptorService.get(category, id);
        if (receptor == null || !receptor.getCreator().equals(securitySession.principal())) {
            CJSystem.logging().warn(getClass(), String.format("不存在感知器:%s, 在分类:%s，因此被忽略", id, category));
            return;
        }
        geoReceptorService.updateLocation(category, id, location);
    }

    @Override
    public void updateRadius(ISecuritySession securitySession, String id, String category, double radius) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get(category);
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:%s", category));
        }
        GeoReceptor receptor = geoReceptorService.get(category, id);
        if (receptor == null || !receptor.getCreator().equals(securitySession.principal())) {
            CJSystem.logging().warn(getClass(), String.format("不存在感知器:%s, 在分类:%s，因此被忽略", id, category));
            return;
        }
        geoReceptorService.updateRadius(category, id, radius);
    }

    @Override
    public GeoReceptor getMobileGeoReceptor(ISecuritySession securitySession) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get("mobiles");
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:mobiles"));
        }
        GeoReceptor receptor = geoReceptorService.getMobileGeoReceptor(securitySession.principal(), securitySession.property("device") + "");
        return receptor;
    }

    @Override
    public void updateMobileLocation(ISecuritySession securitySession, Location location) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get("mobiles");
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:mobiles"));
        }
        geoReceptorService.updateMobileLocation(securitySession.principal(), securitySession.property("device") + "", location);
    }

    @Override
    public void updateMobileRadius(ISecuritySession securitySession, double radius) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get("mobiles");
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:mobiles"));
        }
        geoReceptorService.updateMobileRadius(securitySession.principal(), securitySession.property("device") + "", radius);
    }

    @Override
    public List<GeoObjectResponse> recept(ISecuritySession securitySession, String category, long limit, long offset) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get(category);
        if (geoCategory == null) {
            return new ArrayList<>();
        }
        return geoReceptorService.recept(category, limit, offset);
    }

    @Override
    public void addObserver(ISecuritySession securitySession, String id, String category) throws CircuitException {
        this.geoReceptorService.addObserver(id,category,securitySession.principal());
    }

    @Override
    public void removeObserver(ISecuritySession securitySession, String id, String category) throws CircuitException {
        this.geoReceptorService.removeObserver(id,category,securitySession.principal());
    }

    @Override
    public List<GeoObserver> pageObserver(ISecuritySession securitySession, String id, String category, long limit, long offset) throws CircuitException {
        return this.geoReceptorService.pageObserver(id,category,limit,offset);
    }
}
