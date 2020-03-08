package cj.netos.document.openports.entities.categories;

public class Category {
    String path;
    String parent;//分类所在路径
    String title;
    String desc;
    public  String fullName(){
        return String.format("%s/%s", parent, path);
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        while (parent.endsWith("/")) {
            parent = parent.substring(0, parent.length() - 1);
        }
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
