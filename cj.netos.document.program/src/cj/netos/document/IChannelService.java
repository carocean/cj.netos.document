package cj.netos.document;

import cj.netos.document.openports.entities.ChannelDocument;

public interface IChannelService {
    void publishDocument(String principal, ChannelDocument document);

    void removeDocument(String principal, String docid);

    void likeDocument(String principal, String docid);

    void unlikeDocument(String principal, String docid);

    String commentDocument(String principal, String docid, String content);

    void uncommentDocument(String principal, String docid, String commentid);

}
