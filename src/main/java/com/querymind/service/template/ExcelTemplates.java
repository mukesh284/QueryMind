package com.querymind.service.template;

import lombok.experimental.UtilityClass;
import java.util.HashMap;
import java.util.Map;

/**
 * Template library for common Excel formulas
 */
@UtilityClass
public class ExcelTemplates {

    public static final Map<String, String> TEMPLATES = new HashMap<>();

    static {
        TEMPLATES.put("SUMIF",
            "=SUMIF({range}, {criteria}, {sum_range})");

        TEMPLATES.put("COUNTIF",
            "=COUNTIF({range}, {criteria})");

        TEMPLATES.put("AVERAGEIF",
            "=AVERAGEIF({range}, {criteria}, {average_range})");

        TEMPLATES.put("VLOOKUP",
            "=VLOOKUP({lookup_value}, {table_array}, {col_index_num}, [range_lookup])");

        TEMPLATES.put("XLOOKUP",
            "=XLOOKUP({lookup_value}, {lookup_array}, {return_array}, [if_not_found], [match_mode], [search_mode])");

        TEMPLATES.put("INDEX_MATCH",
            "=INDEX({return_array}, MATCH({lookup_value}, {lookup_array}, 0))");

        TEMPLATES.put("CONCATENATE",
            "=CONCATENATE({text1}, {text2}, {text3})");

        TEMPLATES.put("IF_NESTED",
            "=IF({condition1}, {value1}, IF({condition2}, {value2}, {value3}))");

        TEMPLATES.put("PIVOT_SIMULATION",
            "=SUMIFS({sum_range}, {criteria_range1}, {criteria1}, {criteria_range2}, {criteria2})");
    }

    public String getTemplate(String templateName) {
        return TEMPLATES.getOrDefault(templateName, "");
    }
}

