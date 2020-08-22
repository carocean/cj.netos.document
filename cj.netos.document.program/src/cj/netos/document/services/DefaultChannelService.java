package cj.netos.document.services;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.lns.chip.sos.cube.framework.TupleDocument;
import cj.netos.document.AbstractService;
import cj.netos.document.IChannelService;
import cj.netos.document.openports.entities.netflow.*;
import cj.studio.ecm.CJSystem;
import cj.studio.ecm.annotation.CjService;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CjService(name = "channelService")
public class DefaultChannelService extends AbstractService implements IChannelService {
    @Override
    public void publishDocument(String creator, ChannelDocument document) {
        if (StringUtil.isEmpty(document.getCreator())) {
            document.setCreator(creator);
        }
        if (StringUtil.isEmpty(document.getId())) {
            document.setId(UUID.randomUUID().toString());
        }
        document.setCtime(System.currentTimeMillis());
        ICube cube = cube(document.getCreator());
        cube.saveDoc("network.channel.documents", new TupleDocument<>(document));
    }

    @Override
    public List<ChannelDocument> findDocuments(String creator, List<String> docids) {
        ICube cube = cube(creator);
        String cjql = String.format("select {'tuple':'*'} from tuple network.channel.documents %s where {'tuple.id':{'$in':%s}}",
                ChannelDocument.class.getName(),
                new Gson().toJson(docids)
        );
        IQuery<ChannelDocument> query = cube.createQuery(cjql);
        List<IDocument<ChannelDocument>> docs = query.getResultList();
        List<ChannelDocument> list = new ArrayList<>();
        for (IDocument<ChannelDocument> doc : docs) {
            list.add(doc.tuple());
        }
        return list;
    }

    @Override
    public ChannelDocument getDocument(String creator, String docid) {
        ICube cube = cube(creator);
        String cjql = String.format("select {'tuple':'*'} from tuple network.channel.documents %s where {'tuple.id':'%s'}",
                ChannelDocument.class.getName(),
                docid
        );
        IQuery<ChannelDocument> query = cube.createQuery(cjql);
        IDocument<ChannelDocument> document = query.getSingleResult();
        if (document == null) {
            return null;
        }
        return document.tuple();
    }

    @Override
    public void removeDocument(String creator, String docid) {
        ICube cube = cube(creator);
        cube.deleteDocOne("network.channel.documents", String.format("{'tuple.id':'%s'}", docid));
    }

    @Override
    public void addDocumentMedia(String creator, ChannelMedia media) {
        ICube cube = cube(creator);
        media.setCtime(System.currentTimeMillis());
        cube.saveDoc("network.channel.documents.medias", new TupleDocument<>(media));
    }

    @Override
    public void removeDocumentMedia(String creator, String docid, String mediaid) {
        ICube cube = cube(creator);
        cube.deleteDocOne("network.channel.documents.medias", String.format("{'tuple.docid':'%s','tuple.id':'%s'}", docid, mediaid));
    }

    @Override
    public void emptyDocumentMedia(String creator, String docid) {
        ICube cube = cube(creator);
        cube.deleteDocOne("network.channel.documents.medias", String.format("{'tuple.docid':'%s'}", docid));
    }

    @Override
    public void likeDocument(String liker, String docid, String channel, String creator) {
        ICube cube = cube(creator);
        DocumentLike like = new DocumentLike();
        like.setCtime(System.currentTimeMillis());
        like.setPerson(liker);
        like.setChannel(channel);
        like.setDocid(docid);
        cube.saveDoc("network.channel.documents.likes", new TupleDocument<>(like));
    }

    @Override
    public void unlikeDocument(String unliker, String docid, String channel, String creator) {
        ICube cube = cube(creator);
        cube.deleteDocOne("network.channel.documents.likes", String.format("{'tuple.channel':'%s','tuple.docid':'%s','tuple.person':'%s'}", channel, docid, unliker));
    }

    @Override
    public void commentDocument(String commenter, String docid, String channel, String creator, String commentid, String content) {
        ICube cube = cube(creator);
        if (cube.tupleCount("network.channel.documents.comments", String.format("{'tuple.docid':'%s','tuple.id':'%s'}", docid, commentid)) > 0) {
            CJSystem.logging().warn(getClass(), String.format("用户<%s>的评论标识已存在.%s", commenter, commentid));
            return;
        }
        DocumentComment comment = new DocumentComment();
        comment.setId(commentid);
        comment.setCtime(System.currentTimeMillis());
        comment.setPerson(commenter);
        comment.setDocid(docid);
        comment.setChannel(channel);
        comment.setContent(content);
        cube.saveDoc("network.channel.documents.comments", new TupleDocument<>(comment));
        comment.getId();
    }

    @Override
    public void uncommentDocument(String commenter, String docid, String channel, String creator, String commentid) {
        ICube cube = cube(creator);
        cube.deleteDocOne("network.channel.documents.comments", String.format("{'tuple.channel':'%s','tuple.docid':'%s','tuple.id':'%s'}", channel, docid, commentid));
    }

    @Override
    public void addExtraActivity(String creator, String activitor, String channel, String docid, String action, String attach, BigDecimal wy) {
        ICube cube = cube(creator);
        if (cube.tupleCount("network.channel.documents.activities", String.format("{'tuple.channel':'%s','tuple.docid':'%s','tuple.person':'%s'}", channel, docid, activitor)) > 0) {
            CJSystem.logging().warn(getClass(), String.format("用户<%s>已创建了步聚在文档<%s>", creator, docid));
            return;
        }
        DocumentActivity activity = new DocumentActivity();
        activity.setChannel(channel);
        activity.setDocid(docid);
        activity.setCreator(creator);
        activity.setActivitor(activitor);
        activity.setCtime(System.currentTimeMillis());
        activity.setAction(action);
        activity.setAttach(attach);
        activity.setWy(wy);
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
        String cjql = String.format("select {'tuple':'*'}.sort({'tuple.ctime':1}).limit(%s).skip(%s) from tuple network.channel.documents.activities %s where {'tuple.docid':'%s'}", limit, offset, DocumentActivity.class.getName(), docid);
        IQuery<DocumentActivity> query = cube.createQuery(cjql);
        List<IDocument<DocumentActivity>> docs = query.getResultList();
        List<DocumentActivity> activities = new ArrayList<>();
        for (IDocument<DocumentActivity> doc : docs) {
            activities.add(doc.tuple());
        }
        return activities;
    }

    @Override
    public List<ChannelMedia> listExtraMedia(String creator, String channel, String docid) {
        ICube cube = cube(creator);
        String cjql = String.format("select {'tuple':'*'} from tuple network.channel.documents.medias %s where {'tuple.docid':'%s'}", ChannelMedia.class.getName(), docid);
        IQuery<ChannelMedia> query = cube.createQuery(cjql);
        List<IDocument<ChannelMedia>> docs = query.getResultList();
        List<ChannelMedia> medias = new ArrayList<>();
        for (IDocument<ChannelMedia> doc : docs) {
            medias.add(doc.tuple());
        }
        return medias;
    }
}
