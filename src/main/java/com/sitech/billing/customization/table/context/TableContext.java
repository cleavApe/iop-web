package com.sitech.billing.customization.table.context;

import com.github.pagehelper.PageInfo;
import com.sitech.billing.customization.table.configuration.ViewConfiguration;
import com.sitech.billing.customization.table.configuration.ViewConfigurationBuilder;
import com.sitech.billing.customization.table.configuration.TableConfiguration;
import com.sitech.billing.customization.table.configuration.TableConfigurationBuilder;
import com.sitech.billing.customization.table.execute.BaseExecute;
import com.sitech.billing.customization.table.execute.ExecuteBuilder;
import com.sitech.billing.customization.table.model.request.FieldOrder;
import com.sitech.billing.customization.table.model.request.FieldValue;
import com.sitech.billing.customization.table.model.request.RequestPageInfo;

import com.sitech.billing.customization.table.model.request.UpdateAndInsertParam;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * 定制化图表上下文
 *
 * @author sunzhen
 * @date 2019/1/8 14:30
 */
public class TableContext {
    private TableConfiguration tableConfiguration;
    private BaseExecute execute;
    private ViewConfiguration viewConfiguration;

    private TableContext(Integer id, JdbcTemplate jdbcTemplate, List<FieldValue> fieldValues,
                         List<FieldOrder> fieldOrders, RequestPageInfo pageInfo, String dbDialect) {
        this.tableConfiguration = TableConfigurationBuilder.build(id);
        execute = ExecuteBuilder.build(tableConfiguration, jdbcTemplate, fieldValues, fieldOrders, pageInfo, dbDialect);
    }

    public TableContext(Integer id, JdbcTemplate jdbcTemplate) {
        this.tableConfiguration = TableConfigurationBuilder.build(id);
        this.viewConfiguration = ViewConfigurationBuilder.build(tableConfiguration, jdbcTemplate);
    }

    public ViewConfiguration getViewConfiguration() {
        return this.viewConfiguration;
    }

    public void insert(List<UpdateAndInsertParam> list) {
        execute.insert(list);
    }

    public void update(List<UpdateAndInsertParam> list) {
        execute.update(list);
    }

    public void delete(List<List<UpdateAndInsertParam>> list) {
        execute.delete(list);
    }

    public List<Map<String, Object>> query() {
        return execute.query();
    }

    public PageInfo<Map<String, Object>> queryByPage() {
        PageInfo<Map<String, Object>> pageInfo = execute.queryByPage();
        return pageInfo;
    }

    public static class Builder {
        private JdbcTemplate jdbcTemplate;
        private String dbDialect;
        private Integer id;
        private List<FieldValue> fieldValues;
        private List<FieldOrder> fieldOrders;
        private RequestPageInfo pageInfo;

        public Builder dbDialect(String dbDialect) {
            this.dbDialect = dbDialect;
            return this;
        }

        public Builder jdbc(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            return this;
        }

        public Builder tableConfig(Integer id) {
            this.id = id;
            return this;
        }

        public Builder fieldValues(List<FieldValue> fieldValues) {
            this.fieldValues = fieldValues;
            return this;
        }

        public Builder fieldOrders(List<FieldOrder> fieldOrders) {
            this.fieldOrders = fieldOrders;
            return this;
        }

        public Builder pageInfo(RequestPageInfo pageInfo) {
            this.pageInfo = pageInfo;
            return this;
        }

        public TableContext build() {
            return new TableContext(this.id, this.jdbcTemplate, this.fieldValues,
                    this.fieldOrders, this.pageInfo, this.dbDialect);
        }

        public TableContext buildView() {
            return new TableContext(this.id, this.jdbcTemplate);
        }
    }
}
