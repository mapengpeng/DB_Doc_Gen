package com.mapp.db.gen.util;

import cn.hutool.core.io.resource.ResourceUtil;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.mapp.db.gen.entity.DBInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文档生成器
 *
 * @author mapp
 */
public class DocGenerator {

    public void generator() throws IOException {

        DBInfo dbInfo = MateDataUtil.getDbInfo();

        Map<String, Object> parms = new HashMap<String, Object>();

        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder().bind("tables", policy).bind("columnInfoList", policy).build();



        parms.put("dbName", dbInfo.getDbName());
        parms.put("dbType", dbInfo.getDbType());
        parms.put("version", dbInfo.getVersion());
        parms.put("tables", dbInfo.getTableInfoList());


        XWPFTemplate template = XWPFTemplate.compile(ResourceUtil.getStream("template.docx"), config).render(parms);
        template.writeAndClose(new FileOutputStream(dbInfo.getDbName() + "数据库文档" + ".docx"));

    }

}
