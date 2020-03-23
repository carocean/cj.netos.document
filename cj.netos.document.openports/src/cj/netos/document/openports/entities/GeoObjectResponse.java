package cj.netos.document.openports.entities;

import cj.netos.document.openports.entities.geo.GeoReceptor;

public class GeoObjectResponse {
    GeoReceptor receptor;
    double distance;

    public GeoReceptor getReceptor() {
        return receptor;
    }

    public void setReceptor(GeoReceptor receptor) {
        this.receptor = receptor;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
