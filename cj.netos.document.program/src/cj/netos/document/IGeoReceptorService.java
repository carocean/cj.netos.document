package cj.netos.document;

import cj.netos.document.openports.entities.GeoObjectResponse;
import cj.netos.document.openports.entities.Location;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoObserver;
import cj.netos.document.openports.entities.geo.GeoReceptor;

import java.util.List;

public interface IGeoReceptorService {
    public boolean exists(String category, String id);

    void add(String category,GeoReceptor receptor);

    void remove(String category, String id);

    GeoReceptor get(String category, String id);

    void updateLocation(String category, String id, Location location);

    void updateRadius(String category, String id, double radius);

    GeoReceptor getMobileGeoReceptor(String person, String device);

    void updateMobileLocation(String person, String device, Location location);

    void updateMobileRadius(String person, String device, double radius);

    List<GeoObjectResponse> recept(String category, long limit, long offset);

    void addObserver(String id, String category, String observer);

    void removeObserver(String id, String category, String observer);

    List<GeoObserver> pageObserver(String id, String category, long limit, long offset);

}
