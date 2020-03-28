package cj.netos.document.ports;

import cj.netos.document.IGeoCategoryService;
import cj.netos.document.openports.entities.GeoCategoryMoveMode;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoCategoryApp;
import cj.netos.document.openports.ports.IGeoCategoryPorts;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

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

    @Override
    public void createCategory(ISecuritySession securitySession, String id, String title,String leading,  GeoCategoryMoveMode moveMode, double defaultRadius, int sort) throws CircuitException {
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
}
