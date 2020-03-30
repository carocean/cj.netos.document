package cj.netos.document.openports.entities.geo;

import cj.netos.document.openports.entities.BackgroundMode;
import cj.netos.document.openports.entities.ForegroundMode;
import cj.netos.document.openports.entities.LatLng;

/**
 * 抽象地理感应器，其集合名为分类id
 */
public class GeoReceptor {
    String id;
    String title;
    String category;
    String leading;
    String creator;
    LatLng location;
    double radius;
    //更新距离仅在mobiles分类下的感知器有用
    int uDistance;
    long ctime;
    String device;
    BackgroundMode backgroundMode;
    ForegroundMode foregroundMode;
    String backgroud;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BackgroundMode getBackgroundMode() {
        return backgroundMode;
    }

    public void setBackgroundMode(BackgroundMode backgroundMode) {
        this.backgroundMode = backgroundMode;
    }

    public ForegroundMode getForegroundMode() {
        return foregroundMode;
    }

    public void setForegroundMode(ForegroundMode foregroundMode) {
        this.foregroundMode = foregroundMode;
    }

    public String getBackgroud() {
        return backgroud;
    }

    public void setBackgroud(String backgroud) {
        this.backgroud = backgroud;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLeading() {
        return leading;
    }

    public void setLeading(String leading) {
        this.leading = leading;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getuDistance() {
        return uDistance;
    }

    public void setuDistance(int uDistance) {
        this.uDistance = uDistance;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
