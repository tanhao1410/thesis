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
 * 根据实体类字段，查询匹配行记录数量
 * 2018-4-12 15:30:47
 * @author chenjinlong
 */
public class SelectCountSelectiveGenerated implements AbstractGenerated {
	
	@Override
	public Method clientGenerated(String baseRecordType) {
		//增加selectCountSelective方法
		Parameter parameter = new Parameter(new FullyQualifiedJavaType(baseRecordType), Generator.QUERY_VAR_NAME);
    	parameter.addAnnotation("@Param(\""+Generator.QUERY_VAR_NAME+"\")");
    	
		Method selectCountSelectiveMethod = new Method();
		selectCountSelectiveMethod.addJavaDocLine("/**");
		selectCountSelectiveMethod.addJavaDocLine(" * 通用查询行数方法，可设置查询属性");
		selectCountSelectiveMethod.addJavaDocLine(" */");
		selectCountSelectiveMethod.setName("selectCountSelective");
		selectCountSelectiveMethod.setVisibility(JavaVisibility.PUBLIC);
		selectCountSelectiveMethod.setReturnType(new FullyQualifiedJavaType("java.lang.Long"));
		selectCountSelectiveMethod.addParameter(parameter);
		return selectCountSelectiveMethod;
	}

	@Override
	public XmlElement sqlMapDocumentGenerated(IntrospectedTable introspectedTable,String tableName) {
		/**
    	 * <where >  
         * <if test="accountId != null" >  
         *   and account_id = #{accountId,jdbcType=INTEGER}  
         * </if>  
         * </where>
    	 */
    	XmlElement whereXmlElement = new XmlElement("where");
    	for(IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
    		//属性条件
    		XmlElement isNotNullElement = new XmlElement("if");
    		isNotNullElement.addAttribute(new Attribute("test",Generator.QUERY_VAR_NAME+"."+introspectedColumn.getJavaProperty()+" != null"));
    		
    		StringBuilder sb = new StringBuilder();
    		sb.append(" and ");
    		sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
    		sb.append(" = ");
    		sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn,Generator.QUERY_VAR_NAME+"."));
    		isNotNullElement.addElement(new TextElement(sb.toString()));
    		
    		whereXmlElement.addElement(isNotNullElement);
    	}
    	
    	XmlElement selectCountXmlElement = new XmlElement("select");
    	selectCountXmlElement.addAttribute(new Attribute("id", "selectCountSelective"));
    	selectCountXmlElement.addAttribute(new Attribute("resultType", "java.lang.Long"));
    	selectCountXmlElement.addElement(new TextElement("select count(1) from "+ tableName));
    	selectCountXmlElement.addElement(whereXmlElement);
    	
		return selectCountXmlElement;
	}
}