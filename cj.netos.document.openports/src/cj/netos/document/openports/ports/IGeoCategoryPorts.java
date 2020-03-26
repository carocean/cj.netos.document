package cj.netos.document.openports.ports;

import cj.netos.document.openports.entities.GeoCategoryMoveMode;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoCategoryApp;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenportParameter;
import cj.studio.openport.annotations.CjOpenports;

import java.util.List;

@CjOpenports(usage = "分类管理，仅限平台使用")
public interface IGeoCategoryPorts extends IOpenportService {
    @CjOpenport(usage = "创建分类")
    void createCategory(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "标识,指定有语义的", name = "id") String id,
            @CjOpenportParameter(usage = "显示名", name = "title") String title,
            @CjOpenportParameter(usage = "本类感知器的移动能力模式，有:unmoveable|moveableSelf|moveableDependon", name = "moveMode", defaultValue = "unmoveable") GeoCategoryMoveMode moveMode,
            @CjOpenportParameter(usage = "分类下感知器默认的感知半径，单位米", name = "defaultRadius", defaultValue = "500") double defaultRadius,
            @CjOpenportParameter(usage = "分类显示顺序", name = "sort", defaultValue = "0") int sort
    ) throws CircuitException;

    @CjOpenport(usage = "移除分类")
    void removeCategory(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "标识,指定有语义的", name = "id") String id
    ) throws CircuitException;

    @CjOpenport(usage = "获取分类")
    GeoCategory getCategory(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "标识,指定有语义的", name = "id") String id
    ) throws CircuitException;

    @CjOpenport(usage = "列出所有分类")
    GeoCategory[] listCategory(
            ISecuritySession securitySession
    ) throws CircuitException;

    @CjOpenport(usage = "更新分类标题")
    void updateCategoryTitle(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "标识,指定有语义的", name = "id") String id,
            @CjOpenportParameter(usage = "显示名", name = "title") String title
    ) throws CircuitException;

    @CjOpenport(usage = "刷新分类缓冲")
    void reloadCategories(
            ISecuritySession securitySession
    ) throws CircuitException;

    @CjOpenport(usage = "添加地理应用")
    void addGeoCategoryApp(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "指定分类应用在哪端，客端应用或是服端应用，onserved|onservice", name = "on") String on,
            @CjOpenportParameter(usage = "应用标识,指定有语义的", name = "id") String id,
            @CjOpenportParameter(usage = "分类标识,指定有语义的", name = "category") String category,
            @CjOpenportParameter(usage = "显示名", name = "title") String title,
            @CjOpenportParameter(usage = "图标", name = "leading") String leading,
            @CjOpenportParameter(usage = "导航路径或动作", name = "path") String path
    ) throws CircuitException;

    @CjOpenport(usage = "移除地理应用")
    void removeGeoCategoryApp(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "指定分类应用在哪端，客端应用或是服端应用，onserved|onservice", name = "on") String on,
            @CjOpenportParameter(usage = "应用标识,指定有语义的", name = "id") String id,
            @CjOpenportParameter(usage = "分类标识,指定有语义的", name = "category") String category
    ) throws CircuitException;

    @CjOpenport(usage = "列出所有地理应用")
    List<GeoCategoryApp> listGeoCategoryApp(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "指定分类应用在哪端，客端应用或是服端应用，onserved|onservice", name = "on") String on,
            @CjOpenportParameter(usage = "分类标识,指定有语义的", name = "category") String category
    ) throws CircuitException;
}
