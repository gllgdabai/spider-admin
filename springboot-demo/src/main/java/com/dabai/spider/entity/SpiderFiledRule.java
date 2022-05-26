package com.dabai.spider.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 字段值处理规则对象 spider_filed_rule
 *
 * @author
 * @create 2022-04-03
 */
@Data
public class SpiderFiledRule {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 字段ID */
    private String fieldId;

    /** 数据处理规则 */
    private String processType;

    /** 替换正则 */
    private String replacereg;

    /** 替换内容 */
    private String replacement;

    /** 截取字符串目标 */
    private String substrTarget;

    /** 排序 */
    private Integer sort;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("fieldId", getFieldId())
                .append("processType", getProcessType())
                .append("replacereg", getReplacereg())
                .append("replacement", getReplacement())
                .append("substrTarget", getSubstrTarget())
                .append("sort", getSort())
                .toString();
    }
}
