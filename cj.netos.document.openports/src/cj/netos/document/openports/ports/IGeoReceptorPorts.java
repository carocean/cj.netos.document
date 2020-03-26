package cj.netos.document.openports.ports;

import cj.netos.document.openports.entities.GeoObjectResponse;
import cj.netos.document.openports.entities.LatLng;
import cj.netos.document.openports.entities.geo.GeoObserver;
import cj.netos.document.openports.entities.geo.GeoReceptor;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenportParameter;
import cj.studio.openport.annotations.CjOpenports;

import java.util.List;

@CjOpenports(usage = "地理感知器")
public interface IGeoReceptorPorts extends IOpenportService {
    @CjOpenport(usage = "增加地理感知器")
    void addGeoReceptor(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "分类标识", name = "title") String title,
            @CjOpenportParameter(usage = "分类标识", name = "category") String category,
            @CjOpenportParameter(usage = "分类标识", name = "leading") String leading,
            @CjOpenportParameter(usage = "位置", name = "location", type = LatLng.class) LatLng location,
            @CjOpenportParameter(usage = "距中心点矩离，单位为米", name = "radius") double radius,
            @CjOpenportParameter(usage = "更新距离仅在mobiles分类下的感知器有用，默认10米", name = "uDistance",defaultValue = "10") int uDistance
    ) throws CircuitException;

    @CjOpenport(usage = "移除地理感知器")
    void removeGeoReceptor(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "所属分类", name = "category") String category
    ) throws CircuitException;

    @CjOpenport(usage = "更新位置")
    void updateLocation(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "分类标识", name = "category") String category,
            @CjOpenportParameter(usage = "位置", name = "location", type = LatLng.class) LatLng location
    ) throws CircuitException;

    @CjOpenport(usage = "更新半径")
    void updateRadius(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "分类标识", name = "category") String category,
            @CjOpenportParameter(usage = "半径", name = "radius") double radius
    ) throws CircuitException;

    @CjOpenport(usage = "获取移动设备感知器")
    GeoReceptor getMobileGeoReceptor(
            ISecuritySession securitySession
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

    @CjOpenport(usage = "按地圈自助感知地物")
    List<GeoObjectResponse> recept(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "分类", name = "category") String category,
            @CjOpenportParameter(usage = "分页大小", name = "limit") long limit,
            @CjOpenportParameter(usage = "偏移", name = "offset") long offset
    ) throws CircuitException;

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
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "分类标识", name = "category") String category
    ) throws CircuitException;

    @CjOpenport(usage = "移除观察者")
    void removeObserver(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "标识", name = "id") String id,
            @CjOpenportParameter(usage = "分类标识", name = "category") String category
    ) throws CircuitException;

    @CjOpenport(usage = "分页观察者")
    List<GeoObserver> pageObserver(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "感知器标识", name = "id") String id,
            @CjOpenportParameter(usage = "分类标识", name = "category") String category,
            @CjOpenportParameter(usage = "分页大小", name = "limit") long limit,
            @CjOpenportParameter(usage = "偏移", name = "offset") long offset
    ) throws CircuitException;

}
