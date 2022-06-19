package com.dabai.cms.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *  新闻大事件管理对象 cms_bigevent
 * @author
 * @create 2022-06-18 10:48
 */
@Data
public class BigEvent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 年份 */
    private String year;

    /** 日期 */
    private String date;

    /** 事件 */
    private String event;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("year", getYear())
                .append("date", getDate())
                .append("event", getEvent())
                .toString();
    }


}

