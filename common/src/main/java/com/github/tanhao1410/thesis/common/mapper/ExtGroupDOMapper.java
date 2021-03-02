package com.github.tanhao1410.thesis.common.mapper;

import com.github.tanhao1410.thesis.common.domain.GroupDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 框架自动生成表模型和CRUD操作，勿修改；
 * 如特殊需要，请以Ext***Mapper自行扩展；
 * 生成日期 : 2021-03-02 13:25:30
 * @author ##tanhao##
 */
@Repository
public interface ExtGroupDOMapper {

    /**
     * 通用查询List方法，可设置查询属性，排序字段，分页参数 
     */
    List<GroupDO> selectAll();
}