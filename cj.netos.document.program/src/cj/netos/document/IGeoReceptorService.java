package cj.netos.document;

import cj.netos.document.openports.entities.BackgroundMode;
import cj.netos.document.openports.entities.ForegroundMode;
import cj.netos.document.openports.entities.LatLng;
import cj.netos.document.openports.entities.geo.*;
import cj.netos.document.openports.entities.netflow.GeosphereMedia;

import java.util.List;

public interface IGeoReceptorService {
    public boolean exists( String id);

    void add( GeoReceptor receptor);

    void remove(String principal, String id);

    GeoReceptor get( String id);

    void updateLocation(String creator,  String id, LatLng location);

    void updateRadius(String creator,  String id, double radius);

    GeoReceptor getMobileGeoReceptor(String person, String device);

    void updateMobileLocation(String person, String device, LatLng location);

    void updateMobileRadius(String person, String device, double radius);

//    List<GeoObjectResponse> recept( long limit, long offset);

    void addObserver(String id,  String observer);

    void removeObserver(String id,  String observer);

    List<GeoObserver> pageObserver(String id,  long limit, long offset);

    void updateLeading(String creator,  String id, String leading);

    void updateBackground(String principal,  String id, BackgroundMode mode, String background);

    void emptyBackground(String principal, String id);

    void updateForeground(String principal, String id,  ForegroundMode mode);

    void publishArticle(String creator,  GeosphereDocument document);

    void removeArticle(String creator,  String receptor, String docid);

    void like(String principal,  String receptor, String docid);

    void unlike(String principal,  String receptor, String docid);

    void addComment(String principal,  String receptor, String docid, String commentid, String content);

    void removeComment(String principal,  String receptor, String docid, String commentid);


    void addMedia( GeoDocumentMedia media);

    void createGeoIndex();

    List<GeoReceptor> getAllMyReceptor(String principal);

    List<GeosphereDocument> pageDocument(String id,  String creator, long limit, long skip);

    GeosphereDocument getGeoDocument( String docid);

    List<GeosphereDocument> findGeoDocuments( List<String> docids);

    List<GeosphereMedia> listExtraMedia( String docid);

    void emptyCategory(GeoCategory category);

}
