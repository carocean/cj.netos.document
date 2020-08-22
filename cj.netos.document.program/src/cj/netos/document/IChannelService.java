package cj.netos.document;

import cj.netos.document.openports.entities.netflow.*;

import java.math.BigDecimal;
import java.util.List;

public interface IChannelService {
    void publishDocument(String principal, ChannelDocument document);

    void removeDocument(String principal, String docid);

    void likeDocument(String liker, String docid, String channel, String creator);

    void unlikeDocument(String unliker, String docid, String channel, String creator);

    void commentDocument(String commenter, String docid, String channel, String creator, String commentid, String content);

    void uncommentDocument(String commenter, String docid, String channel, String creator, String commentid);

    void addDocumentMedia(String principal, ChannelMedia media);

    void emptyDocumentMedia(String principal, String docid);

    void removeDocumentMedia(String principal, String docid, String mediaid);

    void addExtraActivity(String creator, String activitor, String channel, String docid, String action, String attach, BigDecimal wy);

    List<DocumentLike> pageExtraLike(String creator, String channel, String docid, int limit, int offset);

    List<DocumentComment> pageExtraComment(String creator, String channel, String docid, int limit, int offset);

    List<DocumentActivity> pageExtraActivity(String creator, String channel, String docid, int limit, int offset);

    List<ChannelMedia> listExtraMedia(String creator, String channel, String docid);

    List<ChannelDocument> findDocuments(String person, List<String> docids);

    ChannelDocument getDocument(String creator, String docid);

}
