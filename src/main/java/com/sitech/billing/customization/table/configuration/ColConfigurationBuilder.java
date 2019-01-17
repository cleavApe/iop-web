package com.sitech.billing.customization.table.configuration;

import com.alibaba.fastjson.JSONObject;
import com.sitech.billing.common.enums.ErrorMsgEnum;
import com.sitech.billing.common.exception.IopException;
import com.sitech.billing.customization.table.handler.fieldmapping.BaseFieldMappingHandler;
import com.sitech.billing.customization.table.handler.fieldmapping.FieldMappingHandlerFactory;
import com.sitech.billing.customization.table.model.Col;
import com.sitech.billing.customization.table.model.Field;
import com.sitech.billing.customization.table.model.FieldMapping;
import com.sitech.billing.customization.table.model.Table;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * TODO
 *
 * @author sunzhen
 * @date 2019/1/15 16:50
 */
public class ColConfigurationBuilder {

    private static BaseFieldMappingHandler fieldMappingHandler;

    public static List<Col> builder(TableConfiguration tableConfiguration,
                                    JdbcTemplate jdbcTemplate) {
        List<Col> cols = new ArrayList<>();
        List<Table> tables = tableConfiguration.getTables();
        for (Table table : tables) {
            List<Field> fields = table.getFields();
            for (Field field : fields) {
                Col col = new Col();
                col.setFieldName(field.getFieldName());
                col.setFieldDesc(field.getFieldDesc());
                col.setFieldType(field.getFieldType());
                col.setFieldOrder(field.getFieldOrder());
                col.setViewable(field.getViewable());
                col.setSortable(field.getSortable());
                col.setSearchable(field.getSearcher().getSearchable());
                col.setRequired(field.getSearcher().getRequired());
                col.setKeyFiled(field.getKeyFiled());

                JSONObject json = field.getMapJson();
                if (json.get("type") != null) {
                    col.setFieldMapping(getMapping(json));
                }
                cols.add(col);
            }
        }

        Collections.sort(cols, new Comparator<Col>() {
            @Override
            public int compare(Col o1, Col o2) {
                if (o1.getFieldOrder() > o2.getFieldOrder()) {
                    return 1;
                } else if (o1.getFieldOrder() < o2.getFieldOrder()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return cols;
    }

    public static List<FieldMapping> getMapping(JSONObject jsonObject) {
        try {
            fieldMappingHandler = FieldMappingHandlerFactory.getHandler(jsonObject);
            return fieldMappingHandler.handle(jsonObject);
        } catch (Exception e) {
            throw new IopException(ErrorMsgEnum.FIELD_MAPPING_HANDLER_NOT_EXIST);
        }
    }
}