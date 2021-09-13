import cn.hutool.core.io.resource.ResourceUtil;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.mapp.db.gen.entity.ColumnInfo;
import com.mapp.db.gen.entity.TableInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocTest {

    public static void main(String[] args) throws IOException {
        Map<String, Object> parms = new HashMap<String, Object>();

        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
//
        Configure config = Configure.builder().bind("tables", policy).bind("columnInfoList", policy).build();

        TableInfo table1 = new TableInfo();
        table1.setTableName("SYSTEM");
        table1.setTableComment("系统管理表");

        TableInfo table2 = new TableInfo();
        table2.setTableName("SYS_USER");
        table2.setTableComment("系统用户表");

        ColumnInfo columnInfo1 = new ColumnInfo();
        columnInfo1.setColumnName("NAME");
        columnInfo1.setColumnComnent("姓名");
        columnInfo1.setIndex(1);
        columnInfo1.setColumnLenth("32");
        columnInfo1.setType("Varchar");

        ColumnInfo columnInfo2 = new ColumnInfo();
        columnInfo2.setColumnName("SEX");
        columnInfo2.setType("Varchar");
        columnInfo2.setColumnComnent("性别");
        columnInfo2.setIndex(2);
        columnInfo2.setColumnLenth("32");

        List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();
        columnInfos.add(columnInfo1);
        columnInfos.add(columnInfo2);

        List<TableInfo> tableInfos = new ArrayList<TableInfo>();
        tableInfos.add(table1);
        tableInfos.add(table2);

        table1.setColumnInfoList(columnInfos);
        table2.setColumnInfoList(columnInfos);


        parms.put("dbName", "系统管理数据库");
        parms.put("tables", tableInfos);


        XWPFTemplate template = XWPFTemplate.compile(ResourceUtil.getStream("template.docx"), config).render(parms);



        template.writeAndClose(new FileOutputStream("output.docx"));
    }
}
