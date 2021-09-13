package com.mapp.db.gen.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DBInfo {

    private String dbName;
    private String dbType;
    private Date createTime;
    private String version;
    private List<TableInfo> tableInfoList = new ArrayList<>();
}
