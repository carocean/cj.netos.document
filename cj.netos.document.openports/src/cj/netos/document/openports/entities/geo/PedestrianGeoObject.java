package cj.netos.document.openports.entities.geo;

import cj.netos.document.openports.entities.Location;

/**
 * 行人地理对<br>
 *     为了性能将行人独立一张表
 */
public class PedestrianGeoObject {
    String person;
    Location location;
    long utime;
    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
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
