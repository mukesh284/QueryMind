package com.querymind.service.template;

import lombok.experimental.UtilityClass;
import java.util.HashMap;
import java.util.Map;

/**
 * Template library for common SQL patterns
 */
@UtilityClass
public class SQLTemplates {

    public static final Map<String, String> TEMPLATES = new HashMap<>();

    static {
        TEMPLATES.put("TOP_N_ROWS",
            "SELECT * FROM {table} ORDER BY {column} DESC LIMIT {n}");

        TEMPLATES.put("AGGREGATION",
            "SELECT {group_column}, {aggregate_function}({value_column}) as result " +
            "FROM {table} GROUP BY {group_column}");

        TEMPLATES.put("JOIN",
            "SELECT a.*, b.* FROM {table1} a " +
            "INNER JOIN {table2} b ON a.{join_key} = b.{join_key}");

        TEMPLATES.put("TIME_SERIES",
            "SELECT DATE_TRUNC({date_column}, {period}) as period, " +
            "{aggregate_function}({value_column}) as value " +
            "FROM {table} GROUP BY DATE_TRUNC({date_column}, {period})");

        TEMPLATES.put("RANKING",
            "SELECT *, ROW_NUMBER() OVER (PARTITION BY {partition_column} " +
            "ORDER BY {order_column} DESC) as rank FROM {table}");

        TEMPLATES.put("WINDOW_FUNCTION",
            "SELECT {column}, {aggregate_function}({value_column}) OVER " +
            "(PARTITION BY {partition_column} ORDER BY {order_column}) as running_total FROM {table}");
    }

    public String getTemplate(String templateName) {
        return TEMPLATES.getOrDefault(templateName, "");
    }
}

