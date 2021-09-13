package com.mapp.db.gen.entity;

import lombok.Data;

@Data
public class ColumnInfo {
    private String columnName;
    private String type;
    private String columnComnent;
    private String columnLenth;
    private String isNull;
    private String isPk;
    private int index;
    private String defaultValue;
    private String isAutoInc;
    private String digits;

}
