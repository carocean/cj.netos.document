package cj.netos.document.openports.entities.geo;

/**
 * 感知器的观察者
 */
public class GeoObserver {
    String observer;
    String receptor;
    long ctime;

    public String getObserver() {
        return observer;
    }

    public void setObserver(String observer) {
        this.observer = observer;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }
}
