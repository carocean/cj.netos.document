package cj.netos.document.services;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.document.AbstractLinkService;
import cj.netos.document.IChannelService;
import cj.netos.document.openports.entities.netflow.*;
import cj.studio.ecm.CJSystem;
import cj.studio.ecm.annotation.CjService;
import cj.ultimate.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
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
    public void addDocumentMedia(String principal, DocumentMedia media) {
        ICube cube = cube(principal);
        media.setCtime(System.currentTimeMillis());
        cube.saveDoc("network.channel.documents.medias", new TupleDocument<>(media));
    }

    @Override
    public void removeDocumentMedia(String principal, String docid, String mediaid) {
        ICube cube = cube(principal);
        cube.deleteDocOne("network.channel.documents.medias", String.format("{'tuple.docid':'%s','tuple.id':'%s'}", docid, mediaid));
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
    public void commentDocument(String principal, String docid, String commentid, String content) {
        ICube cube = cube(principal);
        if (cube.tupleCount("network.channel.documents.comments", String.format("{'tuple.docid':'%s','tuple.id':'%s'}", docid, commentid)) > 0) {
            CJSystem.logging().warn(getClass(), String.format("用户<%s>的评论标识已存在.%s", principal, commentid));
            return;
        }
        DocumentComment comment = new DocumentComment();
        comment.setId(commentid);
        comment.setCtime(System.currentTimeMillis());
        comment.setPerson(principal);
        comment.setDocid(docid);
        comment.setContent(content);
        cube.saveDoc("network.channel.documents.comments", new TupleDocument<>(comment));
        comment.getId();
    }

    @Override
    public void uncommentDocument(String principal, String docid, String commentid) {
        ICube cube = cube(principal);
        cube.deleteDocOne("network.channel.documents.comments", String.format("{'tuple.docid':'%s','tuple.id':'%s'}", docid, commentid));
    }

    @Override
    public void addExtraActivity(String creator, String activitor, String channel, String docid) {
        ICube cube = cube(creator);
        DocumentActivity activity = new DocumentActivity();
        activity.setAtime(System.currentTimeMillis());
        activity.setChannel(channel);
        activity.setDocid(docid);
        activity.setPerson(activitor);
        cube.saveDoc("network.channel.documents.activities", new TupleDocument<>(activity));
    }

    @Override
    public List<DocumentLike> pageExtraLike(String creator, String channel, String docid, int limit, int offset) {
        ICube cube = cube(creator);
        String cjql = String.format("select {'tuple':'*'}.limit(%s).skip(%s) from tuple network.channel.documents.likes %s where {'tuple.docid':'%s'}", limit, offset, DocumentLike.class.getName(), docid);
        IQuery<DocumentLike> query = cube.createQuery(cjql);
        List<IDocument<DocumentLike>> docs = query.getResultList();
        List<DocumentLike> likes = new ArrayList<>();
        for (IDocument<DocumentLike> doc : docs) {
            likes.add(doc.tuple());
        }
        return likes;
    }

    @Override
    public List<DocumentComment> pageExtraComment(String creator, String channel, String docid, int limit, int offset) {
        ICube cube = cube(creator);
        String cjql = String.format("select {'tuple':'*'}.limit(%s).skip(%s) from tuple network.channel.documents.comments %s where {'tuple.docid':'%s'}", limit, offset, DocumentComment.class.getName(), docid);
        IQuery<DocumentComment> query = cube.createQuery(cjql);
        List<IDocument<DocumentComment>> docs = query.getResultList();
        List<DocumentComment> comments = new ArrayList<>();
        for (IDocument<DocumentComment> doc : docs) {
            comments.add(doc.tuple());
        }
        return comments;
    }

    @Override
    public List<DocumentActivity> pageExtraActivity(String creator, String channel, String docid, int limit, int offset) {
        ICube cube = cube(creator);
        String cjql = String.format("select {'tuple':'*'}.limit(%s).skip(%s) from tuple network.channel.documents.activities %s where {'tuple.docid':'%s'}", limit, offset, DocumentActivity.class.getName(), docid);
        IQuery<DocumentActivity> query = cube.createQuery(cjql);
        List<IDocument<DocumentActivity>> docs = query.getResultList();
        List<DocumentActivity> activities = new ArrayList<>();
        for (IDocument<DocumentActivity> doc : docs) {
            activities.add(doc.tuple());
        }
        return activities;
    }

    @Override
    public List<DocumentMedia> listExtraMedia(String creator, String channel, String docid, int limit, int offset) {
        ICube cube = cube(creator);
        String cjql = String.format("select {'tuple':'*'}.limit(%s).skip(%s) from tuple network.channel.documents.medias %s where {'tuple.docid':'%s'}", limit, offset, DocumentMedia.class.getName(), docid);
        IQuery<DocumentMedia> query = cube.createQuery(cjql);
        List<IDocument<DocumentMedia>> docs = query.getResultList();
        List<DocumentMedia> medias = new ArrayList<>();
        for (IDocument<DocumentMedia> doc : docs) {
            medias.add(doc.tuple());
        }
        return medias;
    }
}
