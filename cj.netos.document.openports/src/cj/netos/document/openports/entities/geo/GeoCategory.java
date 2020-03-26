package cj.netos.document.openports.entities.geo;

import cj.netos.document.openports.entities.GeoCategoryMoveMode;
import cj.studio.ecm.net.CircuitException;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.util.StringUtil;

public class GeoCategory {
    String id;
    String title;
    String creator;
    int sort;
    long ctime;
    double defaultRadius;
    GeoCategoryMoveMode moveMode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDefaultRadius() {
        return defaultRadius;
    }

    public void setDefaultRadius(double defaultRadius) {
        this.defaultRadius = defaultRadius;
    }

    public GeoCategoryMoveMode getMoveMode() {
        return moveMode;
    }

    public void setMoveMode(GeoCategoryMoveMode moveMode) {
        this.moveMode = moveMode;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
