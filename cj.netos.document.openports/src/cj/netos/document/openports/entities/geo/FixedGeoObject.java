package cj.netos.document.openports.entities.geo;

import cj.netos.document.openports.entities.Location;

/**
 * 用于查询地理对象的相交关系，但不进行位置更新，更新交由location服务器完成，也就是读写分离原则<br>
 *     存储上为了性能：固定地物共享同一张表；每种移动地物一张表<br>
 *         该固定地物不是纹银银行和加盟商户，加盟后的在市场列出。这里的固定地物是为用户提供附近都有哪里东西而已
 */
public class FixedGeoObject {
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
