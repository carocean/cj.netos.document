package cj.netos.document.program;

import cj.netos.document.IGeoCategoryService;
import cj.netos.document.IGeoReceptorService;
import cj.studio.ecm.CJSystem;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.net.CircuitException;
import cj.studio.gateway.socket.Destination;
import cj.studio.gateway.socket.app.GatewayAppSiteProgram;
import cj.studio.gateway.socket.app.ProgramAdapterType;

@CjService(name = "$.cj.studio.gateway.app", isExoteric = true)
public class AppSiteProgram extends GatewayAppSiteProgram {

    @Override
    protected void onstart(Destination arg0, String arg1, ProgramAdapterType arg2) throws CircuitException {
        IGeoCategoryService geoCategoryService = (IGeoCategoryService) site.getService("geoCategoryService");
        geoCategoryService.reloadCategories();
        IGeoReceptorService geoReceptorService=(IGeoReceptorService)site.getService("geoReceptorService") ;
        geoReceptorService.createGeoIndex();
        CJSystem.logging().info(getClass(), "分类加载完毕");
    }

}
