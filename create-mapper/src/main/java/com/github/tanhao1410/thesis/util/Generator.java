package com.github.tanhao1410.thesis.util;

import org.mybatis.generator.api.ShellRunner;

/**
 * main 函数生产mybatis相关文件
 * 2018-4-12 15:20:19
 * @author chenjinlong
 */
public class Generator {
	//生成方法参数变量名称
	public static final String QUERY_VAR_NAME = "record";
	public static final String QUERY_VAR_NAMES = "records";
	
	//table逻辑删除列名
	public static final String LOGIC_DELETE_COLUMN_NAME = "isactive";
	
	//生成代码，AUTHOR信息
	public static final String AUTHOR_NAME = "##tanhao##";
	
	
	public static void main(String[] args) {
		String configFilePath = Generator.class.getClassLoader().getResource("generatorConfig_school.xml").getFile();
		String[] arg = {"-configfile", configFilePath, "-overwrite" };
		ShellRunner.main(arg);
	}
}