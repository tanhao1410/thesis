package com.github.tanhao1410.thesis.common.mapper;

import com.github.tanhao1410.thesis.common.domain.HistoryAlarmDO;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * 框架自动生成表模型和CRUD操作，勿修改；
 * 如特殊需要，请以Ext***Mapper自行扩展；
 * 生成日期 : 2021-03-01 14:17:01
 * @author ##tanhao##
 */
@Repository
public interface HistoryAlarmDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HistoryAlarmDO record);

    int insertSelective(HistoryAlarmDO record);

    HistoryAlarmDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HistoryAlarmDO record);

    int updateByPrimaryKey(HistoryAlarmDO record);

    /**
     * 通用查询行数方法，可设置查询属性
     */
    Long selectCountSelective(@Param("record") HistoryAlarmDO record);

    /**
     * 通用查询List方法，可设置查询属性，排序字段，分页参数 
     */
    List<HistoryAlarmDO> selectPageSelective(@Param("record") HistoryAlarmDO record, @Param("pageable") Pageable pageable);

    /**
     * 批量逻辑删除,set is_deleted=1返回影响行数
     */
    Integer batchDeleteByPrimaryKey(List<Long> pkIds);

    /**
     * 插入行记录返回影响行数，使用getId()获取插入行记录的Id
     */
    Integer insertSelectiveReturnPrimaryKey(HistoryAlarmDO record);

    /**
     * 批量插入行数据返回插入行数
     */
    Integer batchInsert(List<HistoryAlarmDO> records);

    /**
     * 根据主键批量获取
     */
    List<HistoryAlarmDO> batchSelectByPrimaryKey(List<Long> pkIds);

    /**
     * insertSelective的增强版，插入成功返回1，失败返回0
     * 如果插入的字段中有UNIQUE KEY, KEY已经存在则不予插入返回0，不会抛出DuplicateKeyException
     */
    Integer insertIgnoreSelective(HistoryAlarmDO record);
}