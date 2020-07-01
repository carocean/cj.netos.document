package cj.netos.document.ports;

import cj.lns.chip.sos.cube.framework.*;
import cj.lns.chip.sos.cube.framework.lock.FileLockException;
import cj.netos.document.openports.ports.IAbsorberRobotPorts;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

import java.io.FileNotFoundException;

@CjService(name = "/absorber/template.service")
public class DefaultAbsorberRobotPorts implements IAbsorberRobotPorts {
    @CjServiceRef(refByName = "mongodb.netos.home")
    ICube home;

    @Override
    public String getAbsorberTemplate(ISecuritySession securitySession) throws CircuitException {
        FileSystem fileSystem = home.fileSystem();
        try {
            FileInfo fileInfo = fileSystem.openFile("/robot/template.yaml", OpenMode.onlyOpen);
            IReader reader = fileInfo.reader(0);
            byte[] data = reader.readFully();
            reader.close();
            return new String(data);
        } catch (FileNotFoundException e) {
            throw new CircuitException("404", e);
        } catch (FileLockException e) {
            throw new CircuitException("500", e);
        } catch (TooLongException e) {
            throw new CircuitException("500", e);
        }
    }
}
