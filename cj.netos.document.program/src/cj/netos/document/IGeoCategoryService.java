package cj.netos.document;

import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoCategoryApp;

import java.util.List;

public interface IGeoCategoryService {
    boolean exists(String id);

    void add(GeoCategory category);

    void remove(String id);

    GeoCategory get(String id);

    GeoCategory[] listCategory();

    void updateCategoryTitle(String id, String title);


    void reloadCategories();

    boolean existsApp(String on, String category, String id);

    void addGeoCategoryApp(String on, GeoCategoryApp app);

    void removeGeoCategoryApp(String on, String category, String id);

    List<GeoCategoryApp> listGeoCategoryApp(String on, String category);

}
