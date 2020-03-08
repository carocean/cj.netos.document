package cj.netos.document.openports.ports;

import cj.netos.document.openports.entities.categories.Category;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenportParameter;
import cj.studio.openport.annotations.CjOpenports;

import java.util.List;

@CjOpenports(usage = "分类管理，仅限平台使用")
public interface ICategoriesPorts extends IOpenportService {
    @CjOpenport(usage = "创建分类")
    void createCategory(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文件夹名", name = "folder") String folder,
            @CjOpenportParameter(usage = "显示名", name = "title") String title,
            @CjOpenportParameter(usage = "描述", name = "desc") String desc,
            @CjOpenportParameter(usage = "要放入的路径", name = "parent") String path
    ) throws CircuitException;

    @CjOpenport(usage = "移除分类")
    void removeCategory(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "路径", name = "path") String path
    ) throws CircuitException;

    @CjOpenport(usage = "获取分类")
    Category getCategory(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "路径", name = "path") String path
    ) throws CircuitException;

    @CjOpenport(usage = "获取子分类")
    List<Category> children(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "路径", name = "path") String path
    ) throws CircuitException;

    @CjOpenport(usage = "更新分类标题")
    void updateCategoryTitle(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "路径", name = "path") String path,
            @CjOpenportParameter(usage = "显示名", name = "title") String title
    ) throws CircuitException;
}
