package cj.netos.document.openports.ports;

import cj.netos.document.openports.entities.GeoObjectReponse;
import cj.netos.document.openports.entities.Location;
import cj.netos.document.openports.entities.geo.PedestrianGeoObject;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.annotations.CjOpenports;

import java.util.List;

@CjOpenports(usage = "行人服务")
public interface IPedestrianPorts extends IOpenportService {
    void updateLocation(String person, Location location);
    List<GeoObjectReponse> circleFind(Location point, double radius);
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
}
