package com.github.tanhao1410.thesis.util.generated;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 生成Java Interface方法 和 Mapper xml 文件中某个方法实现
 * 2018-4-12 15:24:12
 * @author chenjinlong
 */
public interface AbstractGenerated {
	
	/**
	 * 生成接口java 接口文件的方法
	 * @param baseRecordType 实体类全限定名
	 * @return
	 */
	public Method clientGenerated(String baseRecordType);
	
	/**
	 * 生成接口对应的xml内容
	 * @param introspectedTable
	 * @param tableName
	 * @return
	 */
	public XmlElement sqlMapDocumentGenerated(IntrospectedTable introspectedTable,String tableName);
}