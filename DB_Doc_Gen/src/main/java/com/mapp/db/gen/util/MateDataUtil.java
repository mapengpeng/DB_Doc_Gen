package com.mapp.db.gen.util;

import cn.hutool.core.util.BooleanUtil;
import com.mapp.db.gen.entity.ColumnInfo;
import com.mapp.db.gen.entity.DBInfo;
import com.mapp.db.gen.entity.TableInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 获取数据库，表，列元数据
 *
 * @author mapp
 */
public class MateDataUtil {


    public static DBInfo getDbInfo() {

        Connection connection = JdbcUtil.getConnection();
        DBInfo dbInfo =  new DBInfo();
        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();

            dbInfo.setDbName(catalog);
            dbInfo.setDbType(metaData.getDatabaseProductName());
            dbInfo.setVersion(metaData.getDatabaseProductVersion());

            // 获取表信息
            try (ResultSet rs = metaData.getTables(catalog, schema, null, new String[]{"TABLE"})) {
                while (rs.next()) {
                    TableInfo tableInfo = new TableInfo();
                    tableInfo.setTableName(rs.getString("TABLE_NAME"));
                    tableInfo.setTableComment(rs.getString("REMARKS"));

                    // 获得主键
                    try (ResultSet pkRs = metaData.getPrimaryKeys(catalog, schema, tableInfo.getTableName())) {
                        if (pkRs != null) {
                            while (pkRs.next()) {
                                tableInfo.getPkName().add(pkRs.getString("COLUMN_NAME"));
                            }
                        }
                    }

                    // 获取列信息
                    try (ResultSet columnMetaRs = metaData.getColumns(catalog, null, tableInfo.getTableName(), null)) {
                       if (columnMetaRs != null) {
                           int index = 1;
                           while (columnMetaRs.next()) {
                               ColumnInfo columnInfo = new ColumnInfo();
                               columnInfo.setColumnName(columnMetaRs.getString("COLUMN_NAME").toUpperCase());

                               columnInfo.setType(columnMetaRs.getString("TYPE_NAME"));
                               columnInfo.setColumnLenth(String.valueOf(columnMetaRs.getInt("COLUMN_SIZE")));
                               columnInfo.setIsNull(columnMetaRs.getBoolean("NULLABLE") ? "true" : "false");
                               columnInfo.setColumnComnent(columnMetaRs.getString("REMARKS"));
                               columnInfo.setDefaultValue(columnMetaRs.getString("COLUMN_DEF"));

                               if (tableInfo.getPkName().contains(columnInfo.getColumnName())) {
                                   columnInfo.setIsPk("true");
                               }
                               // 保留小数位数
                               try {
                                   int decimal_digits = columnMetaRs.getInt("DECIMAL_DIGITS");
                                   if (decimal_digits != 0) {
                                       columnInfo.setDigits(String.valueOf(decimal_digits));
                                   }
                               } catch (SQLException ignore) {

                               }

                               // 是否自增
                               try {
                                   String auto = columnMetaRs.getString("IS_AUTOINCREMENT");
                                   if (BooleanUtil.toBoolean(auto)) {
                                       columnInfo.setIsAutoInc("true");
                                   }
                               } catch (SQLException ignore) {

                               }
                               columnInfo.setIndex(index++);
                               tableInfo.getColumnInfoList().add(columnInfo);
                           }
                       }
                    }

                    dbInfo.getTableInfoList().add(tableInfo);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(connection);
        }


        return dbInfo;
    }
}
