package cj.netos.document.openports.entities.market;

import cj.netos.document.openports.entities.LatLng;

/**
 * 金证喷泉
 */
public class JZPQGeoObject {
    Object body;//对象主题
    LatLng location;
    long utime;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }


    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }
}
