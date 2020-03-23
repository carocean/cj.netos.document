package cj.netos.document.openports.entities.geo;

import cj.netos.document.openports.entities.Location;

import java.util.Map;

/**
 * 抽象地理感应器，其集合名为分类id
 */
public class GeoReceptor {
    String id;
    Location location;
    double radius;
    long ctime;
    long utime;
    String creator;
    /**
     * 代表的真实实体，如：人、树、店、出租车等
     */
    Object entity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
