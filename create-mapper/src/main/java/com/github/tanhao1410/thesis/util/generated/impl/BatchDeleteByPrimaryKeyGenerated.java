package com.github.tanhao1410.thesis.util.generated.impl;

import com.github.tanhao1410.thesis.util.Generator;
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
 * 批量逻辑删除
 * 2018-4-12 15:30:47
 * @author chenjinlong
 */
public class BatchDeleteByPrimaryKeyGenerated implements AbstractGenerated {

	@Override
	public Method clientGenerated(String baseRecordType) {
		//批量删除,batchDeleteByPrimaryKey
		Method batchDeleteByPrimaryKeyMethod = new Method();
    	batchDeleteByPrimaryKeyMethod.addJavaDocLine("/**");
    	batchDeleteByPrimaryKeyMethod.addJavaDocLine(" * 批量逻辑删除,set is_deleted=1返回影响行数");
    	batchDeleteByPrimaryKeyMethod.addJavaDocLine(" */");
    	batchDeleteByPrimaryKeyMethod.setName("batchDeleteByPrimaryKey");
    	batchDeleteByPrimaryKeyMethod.setVisibility(JavaVisibility.PUBLIC);
    	batchDeleteByPrimaryKeyMethod.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));
    	batchDeleteByPrimaryKeyMethod.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<Long>"),"pkIds"));
    	
		return batchDeleteByPrimaryKeyMethod;
	}

	@Override
	public XmlElement sqlMapDocumentGenerated(IntrospectedTable introspectedTable,String tableName) {
		XmlElement batchDeleteByPrimaryKeyForEachElement = new XmlElement("foreach");
    	batchDeleteByPrimaryKeyForEachElement.addAttribute(new Attribute("collection","list"));
    	batchDeleteByPrimaryKeyForEachElement.addAttribute(new Attribute("item","item"));
    	batchDeleteByPrimaryKeyForEachElement.addAttribute(new Attribute("index","index"));
    	batchDeleteByPrimaryKeyForEachElement.addAttribute(new Attribute("open","("));
    	batchDeleteByPrimaryKeyForEachElement.addAttribute(new Attribute("separator",","));
    	batchDeleteByPrimaryKeyForEachElement.addAttribute(new Attribute("close",")"));
    	batchDeleteByPrimaryKeyForEachElement.addElement(new TextElement(" ${item} "));
    	
    	XmlElement batchDeleteByPrimaryKeyXmlElement = new XmlElement("update");
    	batchDeleteByPrimaryKeyXmlElement.addAttribute(new Attribute("id", "batchDeleteByPrimaryKey"));
    	batchDeleteByPrimaryKeyXmlElement.addAttribute(new Attribute("parameterType","java.util.List"));
    	batchDeleteByPrimaryKeyXmlElement.addElement(new TextElement("update "+ tableName + " set "+Generator.LOGIC_DELETE_COLUMN_NAME+" = 0 where id in "));
    	batchDeleteByPrimaryKeyXmlElement.addElement(batchDeleteByPrimaryKeyForEachElement);
    	batchDeleteByPrimaryKeyXmlElement.addElement(new TextElement("and "+ Generator.LOGIC_DELETE_COLUMN_NAME+" = 1"));
    	
		return batchDeleteByPrimaryKeyXmlElement;
	}
}