package cj.netos.document.openports.entities.netflow;

import cj.netos.document.openports.entities.Location;

/**
 * 网流消息，仅用于同步存放个人发布的文档，以使它人可看其动态
 */
public class NetflowMessage {
    String id;
    String content;
    Location location;
    String onchannel;
    String ctime;
    String creator;
    double wy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getOnchannel() {
        return onchannel;
    }

    public void setOnchannel(String onchannel) {
        this.onchannel = onchannel;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public double getWy() {
        return wy;
    }

    public void setWy(double wy) {
        this.wy = wy;
    }
}
