package com.github.tanhao1410.thesis.util.generated.impl;

import com.github.tanhao1410.thesis.util.generated.AbstractGenerated;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 批量根据主键获取
 * 2018-4-12 15:30:47
 * @author chenjinlong
 */
public class BatchSelectByPrimaryKeyGenerated implements AbstractGenerated {

	@Override
	public Method clientGenerated(String baseRecordType) {
		//batchSelectByPrimaryKey，根据主键批量获取
    	Method batchSelectByPrimaryKeyMethod = new Method();
    	batchSelectByPrimaryKeyMethod.addJavaDocLine("/**");
    	batchSelectByPrimaryKeyMethod.addJavaDocLine(" * 根据主键批量获取");
    	batchSelectByPrimaryKeyMethod.addJavaDocLine(" */");
    	batchSelectByPrimaryKeyMethod.setName("batchSelectByPrimaryKey");
    	batchSelectByPrimaryKeyMethod.setVisibility(JavaVisibility.PUBLIC);
    	batchSelectByPrimaryKeyMethod.setReturnType(new FullyQualifiedJavaType("List<"+baseRecordType+">"));
    	batchSelectByPrimaryKeyMethod.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<Long>"),"pkIds"));
    	
		return batchSelectByPrimaryKeyMethod;
	}

	@Override
	public XmlElement sqlMapDocumentGenerated(IntrospectedTable introspectedTable,String tableName) {
		XmlElement include = new XmlElement("include");
    	include.addAttribute(new Attribute("refid","Base_Column_List")); 
    	
    	XmlElement batchSelectByPrimaryKeyForEachElement = new XmlElement("foreach");
    	batchSelectByPrimaryKeyForEachElement.addAttribute(new Attribute("collection","list"));
    	batchSelectByPrimaryKeyForEachElement.addAttribute(new Attribute("item","item"));
    	batchSelectByPrimaryKeyForEachElement.addAttribute(new Attribute("index","index"));
    	batchSelectByPrimaryKeyForEachElement.addAttribute(new Attribute("open","("));
    	batchSelectByPrimaryKeyForEachElement.addAttribute(new Attribute("separator",","));
    	batchSelectByPrimaryKeyForEachElement.addAttribute(new Attribute("close",")"));
    	batchSelectByPrimaryKeyForEachElement.addElement(new TextElement(" ${item} "));
    	
		XmlElement batchSelectByPrimaryKeyXmlElement = new XmlElement("select");
		batchSelectByPrimaryKeyXmlElement.addAttribute(new Attribute("id", "batchSelectByPrimaryKey"));
		batchSelectByPrimaryKeyXmlElement.addAttribute(new Attribute("parameterType","java.util.List"));
		batchSelectByPrimaryKeyXmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		batchSelectByPrimaryKeyXmlElement.addElement(new TextElement("select "));	 
		batchSelectByPrimaryKeyXmlElement.addElement(include);
		batchSelectByPrimaryKeyXmlElement.addElement(new TextElement(" from "+tableName+" where id in"));
		batchSelectByPrimaryKeyXmlElement.addElement(batchSelectByPrimaryKeyForEachElement);
		
		return batchSelectByPrimaryKeyXmlElement;
	}
}