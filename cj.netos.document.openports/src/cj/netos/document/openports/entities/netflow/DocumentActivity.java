package cj.netos.document.openports.entities.netflow;

public class DocumentActivity {
    String docid;
    String creator;
    String channel;
    String activitor;
    long atime;

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

    public long getAtime() {
        return atime;
    }

    public void setAtime(long atime) {
        this.atime = atime;
    }
}
