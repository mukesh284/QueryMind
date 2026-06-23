package com.querymind.service.template;

import lombok.experimental.UtilityClass;
import java.util.HashMap;
import java.util.Map;

/**
 * Template library for common Power BI DAX patterns
 */
@UtilityClass
public class DAXTemplates {

    public static final Map<String, String> TEMPLATES = new HashMap<>();

    static {
        TEMPLATES.put("SIMPLE_MEASURE",
            "Measure = SUM(TableName[ColumnName])");

        TEMPLATES.put("CALCULATE_FILTER",
            "Measure = CALCULATE(SUM(TableName[Amount]), FILTER(TableName, TableName[Status]=\"Active\"))");

        TEMPLATES.put("YOY_GROWTH",
            "YOY_Growth = DIVIDE([Current Year Sales], [Previous Year Sales]) - 1");

        TEMPLATES.put("RUNNING_TOTAL",
            "Running_Total = CALCULATE(SUM(TableName[Amount]), " +
            "FILTER(ALLEXCEPT(TableName, TableName[Date]), TableName[Date] <= MAX(TableName[Date])))");

        TEMPLATES.put("RANK_CALCULATION",
            "Rank = RANKX(ALL(TableName[Product]), [Total Sales], , DESC)");

        TEMPLATES.put("PERCENTAGE_OF_TOTAL",
            "Pct_of_Total = DIVIDE([Current Measure], CALCULATE([Current Measure], ALL(TableName)))");

        TEMPLATES.put("CONDITIONAL_MEASURE",
            "Conditional = IF([Total Sales] > 100000, \"High\", IF([Total Sales] > 50000, \"Medium\", \"Low\"))");

        TEMPLATES.put("MULTI_TABLE_JOIN",
            "Measure = SUMPRODUCT(TableName1[Amount], RELATED(TableName2[Factor]))");
    }

    public String getTemplate(String templateName) {
        return TEMPLATES.getOrDefault(templateName, "");
    }
}

