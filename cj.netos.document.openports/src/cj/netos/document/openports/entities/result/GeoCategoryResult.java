package cj.netos.document.openports.entities.result;

import cj.netos.document.openports.entities.geo.GeoBrand;
import cj.netos.document.openports.entities.geo.GeoCategory;

import java.util.ArrayList;
import java.util.List;

public class GeoCategoryResult extends GeoCategory {
    List<GeoBrand> brands;

    public void load(GeoCategory category, List<GeoBrand> brands) {
        this.brands=brands==null?new ArrayList<>():brands;
        setChannel(category.getChannel());
        setCreator(category.getCreator());
        setCtime(category.getCtime());
        setDefaultRadius(category.getDefaultRadius());
        setHot(category.isHot());
        setId(category.getId());
        setLeading(category.getLeading());
        setMoveMode(category.getMoveMode());
        setSort(category.getSort());
        setTitle(category.getTitle());
    }

    public List<GeoBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<GeoBrand> brands) {
        this.brands = brands;
    }
}
