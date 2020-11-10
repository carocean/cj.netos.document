package cj.netos.document.openports.entities.result;

import cj.netos.document.openports.entities.geo.GeoBrand;

import java.util.List;

public class GeoChannelPortal {
    List<GeoChannelResult> channels;
    List<GeoCategoryResult> hotCategories;
    List<GeoBrand> hotBrands;

    public List<GeoChannelResult> getChannels() {
        return channels;
    }

    public void setChannels(List<GeoChannelResult> channels) {
        this.channels = channels;
    }

    public List<GeoCategoryResult> getHotCategories() {
        return hotCategories;
    }

    public void setHotCategories(List<GeoCategoryResult> hotCategories) {
        this.hotCategories = hotCategories;
    }

    public List<GeoBrand> getHotBrands() {
        return hotBrands;
    }

    public void setHotBrands(List<GeoBrand> hotBrands) {
        this.hotBrands = hotBrands;
    }
}
