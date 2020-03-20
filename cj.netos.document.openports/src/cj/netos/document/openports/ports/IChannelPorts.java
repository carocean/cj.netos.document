package cj.netos.document.openports.ports;

import cj.netos.document.openports.entities.netflow.*;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.PKeyInRequest;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenportParameter;
import cj.studio.openport.annotations.CjOpenports;

import java.math.BigDecimal;
import java.util.List;

@CjOpenports(usage = "管道服务")
public interface IChannelPorts extends IOpenportService {
    @CjOpenport(usage = "网流管道发文。限定为访问者发文", command = "post")
    void publishDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "管道文档", in = PKeyInRequest.content, name = "document")
                    ChannelDocument document
    ) throws CircuitException;

    @CjOpenport(usage = "移除文章")
    void removeDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid
    ) throws CircuitException;

    @CjOpenport(usage = "文章多媒体附件", command = "post")
    void addDocumentMedia(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "多媒体信息", in = PKeyInRequest.content, name = "media")
                    DocumentMedia media
    ) throws CircuitException;

    @CjOpenport(usage = "移除文章多媒体附件")
    void removeDocumentMedia(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "多媒体标识", name = "mediaid")
                    String mediaid
    ) throws CircuitException;

    @CjOpenport(usage = "清空文章多媒体文件")
    void emptyDocumentMedia(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid
    ) throws CircuitException;

    @CjOpenport(usage = "管道文章点赞")
    void likeDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "文档所在管道号", name = "channel")
                    String channel,
            @CjOpenportParameter(usage = "文档创建者", name = "creator")
                    String creator
    ) throws CircuitException;

    @CjOpenport(usage = "管道文章取消点赞")
    void unlikeDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "文档所在管道号", name = "channel")
                    String channel,
            @CjOpenportParameter(usage = "文档创建者", name = "creator")
                    String creator
    ) throws CircuitException;

    @CjOpenport(usage = "管道文章评论。注意评论标识冲突将被放弃")
    void commentDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "文档所在管道号", name = "channel")
                    String channel,
            @CjOpenportParameter(usage = "文档创建者", name = "creator")
                    String creator,
            @CjOpenportParameter(usage = "评论标识", name = "commentid")
                    String commentid,
            @CjOpenportParameter(usage = "评论内容", name = "content")
                    String content
    ) throws CircuitException;

    @CjOpenport(usage = "管道文章取消评论")
    void uncommentDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "文档所在管道号", name = "channel")
                    String channel,
            @CjOpenportParameter(usage = "文档创建者", name = "creator")
                    String creator,
            @CjOpenportParameter(usage = "评论标识", name = "commentid")
                    String commentid
    ) throws CircuitException;

    @CjOpenport(usage = "分页点赞")
    void addExtraActivity(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "文档创建者", name = "creator")
                    String creator,
            @CjOpenportParameter(usage = "管道", name = "channel")
                    String channel,
            @CjOpenportParameter(usage = "流转动作：arrive,send.comment,send.like", name = "action")
                    String action,
            @CjOpenportParameter(usage = "流转附件说明", name = "attach")
                    String attach,
            @CjOpenportParameter(usage = "获取的洇金，纹银", name = "wy")
                    BigDecimal wy
    ) throws CircuitException;

    @CjOpenport(usage = "分页点赞")
    List<DocumentLike> pageExtraLike(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "文档创建者", name = "creator")
                    String creator,
            @CjOpenportParameter(usage = "管道", name = "channel")
                    String channel,
            @CjOpenportParameter(usage = "页大小", name = "limit")
                    int limit,
            @CjOpenportParameter(usage = "偏移", name = "offset")
                    int offset
    ) throws CircuitException;

    @CjOpenport(usage = "分页评论")
    List<DocumentComment> pageExtraComment(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "文档创建者", name = "creator")
                    String creator,
            @CjOpenportParameter(usage = "管道", name = "channel")
                    String channel,
            @CjOpenportParameter(usage = "页大小", name = "limit")
                    int limit,
            @CjOpenportParameter(usage = "偏移", name = "offset")
                    int offset
    ) throws CircuitException;

    @CjOpenport(usage = "分页步骤")
    List<DocumentActivity> pageExtraActivity(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "文档创建者", name = "creator")
                    String creator,
            @CjOpenportParameter(usage = "管道", name = "channel")
                    String channel,
            @CjOpenportParameter(usage = "页大小", name = "limit")
                    int limit,
            @CjOpenportParameter(usage = "偏移", name = "offset")
                    int offset
    ) throws CircuitException;

    @CjOpenport(usage = "列出多媒体附件")
    List<DocumentMedia> listExtraMedia(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "文档创建者", name = "creator")
                    String creator,
            @CjOpenportParameter(usage = "管道", name = "channel")
                    String channel
    ) throws CircuitException;
}
