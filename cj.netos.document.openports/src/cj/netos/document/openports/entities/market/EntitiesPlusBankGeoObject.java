package cj.netos.document.openports.entities.market;

import cj.netos.document.openports.entities.Location;

/**
 * 实体+ 定义信息
 */
public class EntitiesPlusBankGeoObject {
    Object body;//对象主题
    String category;//地理对象所属类别，为两级分类，如：/固定地物/楼盘/；固定地物的分类参考高德地图附近中的分类，这些分类均是固定地物
    Location location;
    long utime;
    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }
}
