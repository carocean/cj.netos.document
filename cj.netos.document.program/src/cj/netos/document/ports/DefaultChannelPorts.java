package cj.netos.document.ports;

import cj.netos.document.IChannelService;
import cj.netos.document.openports.entities.netflow.*;
import cj.netos.document.openports.ports.IChannelPorts;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

import java.util.List;

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
    public void addDocumentMedia(ISecuritySession securitySession, DocumentMedia media) throws CircuitException {
        channelService.addDocumentMedia(securitySession.principal(), media);
    }

    @Override
    public void removeDocumentMedia(ISecuritySession securitySession, String docid, String mediaid) throws CircuitException {
        channelService.removeDocumentMedia(securitySession.principal(), docid, mediaid);
    }

    @Override
    public void emptyDocumentMedia(ISecuritySession securitySession, String docid) throws CircuitException {
        channelService.emptyDocumentMedia(securitySession.principal(), docid);
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
    public void commentDocument(ISecuritySession securitySession, String docid, String commentid, String content) throws CircuitException {
        channelService.commentDocument(securitySession.principal(), docid, commentid, content);
    }

    @Override
    public void uncommentDocument(ISecuritySession securitySession, String docid, String commentid) throws CircuitException {
        channelService.uncommentDocument(securitySession.principal(), docid, commentid);
    }

    @Override
    public void addExtraActivity(ISecuritySession securitySession, String docid, String creator, String channel) throws CircuitException {
        channelService.addExtraActivity(creator,securitySession.principal(), channel, docid);
    }

    @Override
    public List<DocumentLike> pageExtraLike(ISecuritySession securitySession, String docid, String creator, String channel, int limit, int offset) throws CircuitException {
       return channelService.pageExtraLike(creator, channel, docid, limit, offset);
    }

    @Override
    public List<DocumentComment> pageExtraComment(ISecuritySession securitySession, String docid, String creator, String channel, int limit, int offset) throws CircuitException {
        return  channelService.pageExtraComment(creator, channel, docid, limit, offset);
    }

    @Override
    public List<DocumentActivity> pageExtraActivity(ISecuritySession securitySession, String docid, String creator, String channel, int limit, int offset) throws CircuitException {
        return channelService.pageExtraActivity(creator, channel, docid, limit, offset);
    }

    @Override
    public List<DocumentMedia> listExtraMedia(ISecuritySession securitySession, String docid, String creator, String channel, int limit, int offset) throws CircuitException {
        return  channelService.listExtraMedia(creator, channel, docid, limit, offset);
    }
}
