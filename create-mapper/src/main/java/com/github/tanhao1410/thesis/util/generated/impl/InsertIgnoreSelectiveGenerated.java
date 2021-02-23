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
 * 
 * 2019-8-29 13:50:00
 * @author chenjinlong
 */
public class InsertIgnoreSelectiveGenerated implements AbstractGenerated {
	
	
	@Override
	public Method clientGenerated(String baseRecordType) {
		//
		Method insertIgnoreSelectiveMethod = new Method();
		insertIgnoreSelectiveMethod.addJavaDocLine("/**");
		insertIgnoreSelectiveMethod.addJavaDocLine(" * insertSelective的增强版，插入成功返回1，失败返回0");
		insertIgnoreSelectiveMethod.addJavaDocLine(" * 如果插入的字段中有UNIQUE KEY, KEY已经存在则不予插入返回0，不会抛出DuplicateKeyException");
		insertIgnoreSelectiveMethod.addJavaDocLine(" */");
		insertIgnoreSelectiveMethod.setName("insertIgnoreSelective");
		insertIgnoreSelectiveMethod.setVisibility(JavaVisibility.PUBLIC);
		insertIgnoreSelectiveMethod.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));
		insertIgnoreSelectiveMethod.addParameter(new Parameter(new FullyQualifiedJavaType(baseRecordType), Generator.QUERY_VAR_NAME));
    	
		return insertIgnoreSelectiveMethod;
	}

	@Override
	public XmlElement sqlMapDocumentGenerated(IntrospectedTable introspectedTable,String tableName) {
		//实体类全限定名
		String baseRecordType = introspectedTable.getBaseRecordType();
    	
    	XmlElement beforeTrimXmlElement = new XmlElement("trim");
    	beforeTrimXmlElement.addAttribute(new Attribute("prefix","("));
    	beforeTrimXmlElement.addAttribute(new Attribute("suffix",")"));
    	beforeTrimXmlElement.addAttribute(new Attribute("suffixOverrides",","));
    	for(IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
    		//属性条件
    		XmlElement isNotNullElement = new XmlElement("if");
    		isNotNullElement.addAttribute(new Attribute("test",introspectedColumn.getJavaProperty()+" != null"));
    		isNotNullElement.addElement(new TextElement(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)+","));
    		
    		beforeTrimXmlElement.addElement(isNotNullElement);
    	}
    	
    	XmlElement afterTrimXmlElement = new XmlElement("trim");
    	afterTrimXmlElement.addAttribute(new Attribute("prefix"," values ("));
    	afterTrimXmlElement.addAttribute(new Attribute("suffix",")"));
    	afterTrimXmlElement.addAttribute(new Attribute("suffixOverrides",","));
    	for(IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
    		//属性条件
    		XmlElement isNotNullElement = new XmlElement("if");
    		isNotNullElement.addAttribute(new Attribute("test",introspectedColumn.getJavaProperty()+" != null"));
    		isNotNullElement.addElement(new TextElement(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn)+","));
    		
    		afterTrimXmlElement.addElement(isNotNullElement);
    	}
    	
    	XmlElement insertIgnoreSelectiveXmlElement = new XmlElement("insert");
    	insertIgnoreSelectiveXmlElement.addAttribute(new Attribute("id", "insertIgnoreSelective"));
    	insertIgnoreSelectiveXmlElement.addAttribute(new Attribute("parameterType", baseRecordType));
    	insertIgnoreSelectiveXmlElement.addElement(new TextElement("insert ignore into "+tableName));
    	insertIgnoreSelectiveXmlElement.addElement(beforeTrimXmlElement);
    	insertIgnoreSelectiveXmlElement.addElement(afterTrimXmlElement);
    	
		return insertIgnoreSelectiveXmlElement;
	}
}