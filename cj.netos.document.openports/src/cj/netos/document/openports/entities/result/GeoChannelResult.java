package cj.netos.document.openports.entities.result;

import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoChannel;

import java.util.ArrayList;
import java.util.List;
//热点不但独建立频道，而是在分类上作标识，而后查出来，动态的组装成一个热点频到
public class GeoChannelResult extends GeoChannel {
    List<GeoCategoryResult> categories;

    public List<GeoCategoryResult> getCategories() {
        return categories;
    }

    public void setCategories(List<GeoCategoryResult> categories) {
        this.categories = categories;
    }

    public void load(GeoChannel channel, List<GeoCategoryResult> categories) {
        this.categories=categories==null?new ArrayList<>():categories;
        this.setId(channel.getId());
        this.setLeading(channel.getLeading());
        this.setSort(channel.getSort());
        this.setTitle(channel.getTitle());
    }
}
