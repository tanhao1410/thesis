package com.github.tanhao1410.thesis.util.generated.impl;

import com.github.tanhao1410.thesis.util.Generator;
import com.github.tanhao1410.thesis.util.generated.AbstractGenerated;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * 根据实体类字段，查询匹配行记录列表，排序分页
 * 2018-4-12 15:30:47
 * @author chenjinlong
 */
public class SelectAllGenerated implements AbstractGenerated {
	
	@Override
	public Method clientGenerated(String baseRecordType) {
		//增加selectPageSelective方法

    	
		Method selectPageSelectiveMethod = new Method();//增加selectPage方法
		selectPageSelectiveMethod.addJavaDocLine("/**");
		selectPageSelectiveMethod.addJavaDocLine(" * 查询所有");
		selectPageSelectiveMethod.addJavaDocLine(" */");
		selectPageSelectiveMethod.setName("selectAll");
		selectPageSelectiveMethod.setVisibility(JavaVisibility.PUBLIC);
		selectPageSelectiveMethod.setReturnType(new FullyQualifiedJavaType("List<"+baseRecordType+">"));

		return selectPageSelectiveMethod;
	}

	@Override
	public XmlElement sqlMapDocumentGenerated(IntrospectedTable introspectedTable,String tableName) {
		XmlElement include = new XmlElement("include");
    	include.addAttribute(new Attribute("refid","Base_Column_List"));
    	//组装
    	XmlElement selectPageXmlElement = new XmlElement("select");
    	selectPageXmlElement.addAttribute(new Attribute("id", "selectAll"));
    	selectPageXmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));

    	selectPageXmlElement.addElement(new TextElement("select "));	 
    	selectPageXmlElement.addElement(include);
    	selectPageXmlElement.addElement(new TextElement(" from "+tableName));

		return selectPageXmlElement;
	}
}