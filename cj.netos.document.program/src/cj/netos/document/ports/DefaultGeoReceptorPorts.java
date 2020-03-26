package cj.netos.document.ports;

import cj.netos.document.IGeoCategoryService;
import cj.netos.document.IGeoReceptorService;
import cj.netos.document.openports.entities.GeoObjectResponse;
import cj.netos.document.openports.entities.LatLng;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoObserver;
import cj.netos.document.openports.entities.geo.GeoReceptor;
import cj.netos.document.openports.ports.IGeoReceptorPorts;
import cj.studio.ecm.CJSystem;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;
import cj.ultimate.gson2.com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

@CjService(name = "/geo/receptor.service")
public class DefaultGeoReceptorPorts implements IGeoReceptorPorts {
    @CjServiceRef
    IGeoCategoryService geoCategoryService;
    @CjServiceRef
    IGeoReceptorService geoReceptorService;

    @Override
    public void addGeoReceptor(ISecuritySession securitySession, String id, String title, String category, String leading, LatLng location, double radius, int uDistance) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get(category);
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:%s", category));
        }
        if (geoReceptorService.exists(category, id)) {
            throw new CircuitException("500", String.format("已存在地理感知器:%s 在分类:%s", id, category));
        }
        GeoReceptor receptor = new GeoReceptor();
        receptor.setCreator(securitySession.principal());
        receptor.setRadius(radius);
        receptor.setId(id);
        receptor.setCtime(System.currentTimeMillis());
        receptor.setCategory(category);
        receptor.setDevice((String) securitySession.property("device"));
        receptor.setLeading(leading);
        receptor.setLocation(location);
        receptor.setTitle(title);
        receptor.setuDistance(uDistance);
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
    public void updateLocation(ISecuritySession securitySession, String id, String category, LatLng location) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get(category);
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:%s", category));
        }
        GeoReceptor receptor = geoReceptorService.get(category, id);
        if (receptor == null || !receptor.getCreator().equals(securitySession.principal())) {
            CJSystem.logging().warn(getClass(), String.format("不存在感知器:%s, 在分类:%s，因此被忽略", id, category));
            return;
        }
        geoReceptorService.updateLocation(securitySession.principal(), category, id, location);
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
        geoReceptorService.updateRadius(securitySession.principal(), category, id, radius);
    }

    @Override
    public void updateLeading(ISecuritySession securitySession, String id, String category, String leading) throws CircuitException {
        GeoCategory geoCategory = geoCategoryService.get(category);
        if (geoCategory == null) {
            throw new CircuitException("500", String.format("不存在地理感知器:%s", category));
        }
        GeoReceptor receptor = geoReceptorService.get(category, id);
        if (receptor == null || !receptor.getCreator().equals(securitySession.principal())) {
            CJSystem.logging().warn(getClass(), String.format("不存在感知器:%s, 在分类:%s，因此被忽略", id, category));
            return;
        }
        geoReceptorService.updateLeading(securitySession.principal(), category, id, leading);
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
    public void updateMobileLocation(ISecuritySession securitySession, LatLng location) throws CircuitException {
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
        this.geoReceptorService.addObserver(id, category, securitySession.principal());
    }

    @Override
    public void removeObserver(ISecuritySession securitySession, String id, String category) throws CircuitException {
        this.geoReceptorService.removeObserver(id, category, securitySession.principal());
    }

    @Override
    public List<GeoObserver> pageObserver(ISecuritySession securitySession, String id, String category, long limit, long offset) throws CircuitException {
        return this.geoReceptorService.pageObserver(id, category, limit, offset);
    }
}
