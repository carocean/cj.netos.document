package cj.netos.document;

import cj.netos.document.openports.entities.BackgroundMode;
import cj.netos.document.openports.entities.ForegroundMode;
import cj.netos.document.openports.entities.GeoObjectResponse;
import cj.netos.document.openports.entities.LatLng;
import cj.netos.document.openports.entities.geo.GeoDocumentMedia;
import cj.netos.document.openports.entities.geo.GeoObserver;
import cj.netos.document.openports.entities.geo.GeoReceptor;
import cj.netos.document.openports.entities.geo.GeosphereDocument;

import java.util.List;

public interface IGeoReceptorService {
    public boolean exists(String category, String id);

    void add(String category, GeoReceptor receptor);

    void remove(String category, String id);

    GeoReceptor get(String category, String id);

    void updateLocation(String creator, String category, String id, LatLng location);

    void updateRadius(String creator, String category, String id, double radius);

    GeoReceptor getMobileGeoReceptor(String person, String device);

    void updateMobileLocation(String person, String device, LatLng location);

    void updateMobileRadius(String person, String device, double radius);

//    List<GeoObjectResponse> recept(String category, long limit, long offset);

    void addObserver(String id, String category, String observer);

    void removeObserver(String id, String category, String observer);

    List<GeoObserver> pageObserver(String id, String category, long limit, long offset);

    void updateLeading(String creator, String category, String id, String leading);

    void updateBackground(String principal, String category, String id, BackgroundMode mode, String background);

    void emptyBackground(String principal, String id, String category);

    void updateForeground(String principal, String id, String category, ForegroundMode mode);

    void publishArticle(String creator, String category, GeosphereDocument document);

    void removeArticle(String creator, String category, String receptor, String docid);

    void like(String principal, String category, String receptor, String docid);

    void unlike(String principal, String category, String receptor, String docid);

    void addComment(String principal, String category, String receptor, String docid, String commentid, String content);

    void removeComment(String principal, String category, String receptor, String docid, String commentid);


    void addMedia(String category, GeoDocumentMedia media);

    void createGeoIndex();

    List<GeoReceptor> getAllMyReceptor(String principal, Object device);

}
