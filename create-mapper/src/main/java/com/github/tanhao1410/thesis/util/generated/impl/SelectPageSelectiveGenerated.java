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
public class SelectPageSelectiveGenerated implements AbstractGenerated {
	
	@Override
	public Method clientGenerated(String baseRecordType) {
		//增加selectPageSelective方法
		Parameter parameter = new Parameter(new FullyQualifiedJavaType(baseRecordType), Generator.QUERY_VAR_NAME);
    	parameter.addAnnotation("@Param(\""+ Generator.QUERY_VAR_NAME+"\")");
    	
    	Parameter pageParameter = new Parameter(new FullyQualifiedJavaType("org.springframework.data.domain.Pageable"),"pageable");
    	pageParameter.addAnnotation("@Param(\"pageable\")");
    	
		Method selectPageSelectiveMethod = new Method();//增加selectPage方法
		selectPageSelectiveMethod.addJavaDocLine("/**");
		selectPageSelectiveMethod.addJavaDocLine(" * 通用查询List方法，可设置查询属性，排序字段，分页参数 ");
		selectPageSelectiveMethod.addJavaDocLine(" */");
		selectPageSelectiveMethod.setName("selectPageSelective");
		selectPageSelectiveMethod.setVisibility(JavaVisibility.PUBLIC);
		selectPageSelectiveMethod.setReturnType(new FullyQualifiedJavaType("List<"+baseRecordType+">"));
		selectPageSelectiveMethod.addParameter(parameter);
		selectPageSelectiveMethod.addParameter(pageParameter);
		return selectPageSelectiveMethod;
	}

	@Override
	public XmlElement sqlMapDocumentGenerated(IntrospectedTable introspectedTable,String tableName) {
		XmlElement include = new XmlElement("include");
    	include.addAttribute(new Attribute("refid","Base_Column_List")); 
    	
    	/**
    	 * <where >  
         * <if test="accountId != null" >  
         *   and account_id = #{accountId,jdbcType=INTEGER}  
         * </if>  
         * </where>
    	 */
    	XmlElement whereXmlElement = new XmlElement("where");
    	for(IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {//属性条件
    		XmlElement isNotNullElement = new XmlElement("if");
    		isNotNullElement.addAttribute(new Attribute("test", Generator.QUERY_VAR_NAME+"."+introspectedColumn.getJavaProperty()+" != null"));
    		
    		StringBuilder sb = new StringBuilder();
    		sb.append(" and ");
    		sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
    		sb.append(" = ");
    		sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, Generator.QUERY_VAR_NAME+"."));
    		isNotNullElement.addElement(new TextElement(sb.toString()));
    		
    		whereXmlElement.addElement(isNotNullElement);
    	}
    	
    	/**
    	 * <if test=" order.property == 'id' || order.property == 'fund_code'" >
         *		${order.property} ${order.direction}
         *  </if>
    	 */
    	StringBuilder sb = new StringBuilder();
    	sb.append("order.property == 'id' || ");
    	for(int i=0;i<introspectedTable.getNonPrimaryKeyColumns().size();i++) {
    		IntrospectedColumn introspectedColumn = introspectedTable.getNonPrimaryKeyColumns().get(i);
    		
    		sb.append("order.property == '"+MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)+"' ");
    		if(i!=introspectedTable.getNonPrimaryKeyColumns().size()-1) {
    			sb.append(" || ");
    		}
    	}
    	
    	XmlElement pageSortForEachOrderElement = new XmlElement("if");
    	pageSortForEachOrderElement.addAttribute(new Attribute("test",sb.toString()));
    	pageSortForEachOrderElement.addElement(new TextElement(" ${order.property} ${order.direction} "));
    	
    	/**
    	 * 	<foreach collection="pageable.sort" item="order" separator="," >
    	 *    <if test=" order.property == 'id' || order.property == 'fund_code'" >
         *			${order.property} ${order.direction}
         *   </if>
         *  </foreach>
    	 */
    	XmlElement pageSortForEachElement = new XmlElement("foreach");
    	pageSortForEachElement.addAttribute(new Attribute("collection","pageable.sort"));
    	pageSortForEachElement.addAttribute(new Attribute("item","order"));
    	pageSortForEachElement.addAttribute(new Attribute("separator",","));
    	pageSortForEachElement.addElement(pageSortForEachOrderElement);
    	/**
    	 *  <if test="pageable.sort != null" >
    	 *   order by 
    	 *   <foreach collection="pageable.sort" item="order" separator="," >
    	 *    	<if test=" order.property == 'id' || order.property == 'fund_code'" >
         *			${order.property} ${order.direction}
         *  	</if>
         *  </foreach>
         *  </if>
    	 */
    	XmlElement pageSortElement = new XmlElement("if");//排序
    	pageSortElement.addAttribute(new Attribute("test","pageable.sort != null"));
    	pageSortElement.addElement(new TextElement(" order by "));
    	pageSortElement.addElement(pageSortForEachElement);
    	whereXmlElement.addElement(pageSortElement);
    	
    	/**
    	 *  <if test="pageable.offset >= 0 and pageable.pageSize > 0" >
    	 *   limit ${pageable.offset}, ${pageable.pageSize}
    	 *   </if>
    	 */
    	XmlElement pageLimitElement = new XmlElement("if");//分页
    	pageLimitElement.addAttribute(new Attribute("test","pageable.offset >= 0 and pageable.pageSize > 0"));
    	pageLimitElement.addElement(new TextElement("limit ${pageable.offset}, ${pageable.pageSize}"));
    	whereXmlElement.addElement(pageLimitElement);
    	
    	//组装
    	XmlElement selectPageXmlElement = new XmlElement("select");
    	selectPageXmlElement.addAttribute(new Attribute("id", "selectPageSelective"));
    	selectPageXmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));
    	selectPageXmlElement.addElement(new TextElement("select "));	 
    	selectPageXmlElement.addElement(include);
    	selectPageXmlElement.addElement(new TextElement(" from "+tableName));
    	selectPageXmlElement.addElement(whereXmlElement);
    	
		return selectPageXmlElement;
	}
}