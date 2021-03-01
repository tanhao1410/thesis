package com.github.tanhao1410.thesis.common.mapper;

import com.github.tanhao1410.thesis.common.domain.MonitoringItemDO;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * 框架自动生成表模型和CRUD操作，勿修改；
 * 如特殊需要，请以Ext***Mapper自行扩展；
 * 生成日期 : 2021-03-01 16:27:59
 * @author ##tanhao##
 */
@Repository
public interface MonitoringItemDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MonitoringItemDO record);

    int insertSelective(MonitoringItemDO record);

    MonitoringItemDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MonitoringItemDO record);

    int updateByPrimaryKey(MonitoringItemDO record);

    /**
     * 通用查询行数方法，可设置查询属性
     */
    Long selectCountSelective(@Param("record") MonitoringItemDO record);

    /**
     * 通用查询List方法，可设置查询属性，排序字段，分页参数 
     */
    List<MonitoringItemDO> selectPageSelective(@Param("record") MonitoringItemDO record, @Param("pageable") Pageable pageable);

    /**
     * 批量逻辑删除,set is_deleted=1返回影响行数
     */
    Integer batchDeleteByPrimaryKey(List<Long> pkIds);

    /**
     * 插入行记录返回影响行数，使用getId()获取插入行记录的Id
     */
    Integer insertSelectiveReturnPrimaryKey(MonitoringItemDO record);

    /**
     * 批量插入行数据返回插入行数
     */
    Integer batchInsert(List<MonitoringItemDO> records);

    /**
     * 根据主键批量获取
     */
    List<MonitoringItemDO> batchSelectByPrimaryKey(List<Long> pkIds);

    /**
     * insertSelective的增强版，插入成功返回1，失败返回0
     * 如果插入的字段中有UNIQUE KEY, KEY已经存在则不予插入返回0，不会抛出DuplicateKeyException
     */
    Integer insertIgnoreSelective(MonitoringItemDO record);
}