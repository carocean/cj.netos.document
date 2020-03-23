package cj.netos.document.openports.entities.geo;

/**
 * 地物分类对应的特有服务，它表示两个集合：客端服务与服端服务
 */
public class GeoCategoryApp {
    String id;
    String title;
    String leading;
    String path;
    String category;
    String creator;
    long ctime;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
