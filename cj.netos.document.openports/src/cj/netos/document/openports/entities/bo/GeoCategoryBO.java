package cj.netos.document.openports.entities.bo;

import cj.netos.document.openports.entities.geo.GeoBrand;
import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.studio.openport.util.Encript;
import cj.ultimate.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeoCategoryBO extends GeoCategory {
    List<GeoBrand> brands;


    public List<GeoBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<GeoBrand> brands) {
        this.brands = brands;
    }

    public GeoCategory toCategory() {
        GeoCategory category = new GeoCategory();
        category.setId(getId());
        category.setLeading(getLeading());
        category.setDefaultRadius(getDefaultRadius());
        category.setMoveMode(getMoveMode());
        category.setSort(getSort());
        category.setTitle(getTitle());
        category.setCreator(getCreator());
        category.setChannel(getChannel());
        category.setCtime(getCtime());
        category.setHot(isHot()
        );
        return category;
    }
}
