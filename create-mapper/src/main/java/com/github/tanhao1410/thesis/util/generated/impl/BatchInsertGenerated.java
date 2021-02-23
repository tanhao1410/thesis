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

import java.util.List;

/**
 * 批量插入
 * 2018-4-12 15:30:47
 * @author chenjinlong
 */
public class BatchInsertGenerated implements AbstractGenerated {
	
	@Override
	public Method clientGenerated(String baseRecordType) {
		Method batchInsertMethod = new Method();//batchInsert批量插入
    	batchInsertMethod.addJavaDocLine("/**");
    	batchInsertMethod.addJavaDocLine(" * 批量插入行数据返回插入行数");
    	batchInsertMethod.addJavaDocLine(" */");
    	batchInsertMethod.setName("batchInsert");
    	batchInsertMethod.setVisibility(JavaVisibility.PUBLIC);
    	batchInsertMethod.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));
    	batchInsertMethod.addParameter(new Parameter(new FullyQualifiedJavaType("List<"+baseRecordType+">"), Generator.QUERY_VAR_NAMES));
    	
		return batchInsertMethod;
	}

	@Override
	public XmlElement sqlMapDocumentGenerated(IntrospectedTable introspectedTable,String tableName) {
		String baseRecordType = introspectedTable.getBaseRecordType();//实体类全限定名
		
		XmlElement batchInsertXmlElement = new XmlElement("insert");
		batchInsertXmlElement.addAttribute(new Attribute("id", "batchInsert"));
		batchInsertXmlElement.addAttribute(new Attribute("parameterType", baseRecordType));
    	
    	StringBuilder sbKey = new StringBuilder();
    	sbKey.append("insert into "+tableName+" (");
    	List<IntrospectedColumn> introspectedColumns = introspectedTable.getNonPrimaryKeyColumns();
    	for(int i=0;i<introspectedColumns.size();i++) {//属性条件
    		IntrospectedColumn introspectedColumn = introspectedColumns.get(i);
    		sbKey.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
    		if(i!=introspectedColumns.size()-1) {
    			sbKey.append(",");
    		}
    	}
    	sbKey.append(" ) values ");
    	batchInsertXmlElement.addElement(new TextElement(sbKey.toString()));
    	
    	XmlElement batchInsertForEachElement = new XmlElement("foreach");
    	batchInsertForEachElement.addAttribute(new Attribute("collection","list"));
    	batchInsertForEachElement.addAttribute(new Attribute("item","item"));
    	batchInsertForEachElement.addAttribute(new Attribute("index","index"));
    	batchInsertForEachElement.addAttribute(new Attribute("separator",","));
    	StringBuilder sbValue = new StringBuilder();
    	sbValue.append(" (");
    	for(int i=0;i<introspectedColumns.size();i++) {//属性条件
    		IntrospectedColumn introspectedColumn = introspectedColumns.get(i);
    		sbValue.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn,"item."));
    		if(i!=introspectedColumns.size()-1) {
    			sbValue.append(",");
    		}
    	}
    	sbValue.append(" ) ");
    	batchInsertForEachElement.addElement(new TextElement(sbValue.toString()));
    	
    	batchInsertXmlElement.addElement(batchInsertForEachElement);
		return batchInsertXmlElement;
	}
}