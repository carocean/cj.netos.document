package cj.netos.document;

import cj.netos.document.openports.entities.bo.GeoChannelBO;
import cj.netos.document.openports.entities.geo.GeoBrand;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoCategoryApp;
import cj.netos.document.openports.entities.geo.GeoChannel;

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

    void config(String principal, List<GeoChannelBO> channels);

    List<GeoChannel> listChannel();

    List<GeoCategory> listCategoryOf(String channel);

    List<GeoBrand> listBrandBy(String channel, String category);

    List<GeoBrand> listBrandByCategory(String category);

    List<GeoBrand> listBrandByChannel(String channel);

    List<GeoCategory> listHotCategories();

    List<GeoBrand> listHotBrands();

    List<GeoCategory> listAllCategory();

}
