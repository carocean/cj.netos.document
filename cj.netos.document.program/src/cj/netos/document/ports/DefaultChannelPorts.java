package cj.netos.document.ports;

import cj.netos.document.IChannelService;
import cj.netos.document.openports.entities.netflow.*;
import cj.netos.document.openports.ports.IChannelPorts;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

import java.math.BigDecimal;
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
    public List<ChannelDocument> findDocuments(ISecuritySession securitySession, String person, List<String> docids) throws CircuitException {
        return channelService.findDocuments(person,docids);
    }

    @Override
    public ChannelDocument getDocument(ISecuritySession securitySession, String creator, String docid) throws CircuitException {
        return channelService.getDocument(creator,docid);
    }

    @Override
    public List<ChannelDocument> pageDocument(ISecuritySession securitySession, String creator, String channel, int limit, long offset) throws CircuitException {
        return channelService.pageDocument(creator,channel,limit,offset);
    }

    @Override
    public void removeDocument(ISecuritySession securitySession, String docid) throws CircuitException {
        channelService.removeDocument(securitySession.principal(), docid);
    }

    @Override
    public void addDocumentMedia(ISecuritySession securitySession, ChannelMedia media) throws CircuitException {
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
    public void likeDocument(ISecuritySession securitySession, String docid, String channel, String creator) throws CircuitException {
        channelService.likeDocument(securitySession.principal(), docid, channel, creator);
    }

    @Override
    public void unlikeDocument(ISecuritySession securitySession, String docid, String channel, String creator) throws CircuitException {
        channelService.unlikeDocument(securitySession.principal(), docid, channel, creator);
    }

    @Override
    public void commentDocument(ISecuritySession securitySession, String docid, String channel, String creator, String commentid, String content) throws CircuitException {
        channelService.commentDocument(securitySession.principal(), docid, channel, creator, commentid, content);
    }

    @Override
    public void uncommentDocument(ISecuritySession securitySession, String docid, String channel, String creator, String commentid) throws CircuitException {
        channelService.uncommentDocument(securitySession.principal(), docid, channel, creator, commentid);
    }

    @Override
    public void addExtraActivity(ISecuritySession securitySession, String docid, String creator, String channel, String action, String attach, BigDecimal wy) throws CircuitException {
        channelService.addExtraActivity(creator, securitySession.principal(), channel, docid, action, attach, wy);
    }

    @Override
    public List<DocumentLike> pageExtraLike(ISecuritySession securitySession, String docid, String creator, String channel, int limit, int offset) throws CircuitException {
        return channelService.pageExtraLike(creator, channel, docid, limit, offset);
    }

    @Override
    public List<DocumentComment> pageExtraComment(ISecuritySession securitySession, String docid, String creator, String channel, int limit, int offset) throws CircuitException {
        return channelService.pageExtraComment(creator, channel, docid, limit, offset);
    }

    @Override
    public List<DocumentActivity> pageExtraActivity(ISecuritySession securitySession, String docid, String creator, String channel, int limit, int offset) throws CircuitException {
        return channelService.pageExtraActivity(creator, channel, docid, limit, offset);
    }

    @Override
    public List<ChannelMedia> listExtraMedia(ISecuritySession securitySession, String docid, String creator, String channel) throws CircuitException {
        return channelService.listExtraMedia(creator, channel, docid);
    }
}
