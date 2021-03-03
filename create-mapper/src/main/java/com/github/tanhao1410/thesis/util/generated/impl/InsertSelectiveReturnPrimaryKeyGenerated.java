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
 * 可选属性插入，返回自增主键
 * 2018-4-12 15:30:47
 * @author chenjinlong
 */
public class InsertSelectiveReturnPrimaryKeyGenerated implements AbstractGenerated {
	
	@Override
	public Method clientGenerated(String baseRecordType) {
		//insertSelectiveReturnPrimaryKey插入返回ID
		Method insertSelectiveReturnPrimaryKeyMethod = new Method();
		insertSelectiveReturnPrimaryKeyMethod.addJavaDocLine("/**");
		insertSelectiveReturnPrimaryKeyMethod.addJavaDocLine(" * 插入行记录返回影响行数，使用getId()获取插入行记录的Id");
		insertSelectiveReturnPrimaryKeyMethod.addJavaDocLine(" */");
		insertSelectiveReturnPrimaryKeyMethod.setName("insertSelectiveReturnPrimaryKey");
		insertSelectiveReturnPrimaryKeyMethod.setVisibility(JavaVisibility.PUBLIC);
		insertSelectiveReturnPrimaryKeyMethod.setReturnType(new FullyQualifiedJavaType("java.lang.Long"));
		insertSelectiveReturnPrimaryKeyMethod.addParameter(new Parameter(new FullyQualifiedJavaType(baseRecordType), Generator.QUERY_VAR_NAME));
    	
		return insertSelectiveReturnPrimaryKeyMethod;
	}

	@Override
	public XmlElement sqlMapDocumentGenerated(IntrospectedTable introspectedTable,String tableName) {
		String baseRecordType = introspectedTable.getBaseRecordType();//实体类全限定名
		
		XmlElement selectKeyXmlElement = new XmlElement("selectKey");
    	selectKeyXmlElement.addAttribute(new Attribute("keyProperty","id"));
    	selectKeyXmlElement.addAttribute(new Attribute("order","AFTER"));
    	selectKeyXmlElement.addAttribute(new Attribute("resultType","java.lang.Long"));
    	selectKeyXmlElement.addElement(new TextElement("select LAST_INSERT_ID() as id"));
    	
    	XmlElement beforeTrimXmlElement = new XmlElement("trim");
    	beforeTrimXmlElement.addAttribute(new Attribute("prefix","("));
    	beforeTrimXmlElement.addAttribute(new Attribute("suffix",")"));
    	beforeTrimXmlElement.addAttribute(new Attribute("suffixOverrides",","));
    	for(IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {//属性条件
    		XmlElement isNotNullElement = new XmlElement("if");
    		isNotNullElement.addAttribute(new Attribute("test",introspectedColumn.getJavaProperty()+" != null"));
    		isNotNullElement.addElement(new TextElement(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)+","));
    		
    		beforeTrimXmlElement.addElement(isNotNullElement);
    	}
    	
    	XmlElement afterTrimXmlElement = new XmlElement("trim");
    	afterTrimXmlElement.addAttribute(new Attribute("prefix"," values ("));
    	afterTrimXmlElement.addAttribute(new Attribute("suffix",")"));
    	afterTrimXmlElement.addAttribute(new Attribute("suffixOverrides",","));
    	for(IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {//属性条件
    		XmlElement isNotNullElement = new XmlElement("if");
    		isNotNullElement.addAttribute(new Attribute("test",introspectedColumn.getJavaProperty()+" != null"));
    		isNotNullElement.addElement(new TextElement(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn)+","));
    		
    		afterTrimXmlElement.addElement(isNotNullElement);
    	}
    	
    	XmlElement insertSelectiveReturnPrimaryKeyXmlElement = new XmlElement("insert");
    	insertSelectiveReturnPrimaryKeyXmlElement.addAttribute(new Attribute("id", "insertSelectiveReturnPrimaryKey"));
    	insertSelectiveReturnPrimaryKeyXmlElement.addAttribute(new Attribute("parameterType", baseRecordType));
    	insertSelectiveReturnPrimaryKeyXmlElement.addElement(selectKeyXmlElement);
    	insertSelectiveReturnPrimaryKeyXmlElement.addElement(new TextElement("insert into "+tableName));
    	insertSelectiveReturnPrimaryKeyXmlElement.addElement(beforeTrimXmlElement);
    	insertSelectiveReturnPrimaryKeyXmlElement.addElement(afterTrimXmlElement);
    	
		return insertSelectiveReturnPrimaryKeyXmlElement;
	}
}