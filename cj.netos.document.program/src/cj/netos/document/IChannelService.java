package cj.netos.document;

import cj.netos.document.openports.entities.ChannelDocument;
import cj.netos.document.openports.entities.DocMedia;

public interface IChannelService {
    void publishDocument(String principal, ChannelDocument document);

    void removeDocument(String principal, String docid);

    void likeDocument(String principal, String docid);

    void unlikeDocument(String principal, String docid);

    String commentDocument(String principal, String docid, String content);

    void uncommentDocument(String principal, String docid, String commentid);

    void addDocumentMedia(String principal, DocMedia media);

    void emptyDocumentMedia(String principal, String docid);

}
