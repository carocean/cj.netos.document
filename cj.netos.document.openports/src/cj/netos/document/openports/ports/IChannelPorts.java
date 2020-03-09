package cj.netos.document.openports.ports;

import cj.netos.document.openports.entities.ChannelDocument;
import cj.netos.document.openports.entities.DocMedia;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.PKeyInRequest;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenportParameter;
import cj.studio.openport.annotations.CjOpenports;

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
                    DocMedia media
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
                    String docid
    ) throws CircuitException;

    @CjOpenport(usage = "管道文章取消点赞")
    void unlikeDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid
    ) throws CircuitException;

    @CjOpenport(usage = "管道文章评论。返回评论标识")
    String commentDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "评论内容", name = "content")
                    String content
    ) throws CircuitException;

    @CjOpenport(usage = "管道文章取消评论")
    void uncommentDocument(
            ISecuritySession securitySession,
            @CjOpenportParameter(usage = "文档标识", name = "docid")
                    String docid,
            @CjOpenportParameter(usage = "评论标识", name = "commentid")
                    String commentid
    ) throws CircuitException;

}
