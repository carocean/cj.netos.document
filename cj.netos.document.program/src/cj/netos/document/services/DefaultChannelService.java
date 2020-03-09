package cj.netos.document.services;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.document.AbstractLinkService;
import cj.netos.document.IChannelService;
import cj.netos.document.openports.entities.ChannelDocument;
import cj.netos.document.openports.entities.DocMedia;
import cj.netos.document.openports.entities.DocumentComment;
import cj.netos.document.openports.entities.DocumentLike;
import cj.studio.ecm.annotation.CjService;
import cj.ultimate.util.StringUtil;

import java.util.UUID;

@CjService(name = "channelService")
public class DefaultChannelService extends AbstractLinkService implements IChannelService {
    @Override
    public void publishDocument(String principal, ChannelDocument document) {
        ICube cube = cube(principal);
        if (StringUtil.isEmpty(document.getCreator())) {
            document.setCreator(principal);
        }
        if (StringUtil.isEmpty(document.getId())) {
            document.setId(UUID.randomUUID().toString());
        }
        document.setCtime(System.currentTimeMillis());
        cube.saveDoc("network.channel.documents", new TupleDocument<>(document));
    }

    @Override
    public void removeDocument(String principal, String docid) {
        ICube cube = cube(principal);
        cube.deleteDocOne("network.channel.documents", String.format("{'tuple.id':'%s'}", docid));
    }

    @Override
    public void addDocumentMedia(String principal, DocMedia media) {
        ICube cube = cube(principal);
        media.setCtime(System.currentTimeMillis());
        cube.saveDoc("network.channel.documents.medias", new TupleDocument<>(media));
    }

    @Override
    public void emptyDocumentMedia(String principal, String docid) {
        ICube cube = cube(principal);
        cube.deleteDocOne("network.channel.documents.medias", String.format("{'tuple.docid':'%s'}", docid));
    }

    @Override
    public void likeDocument(String principal, String docid) {
        ICube cube = cube(principal);
        DocumentLike like = new DocumentLike();
        like.setCtime(System.currentTimeMillis());
        like.setPerson(principal);
        like.setDocid(docid);
        cube.saveDoc("network.channel.documents.likes", new TupleDocument<>(like));
    }

    @Override
    public void unlikeDocument(String principal, String docid) {
        ICube cube = cube(principal);
        cube.deleteDocOne("network.channel.documents.likes", String.format("{'tuple.docid':'%s','tuple.person':'%s'}", docid, principal));
    }

    @Override
    public String commentDocument(String principal, String docid, String content) {
        ICube cube = cube(principal);
        DocumentComment comment = new DocumentComment();
        comment.setId(UUID.randomUUID().toString());
        comment.setCtime(System.currentTimeMillis());
        comment.setPerson(principal);
        comment.setDocid(docid);
        comment.setContent(content);
        cube.saveDoc("network.channel.documents.comments", new TupleDocument<>(comment));
        return comment.getId();
    }

    @Override
    public void uncommentDocument(String principal, String docid, String commentid) {
        ICube cube = cube(principal);
        cube.deleteDocOne("network.channel.documents.comments", String.format("{'tuple.docid':'%s','tuple.id':'%s'}", docid, commentid));
    }
}
