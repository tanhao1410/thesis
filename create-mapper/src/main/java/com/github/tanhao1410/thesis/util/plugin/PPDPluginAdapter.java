package com.github.tanhao1410.thesis.util.plugin;

import com.github.tanhao1410.thesis.util.Generator;
import com.github.tanhao1410.thesis.util.generated.AbstractGenerated;
import com.github.tanhao1410.thesis.util.generated.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 扩展mybatis插件
 * 2018-4-12 16:13:15
 * @author chenjinlong
 */
@Slf4j
public class PPDPluginAdapter extends PluginAdapter {
	
	private List<AbstractGenerated> abstractGenerateds = new ArrayList<AbstractGenerated>();
	
	public PPDPluginAdapter() {
		abstractGenerateds.add(new SelectCountSelectiveGenerated());
		abstractGenerateds.add(new SelectPageSelectiveGenerated());
		abstractGenerateds.add(new BatchDeleteByPrimaryKeyGenerated());
		abstractGenerateds.add(new InsertSelectiveReturnPrimaryKeyGenerated());
		abstractGenerateds.add(new BatchInsertGenerated());
		abstractGenerateds.add(new BatchSelectByPrimaryKeyGenerated());
		abstractGenerateds.add(new InsertIgnoreSelectiveGenerated());
		abstractGenerateds.add(new SelectAllGenerated());
	}
	
	@Override  
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//生成实体，增加注解setter，getter，toString注解
		Set<FullyQualifiedJavaType> importAnnotations = new HashSet<FullyQualifiedJavaType>();
		importAnnotations.add(new FullyQualifiedJavaType("lombok.Getter"));
		importAnnotations.add(new FullyQualifiedJavaType("lombok.Setter"));
		importAnnotations.add(new FullyQualifiedJavaType("lombok.ToString"));
		topLevelClass.addImportedTypes(importAnnotations);
		
		topLevelClass.addAnnotation("@Setter");
		topLevelClass.addAnnotation("@Getter");
		topLevelClass.addAnnotation("@ToString");
		
		topLevelClass.addJavaDocLine("/**");
		topLevelClass.addJavaDocLine(" * 框架自动生成表模型和CRUD操作，勿修改；");
		topLevelClass.addJavaDocLine(" * 如特殊需要，请以Ext***Mapper自行扩展；");
		topLevelClass.addJavaDocLine(" * 生成日期 : "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		topLevelClass.addJavaDocLine(" * @author "+ Generator.AUTHOR_NAME);
		topLevelClass.addJavaDocLine(" */");
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);  
    }
	@Override  
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
    		IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		//不生成属性getter
		return false;
    }  
	@Override  
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
    		IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		//不生成属性setter
		return false;
    }
    @Override  
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    	//生成接口java文件
    	interfaze.addJavaDocLine("/**");
    	interfaze.addJavaDocLine(" * 框架自动生成表模型和CRUD操作，勿修改；");
    	interfaze.addJavaDocLine(" * 如特殊需要，请以Ext***Mapper自行扩展；");
    	interfaze.addJavaDocLine(" * 生成日期 : "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    	interfaze.addJavaDocLine(" * @author "+ Generator.AUTHOR_NAME);
    	interfaze.addJavaDocLine(" */");
    	
    	interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.data.domain.Pageable"));
    	interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
    	interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
    	interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
    	
    	interfaze.addAnnotation("@Repository");
    	
    	String baseRecordType = introspectedTable.getBaseRecordType();//全限定名
    	
    	for(AbstractGenerated abstractGenerated : abstractGenerateds) {
    		Method method = abstractGenerated.clientGenerated(baseRecordType);
    		interfaze.addMethod(method);
    	}
        return super.clientGenerated(interfaze,topLevelClass,introspectedTable);  
    }
    @Override  
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
    	//生产xml文件
    	String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();//表名
    	String baseRecordType = introspectedTable.getBaseRecordType();//实体类全限定名
    	
    	List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
    	//log.info("tableName : [{}], columns size : [{}], baseRecordType : [{}]",tableName,columns.size(),baseRecordType);
    	
    	XmlElement rootElement = document.getRootElement();//根节点
    	
    	for(AbstractGenerated abstractGenerated : abstractGenerateds) {
    		XmlElement xmlElement = abstractGenerated.sqlMapDocumentGenerated(introspectedTable,tableName);
    		rootElement.addElement(xmlElement);
    	}
    	
    	return super.sqlMapDocumentGenerated(document,introspectedTable);
    }
	@Override
	public boolean validate(List<String> arg0) {
		//是否有效
		return true;
	}
}