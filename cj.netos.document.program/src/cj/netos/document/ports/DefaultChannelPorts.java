package cj.netos.document.ports;

import cj.netos.document.IChannelService;
import cj.netos.document.openports.entities.ChannelDocument;
import cj.netos.document.openports.ports.IChannelPorts;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

@CjService(name = "/network/channel.service")
public class DefaultChannelPorts implements IChannelPorts {
    @CjServiceRef
    IChannelService channelService;

    @Override
    public void publishDocument(ISecuritySession securitySession, ChannelDocument document) throws CircuitException {
        channelService.publishDocument(securitySession.principal(), document);
    }

    @Override
    public void removeDocument(ISecuritySession securitySession, String docid) throws CircuitException {
        channelService.removeDocument(securitySession.principal(), docid);
    }

    @Override
    public void likeDocument(ISecuritySession securitySession, String docid) throws CircuitException {
        channelService.likeDocument(securitySession.principal(), docid);
    }

    @Override
    public void unlikeDocument(ISecuritySession securitySession, String docid) throws CircuitException {
        channelService.unlikeDocument(securitySession.principal(), docid);
    }

    @Override
    public String commentDocument(ISecuritySession securitySession, String docid, String content) throws CircuitException {
        return channelService.commentDocument(securitySession.principal(), docid, content);
    }

    @Override
    public void uncommentDocument(ISecuritySession securitySession, String docid, String commentid) throws CircuitException {
        channelService.uncommentDocument(securitySession.principal(), docid, commentid);
    }
}
