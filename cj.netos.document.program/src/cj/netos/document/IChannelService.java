package cj.netos.document;

import cj.netos.document.openports.entities.netflow.*;

import java.util.List;

public interface IChannelService {
    void publishDocument(String principal, ChannelDocument document);

    void removeDocument(String principal, String docid);

    void likeDocument(String liker, String docid, String channel, String creator);

    void unlikeDocument(String unliker, String docid, String channel, String creator);

    void commentDocument(String commenter, String docid, String channel, String creator, String commentid, String content);

    void uncommentDocument(String commenter, String docid, String channel, String creator, String commentid);

    void addDocumentMedia(String principal, DocumentMedia media);

    void emptyDocumentMedia(String principal, String docid);

    void removeDocumentMedia(String principal, String docid, String mediaid);

    void addExtraActivity(String creator,String activitor, String channel, String docid);

    List<DocumentLike> pageExtraLike(String creator, String channel, String docid, int limit, int offset);

    List<DocumentComment> pageExtraComment(String creator, String channel, String docid, int limit, int offset);

    List<DocumentActivity> pageExtraActivity(String creator, String channel, String docid, int limit, int offset);

    List<DocumentMedia> listExtraMedia(String creator, String channel, String docid);

}
