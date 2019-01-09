package com.sitech.billing.customization.table.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 字段实体类
 *
 * @author sunzhen
 * @date 2019/1/8 14:00
 */
@Getter
@Setter
@ToString
public class Field {

    /* 字段名称 如：id、user_name */
    private String fieldName;
    /* 字段转义 如："user_name"转义为"用户名称" */
    private String fieldDesc;
    /* 字段是否显示 */
    private boolean viewable;
    /* 字段别名：sql语句中字段的别名*/
    private String fieldAlias;
    /* 字段顺位 */
    private int fieldOrder;
    /* 字段是否支持排序 */
    private boolean sortable;
    /* 字段查询条件信息 */
    private Searcher searcher;
    /* 字段是否为主键 */
    private boolean keyFiled;
    /* 字段映射（考虑多级联动的实现） */
    private FieldMapping fieldMapping;

}