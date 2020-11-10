package cj.netos.document.ports;

import cj.netos.document.IGeoCategoryService;
import cj.netos.document.openports.entities.GeoCategoryMoveMode;
import cj.netos.document.openports.entities.bo.GeoChannelBO;
import cj.netos.document.openports.entities.geo.GeoBrand;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoCategoryApp;
import cj.netos.document.openports.entities.geo.GeoChannel;
import cj.netos.document.openports.entities.result.GeoCategoryResult;
import cj.netos.document.openports.entities.result.GeoChannelPortal;
import cj.netos.document.openports.entities.result.GeoChannelResult;
import cj.netos.document.openports.ports.IGeoCategoryPorts;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.gson2.com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CjService(name = "/geo/category.service")
public class DefaultGeoCategoryPorts implements IGeoCategoryPorts {
    @CjServiceRef
    IGeoCategoryService geoCategoryService;

    private void _demandDistrictRights(ISecuritySession securitySession) throws CircuitException {
        boolean hasRights = false;
        for (int i = 0; i < securitySession.roleCount(); i++) {
            String role = securitySession.role(i);
            if (role.startsWith("platform:administrators") || role.startsWith("tenant:administrators") || role.startsWith("app:administrators")) {
                hasRights = true;
                break;
            }
        }
        if (!hasRights) {
            throw new CircuitException("801", "无权访问");
        }
    }

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
    public void createCategory(ISecuritySession securitySession, String id, String title, String leading, GeoCategoryMoveMode moveMode, double defaultRadius, int sort) throws CircuitException {
        _demandDistrictRights(securitySession);
        if (geoCategoryService.exists(id)) {
            throw new CircuitException("500", String.format("已存在分类：%s %s", id, title));
        }
        if (moveMode == null) {
            moveMode = GeoCategoryMoveMode.unmoveable;
        }
        GeoCategory category = new GeoCategory();
        category.setId(id);
        category.setMoveMode(moveMode);
        category.setTitle(title);
        category.setLeading(leading);
        category.setSort(sort);
        category.setDefaultRadius(defaultRadius < 0 ? 500 : defaultRadius);
        category.setCreator(securitySession.principal());
        category.setCtime(System.currentTimeMillis());
        this.geoCategoryService.add(category);
    }

    @Override
    public void removeCategory(ISecuritySession securitySession, String id) throws CircuitException {
        _demandDistrictRights(securitySession);
        this.geoCategoryService.remove(id);
    }

    @Override
    public GeoCategory getCategory(ISecuritySession securitySession, String id) throws CircuitException {
        return this.geoCategoryService.get(id);
    }

    @Override
    public GeoCategory[] listCategory(ISecuritySession securitySession) throws CircuitException {
        return geoCategoryService.listCategory();
    }

    @Override
    public void updateCategoryTitle(ISecuritySession securitySession, String id, String title) throws CircuitException {
        geoCategoryService.updateCategoryTitle(id, title);
    }

    @Override
    public void reloadCategories(ISecuritySession securitySession) throws CircuitException {
        _demandDistrictRights(securitySession);
        geoCategoryService.reloadCategories();
    }

    @Override
    public void addGeoCategoryApp(ISecuritySession securitySession, String on, String id, String category, String title, String leading, String path) throws CircuitException {
        _demandDistrictRights(securitySession);
        if (geoCategoryService.existsApp(on, category, id)) {
            throw new CircuitException("500", String.format("分类<%s>下已存在应用<%s>在端:%s", category, id, on));
        }
        GeoCategoryApp app = new GeoCategoryApp();
        app.setCategory(category);
        app.setId(id);
        app.setLeading(leading);
        app.setPath(path);
        app.setTitle(title);
        app.setCreator(securitySession.principal());
        app.setCtime(System.currentTimeMillis());
        geoCategoryService.addGeoCategoryApp(on, app);
    }

    @Override
    public void removeGeoCategoryApp(ISecuritySession securitySession, String on, String id, String category) throws CircuitException {
        _demandDistrictRights(securitySession);
        geoCategoryService.removeGeoCategoryApp(on, category, id);
    }

    @Override
    public List<GeoCategoryApp> listGeoCategoryApp(ISecuritySession securitySession, String on, String category) throws CircuitException {
        return geoCategoryService.listGeoCategoryApp(on, category);
    }

    @Override
    public GeoChannelPortal config(ISecuritySession securitySession, String info) throws CircuitException {
        _demandAdminRights(securitySession);
        List<GeoChannelBO> boList = new Gson().fromJson(info, new TypeToken<ArrayList<GeoChannelBO>>() {
        }.getType());
        if (boList == null) {
            GeoChannelPortal portal = new GeoChannelPortal();
            portal.setChannels(new ArrayList<>());
            portal.setHotBrands(new ArrayList<>());
            portal.setHotCategories(new ArrayList<>());
            return portal;
        }
        geoCategoryService.config(securitySession.principal(), boList);
        return getGeoPortal(securitySession);
    }

    @Override
    public GeoChannelPortal getGeoPortal(ISecuritySession securitySession) throws CircuitException {
        List<GeoChannelResult> results = new ArrayList<>();
        List<GeoChannel> channels = geoCategoryService.listChannel();
        for (GeoChannel channel : channels) {
            List<GeoCategory> categories = geoCategoryService.listCategoryOf(channel.getId());
            List<GeoCategoryResult> categoryResults = new ArrayList<>();
            for (GeoCategory category : categories) {
                List<GeoBrand> brands = geoCategoryService.listBrandByCategory(category.getId());
                GeoCategoryResult categoryResult = new GeoCategoryResult();
                Collections.sort(brands,new BrandComparetor());
                categoryResult.load(category, brands);
                categoryResults.add(categoryResult);
            }
            Collections.sort(categoryResults,new CategoryComparetor());
            GeoChannelResult channelResult = new GeoChannelResult();
            channelResult.load(channel, categoryResults);
            results.add(channelResult);
        }
        List<GeoCategory> categories = geoCategoryService.listHotCategories();
        List<GeoCategoryResult> categoryResults = new ArrayList<>();
        for (GeoCategory category : categories) {
            GeoCategoryResult categoryResult = new GeoCategoryResult();
            categoryResult.load(category, new ArrayList<>());
            categoryResults.add(categoryResult);
        }
        List<GeoBrand> brands = geoCategoryService.listHotBrands();
        GeoChannelPortal portal = new GeoChannelPortal();
        Collections.sort(results, new ChannelComparetor());
        portal.setChannels(results);
        Collections.sort(categoryResults,new CategoryComparetor());
        portal.setHotCategories(categoryResults);
        Collections.sort(brands,new BrandComparetor());
        portal.setHotBrands(brands);
        return portal;
    }

    class ChannelComparetor implements Comparator<GeoChannelResult> {

        @Override
        public int compare(GeoChannelResult o1, GeoChannelResult o2) {
            if (o1.getSort() == o2.getSort()) {
                return 0;
            }
            return o1.getSort() > o2.getSort() ? 1 : -1;
        }
    }
    class CategoryComparetor implements Comparator<GeoCategoryResult> {

        @Override
        public int compare(GeoCategoryResult o1, GeoCategoryResult o2) {
            if (o1.getSort() == o2.getSort()) {
                return 0;
            }
            return o1.getSort() > o2.getSort() ? 1 : -1;
        }
    }
    class BrandComparetor implements Comparator<GeoBrand> {

        @Override
        public int compare(GeoBrand o1, GeoBrand o2) {
            if (o1.getSort() == o2.getSort()) {
                return 0;
            }
            return o1.getSort() > o2.getSort() ? 1 : -1;
        }
    }
}
