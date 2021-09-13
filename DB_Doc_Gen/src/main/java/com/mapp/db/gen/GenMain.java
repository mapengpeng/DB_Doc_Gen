package com.mapp.db.gen;

import com.mapp.db.gen.util.DocGenerator;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;


@Slf4j
public class GenMain {

    public static void main(String[] args) throws IOException {

        log.info(">>>=============== 开始获取数据库信息  =======================");

        DocGenerator generator = new DocGenerator();

        generator.generator();

        log.info(">>>=============== 生成完毕！  =======================");

    }
}
