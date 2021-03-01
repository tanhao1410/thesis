package com.github.tanhao1410.thesis.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 框架自动生成表模型和CRUD操作，勿修改；
 * 如特殊需要，请以Ext***Mapper自行扩展；
 * 生成日期 : 2021-03-01 14:17:01
 * @author ##tanhao##
 */
@Setter
@Getter
@ToString
public class GroupDO {
    private Long id;

    private String name;

    private String description;

    private Integer x;

    private Integer y;

    private String department;
}