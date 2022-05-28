package com.dabai.spider.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * 爬虫字段对象 spider_field
 *
 * @author
 * @create 2022-04-03
 */
@Data
public class SpiderField extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 字段ID */
    private Long fieldId;

    /** 爬虫配置ID */
    private Long configId;

    /** 字段 */
    private String field;

    /** 字段名称 */
    private String fieldName;

    /** 提取类型 */
    private String extractType;

    /** 提取规则 */
    private String extractBy;

    /** 常量值 */
    private Integer constantValue;

    /** 元素的索引 */
    private String extractIndex;

    /** 处理规则 */
    private String processRuleId;

    /** 是否是根据元素取值 */
    private String extractAttrFlag;

    /** 根据哪个元素取值，属性名 */
    private String extractAttr;

    private List<SpiderFiledRule> fieldRules= Lists.newArrayList();//规则字段，存放该字段文本处理规则

    public List<SpiderFiledRule> getFieldRules() {
        return fieldRules;
    }

    public SpiderField setFieldRules(List<SpiderFiledRule> fieldRules) {
        if(CollectionUtils.isNotEmpty(fieldRules)){
            this.fieldRules .addAll(fieldRules);
        }
        return this;
    }
    public SpiderField addFieldRule(SpiderFiledRule rule) {
        if(rule!=null){
            this.fieldRules.add(rule);
        }
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("fieldId", getFieldId())
                .append("configId", getConfigId())
                .append("field", getField())
                .append("fieldName", getFieldName())
                .append("extractType", getExtractType())
                .append("extractBy", getExtractBy())
                .append("constantValue", getConstantValue())
                .append("extractIndex", getExtractIndex())
                .append("processRuleId", getProcessRuleId())
                .append("extractAttrFlag", getExtractAttrFlag())
                .append("extractAttr", getExtractAttr())
                .toString();
    }
}
