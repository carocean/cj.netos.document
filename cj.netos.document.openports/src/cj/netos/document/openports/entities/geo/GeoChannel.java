package cj.netos.document.openports.entities.geo;
//地理频道，分类归属。热点频道是通过分类的热点标记动态查出来的，与分类没有物理连接
public class GeoChannel {
    String id;
    String title;
    String leading;
    int sort;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLeading() {
        return leading;
    }

    public void setLeading(String leading) {
        this.leading = leading;
    }
}
