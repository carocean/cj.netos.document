package cj.netos.document.openports.entities.netflow;

import java.math.BigDecimal;

public class DocumentActivity {
    String docid;
    String creator;
    String channel;
    String activitor;
    String action;
    String attach;
    long ctime;
    BigDecimal wy;

    public BigDecimal getWy() {
        return wy;
    }

    public void setWy(BigDecimal wy) {
        this.wy = wy;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
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

    public String getActivitor() {
        return activitor;
    }

    public void setActivitor(String activitor) {
        this.activitor = activitor;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
}
