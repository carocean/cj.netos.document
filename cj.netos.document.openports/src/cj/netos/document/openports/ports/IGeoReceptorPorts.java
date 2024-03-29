package cj.netos.document.openports.ports;

import cj.netos.document.openports.entities.BackgroundMode;
import cj.netos.document.openports.entities.ForegroundMode;
import cj.netos.document.openports.entities.LatLng;
import cj.netos.document.openports.entities.geo.*;
import cj.netos.document.openports.entities.netflow.ChannelMedia;
import cj.netos.document.openports.entities.netflow.GeosphereMedia;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.PKeyInRequest;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenportParameter;
import cj.studio.openport.annotations.CjOpenports;

import java.util.List;

@CjOpenports(usage = "地理感知器")
public interface IGeoReceptorPorts extends IOpenportService {
    @CjOpenport(usage = "增加地理感知器")
    void emptyReceptors(ISecuritySession securitySession
    ) throws CircuitException;

    @CjOpenport(usage = "增加地理感知器，在同一乡镇街道下唯一")
    void addGeoReceptor(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "分类标识", name = "title") String title,
            @CjOpenportParameter(usage = "乡镇或镇道行政代码", name = "townCode") String townCode,
            @CjOpenportParameter(usage = "频道标识", name = "channel") String channel,
            @CjOpenportParameter(usage = "分类标识", name = "category") String category,
            @CjOpenportParameter(usage = "所属品牌，可为空", name = "brand") String brand,
            @CjOpenportParameter(usage = "分类标识", name = "leading") String leading,
            @CjOpenportParameter(usage = "位置", name = "location", type = LatLng.class) LatLng location,
            @CjOpenportParameter(usage = "距中心点矩离，单位为米", name = "radius") double radius,
            @CjOpenportParameter(usage = "更新距离仅在mobiles分类下的感知器有用，默认10米", name = "uDistance", defaultValue = "10") int uDistance
    ) throws CircuitException;

    @CjOpenport(usage = "判断地理感知器名，在同一乡镇街道下是否唯一")
    boolean existsGeoReceptorOnTown(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "分类标识", name = "title") String title,
            @CjOpenportParameter(usage = "乡镇或镇道行政代码", name = "townCode") String townCode
    ) throws CircuitException;

    @CjOpenport(usage = "重建空间索引")
    void geoReindex(ISecuritySession securitySession) throws CircuitException;

    @CjOpenport(usage = "查询我的所有戌知器")
    List<GeoReceptor> getAllMyReceptor(
            ISecuritySession securitySession
    ) throws CircuitException;

    @CjOpenport(usage = "查询我的已删除的感知器")
    List<GeoReceptor> pageMyDeletedReceptor(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "分页大小", name = "limit") long limit,
            @CjOpenportParameter(usage = "记录偏移", name = "skip") long skip
    ) throws CircuitException;

    @CjOpenport(usage = "恢复我的已删的感知器")
    void recoverReceptor(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id
    ) throws CircuitException;

    @CjOpenport(usage = "查询指定用户创建的消息")
    List<GeosphereDocument> pageDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "文档创建者", name = "creator") String creator,
            @CjOpenportParameter(usage = "分页大小", name = "limit") long limit,
            @CjOpenportParameter(usage = "记录偏移", name = "skip") long skip
    ) throws CircuitException;

    @CjOpenport(usage = "查询消息")
    List<GeosphereDocument> pageDocument2(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "分页大小", name = "limit") long limit,
            @CjOpenportParameter(usage = "记录偏移", name = "skip") long skip
    ) throws CircuitException;

    @CjOpenport(usage = "获取文档")
    List<GeosphereDocument> findGeoDocuments(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "消息id集合", name = "docids") List<String> docids
    ) throws CircuitException;

    @CjOpenport(usage = "移除地理感知器")
    void removeGeoReceptor(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id
    ) throws CircuitException;

    @CjOpenport(usage = "更新位置")
    void updateLocation(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "位置", name = "location", type = LatLng.class) LatLng location
    ) throws CircuitException;

    @CjOpenport(usage = "更新半径")
    void updateRadius(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "半径", name = "radius") double radius
    ) throws CircuitException;

    @CjOpenport(usage = "更新背景")
    void updateBackground(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "背景模式。有：vertical,horizontal,none,", name = "mode", defaultValue = "none") BackgroundMode mode,
            @CjOpenportParameter(usage = "背景文件路径", name = "background") String background
    ) throws CircuitException;

    @CjOpenport(usage = "清除背景")
    void emptyBackground(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id
    ) throws CircuitException;

    @CjOpenport(usage = "更新前景色")
    void updateForeground(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "前景模式。有：original,white,", name = "mode", defaultValue = "original") ForegroundMode mode
    ) throws CircuitException;

    @CjOpenport(usage = "更新图标")
    void updateLeading(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "图标地址", name = "leading") String leading
    ) throws CircuitException;

    @CjOpenport(usage = "获取移动设备感知器")
    GeoReceptor getMobileGeoReceptor(
            ISecuritySession securitySession
    ) throws CircuitException;

    @CjOpenport(usage = "获取设备感知器")
    GeoReceptor getGeoReceptor(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id
    ) throws CircuitException;

    @CjOpenport(usage = "更新移动设备感知器位置")
    void updateMobileLocation(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "位置", name = "location", type = LatLng.class) LatLng location
    ) throws CircuitException;

    @CjOpenport(usage = "更新移动设备感知器中心矩离")
    void updateMobileRadius(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "半径", name = "radius") double radius
    ) throws CircuitException;

//    @CjOpenport(usage = "按地圈自助感知地物")
//    List<GeoObjectResponse> recept(
//            ISecuritySession securitySession,
//            @CjOpenportParameter(usage = "分类", name = "category") String category,
//            @CjOpenportParameter(usage = "分页大小", name = "limit") long limit,
//            @CjOpenportParameter(usage = "偏移", name = "offset") long offset
//    ) throws CircuitException;

    /*
    将来借助于高德api中的行政区边界得到行政区的Polygon，然后即可在mongodb中查是否有地物在此边界内，因此只有查询才依赖于高德接口：
    var polygon = new AMap.Polygon({
                        strokeWeight: 1,
                        path: bounds[i],
                        fillOpacity: 0.4,
                        fillColor: '#80d8ff',
                        strokeColor: '#0091ea'
                    });
     */

//    List<GeoObjectReponse> administrativeRegionFind();

    @CjOpenport(usage = "添加观察者")
    void addObserver(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id
    ) throws CircuitException;

    @CjOpenport(usage = "移除观察者")
    void removeObserver(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "标识", name = "id") String id
    ) throws CircuitException;

    @CjOpenport(usage = "分页观察者")
    List<GeoObserver> pageObserver(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "分页大小", name = "limit") long limit,
            @CjOpenportParameter(usage = "偏移", name = "offset") long offset
    ) throws CircuitException;

    @CjOpenport(usage = "发布感知器文档", command = "post")
    void publishArticle(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器文档", name = "document", in = PKeyInRequest.content) GeosphereDocument document
    ) throws CircuitException;

    @CjOpenport(usage = "删除我的文档")
    void removeArticle(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器", name = "receptor") String receptor,
            @CjOpenportParameter(usage = "消息id", name = "docid") String docid
    ) throws CircuitException;

    @CjOpenport(usage = "获取我的文档")
    GeosphereDocument getGeoDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "消息id", name = "docid") String docid
    ) throws CircuitException;

    @CjOpenport(usage = "我点赞文档")
    void like(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器", name = "receptor") String receptor,
            @CjOpenportParameter(usage = "消息id", name = "docid") String docid
    ) throws CircuitException;

    @CjOpenport(usage = "我取消点赞文档")
    void unlike(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器", name = "receptor") String receptor,
            @CjOpenportParameter(usage = "消息id", name = "docid") String docid
    ) throws CircuitException;

    @CjOpenport(usage = "我评论文档")
    void addComment(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器", name = "receptor") String receptor,
            @CjOpenportParameter(usage = "消息id", name = "docid") String docid,
            @CjOpenportParameter(usage = "评论标识", name = "commentid") String commentid,
            @CjOpenportParameter(usage = "评论内容", name = "content") String content
    ) throws CircuitException;

    @CjOpenport(usage = "删除我的评论")
    void removeComment(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器", name = "receptor") String receptor,
            @CjOpenportParameter(usage = "消息id", name = "docid") String docid,
            @CjOpenportParameter(usage = "评论标识", name = "commentid") String commentid
    ) throws CircuitException;

    @CjOpenport(usage = "文档的多媒体附件")
    void addMedia(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器", name = "receptor") String receptor,
            @CjOpenportParameter(usage = "消息id", name = "docid") String docid,
            @CjOpenportParameter(usage = "多媒体标识", name = "id") String id,
            @CjOpenportParameter(usage = "多媒体类型", name = "type") String type,
            @CjOpenportParameter(usage = "多媒体文件路径", name = "src") String src,
            @CjOpenportParameter(usage = "多媒体内容，如是分享", name = "text") String text,
            @CjOpenportParameter(usage = "多媒体头图标，如是分享", name = "leading") String leading
    ) throws CircuitException;


    @CjOpenport(usage = "文档的多媒体附件",command = "post")
    void addMedia2(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "media", name = "media",in = PKeyInRequest.content)  GeoDocumentMedia media
    ) throws CircuitException;

    @CjOpenport(usage = "列出多媒体附件")
    List<GeosphereMedia> listExtraMedia(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid
    ) throws CircuitException;

    @CjOpenport(usage = "分页点赞者")
    List<GeoDocumentLike> pageLike(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid") String docid,
            @CjOpenportParameter(usage = "分页大小", name = "limit") long limit,
            @CjOpenportParameter(usage = "记录偏移", name = "offset") long offset

    ) throws CircuitException;

    @CjOpenport(usage = "分页评论者")
    List<GeoDocumentComment> pageComment(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid") String docid,
            @CjOpenportParameter(usage = "分页大小", name = "limit") long limit,
            @CjOpenportParameter(usage = "记录偏移", name = "offset") long offset
    ) throws CircuitException;
}
