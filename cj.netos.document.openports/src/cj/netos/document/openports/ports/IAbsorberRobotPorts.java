package cj.netos.document.openports.ports;

import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.AccessTokenIn;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenportAppSecurity;
import cj.studio.openport.annotations.CjOpenports;

@CjOpenports(usage = "获取洇取器模板文件")
public interface IAbsorberRobotPorts extends IOpenportService {
    @CjOpenportAppSecurity
    @CjOpenport(usage = "洇取器模板", tokenIn = AccessTokenIn.nope)
    String getAbsorberTemplate(
            ISecuritySession securitySession
    ) throws CircuitException;
}
