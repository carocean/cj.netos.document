package cj.netos.document.openports.entities.geo;

import cj.studio.ecm.net.CircuitException;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.util.StringUtil;

public class GeoCategory {
    String id;
    String title;
    String entityClass;
    String creator;
    int sort;
    long ctime;

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

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }


    public Object createEntityByJson(String entityJson) throws CircuitException {
        if (StringUtil.isEmpty(entityClass)) {
            throw new CircuitException("404", "分类缺少实体类型定义:" + id);
        }
        try {
            Class<?> clazz = Class.forName(entityClass);
            return new Gson().fromJson(entityJson, clazz);
        } catch (ClassNotFoundException e) {
            throw new CircuitException("500", e);
        }
    }
}
