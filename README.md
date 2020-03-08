# 云文档
为了当终端删除后恢复数据。云磁盘是总服务
- 用户文档同步
- 平台管理，数据中心

## 高德接口api

将来借助于高德api中的行政区边界得到行政区的Polygon，然后即可在mongodb中查是否有地物在此边界内，因此只有查询才依赖于高德接口：
    var polygon = new AMap.Polygon({
                        strokeWeight: 1,
                        path: bounds[i],
                        fillOpacity: 0.4,
                        fillColor: '#80d8ff',
                        strokeColor: '#0091ea'
                    });