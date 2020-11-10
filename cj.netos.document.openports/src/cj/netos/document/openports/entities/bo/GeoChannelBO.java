package cj.netos.document.openports.entities.bo;

import cj.netos.document.openports.entities.geo.GeoCategory;
import cj.netos.document.openports.entities.geo.GeoChannel;
import cj.studio.openport.util.Encript;
import cj.ultimate.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//热点不但独建立频道，而是在分类上作标识，而后查出来，动态的组装成一个热点频到
public class GeoChannelBO extends GeoChannel {
    List<GeoCategoryBO> categories;

    public List<GeoCategoryBO> getCategories() {
        return categories;
    }

    public void setCategories(List<GeoCategoryBO> categories) {
        this.categories = categories;
    }


    public GeoChannel toChannel() {
        GeoChannel channel = new GeoChannel();
        channel.setId(StringUtil.isEmpty(getId()) ? Encript.md5(UUID.randomUUID().toString()) : getId());
        channel.setLeading(getLeading());
        channel.setSort(getSort());
        channel.setTitle(getTitle());
        return channel;
    }
}
