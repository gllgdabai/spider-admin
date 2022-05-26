package com.dabai.cms.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文章管理对象 cms_article
 *
 * @author
 * @create 2022-04-03
 */
@Data
public class Article extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键，文章ID */
    private String id;

    /** 推荐到哪个专区。比如：头条、精选、最新、热门、评论最多等 */
    private String articleRegion;

    /** 原文作者 */
    private String author;

    /** 发布时间 */
    private String publishTime;

    /** 文章标题 */
    private String title;

    /** 关键词 */
    private String keywords;

    /** 摘要 */
    private String description;

    /** 封面图片 */
    private String coverImage;

    /** 转载标志 */
    private Integer copyFlag;

    /** 频道栏目ID */
    private String categoryId;

    /** 原始链接 */
    private String link;

    /** 静态化后url */
    private String staticUrl;

    /** 标签 */
    private String tags;

    /** 点击数 */
    private Long hit;

    /** 回复数 */
    private Long replyNum;

    /** 点赞数 */
    private Long upVote;

    /** 差评数 */
    private Long downVote;

    /** 热点标志 */
    private Integer hotFlag;

    /** 新增标志 */
    private Integer newFlag;

    /** 是否开启评论 */
    private String commentFlag;

    /** 置顶标志 */
    private Integer topFlag;

    /** 收藏数 */
    private Long favourite;

    /** 趴取任务的ID */
    private String missionId;

    /** 生成静态页面的模板(cms_template表中的name) */
    private String templateName;

    /** 状态标志 */
    private Integer available;

    /** 删除标志 */
    private Integer deleted;

    /** 附加字段1 */
    private String extra1;

    /** 附加字段2 */
    private String extra2;

    /** 附加字段3 */
    private String extra3;

    /** 文章内容 */
    private String content;

    /** 文章markdown源码 */
    private String content_markdown_source;

    /** 扩展字段。标签名称 */
    private String tags_name;

    /** 扩展字段。存放一个标签id */
    private String tag;

    /** 扩展字段 */
    private String extraName;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("articleRegion", getArticleRegion())
                .append("author", getAuthor())
                .append("publishTime", getPublishTime())
                .append("title", getTitle())
                .append("keywords", getKeywords())
                .append("description", getDescription())
                .append("coverImage", getCoverImage())
                .append("copyFlag", getCopyFlag())
                .append("categoryId", getCategoryId())
                .append("link", getLink())
                .append("staticUrl", getStaticUrl())
                .append("tags", getTags())
                .append("hit", getHit())
                .append("replyNum", getReplyNum())
                .append("upVote", getUpVote())
                .append("downVote", getDownVote())
                .append("hotFlag", getHotFlag())
                .append("newFlag", getNewFlag())
                .append("commentFlag", getCommentFlag())
                .append("topFlag", getTopFlag())
                .append("favourite", getFavourite())
                .append("missionId", getMissionId())
                .append("templateName", getTemplateName())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("available", getAvailable())
                .append("deleted", getDeleted())
                .append("extra1", getExtra1())
                .append("extra2", getExtra2())
                .append("extra3", getExtra3())
                .append("content", getContent())
                .append("content_markdown_source", getContent_markdown_source())
                .toString();
    }
}
