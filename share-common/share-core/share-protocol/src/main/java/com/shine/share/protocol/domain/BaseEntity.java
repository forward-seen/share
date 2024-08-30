package com.shine.share.protocol.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体类基础属性，用于映射数据库表的通用字段
 * 业务entity类继承该类，并加上lombok注解@EqualsAndHashCode(callSuper = true)
 * 、@ToString(callSuper = true)、@Data
 *
 * @author 辛凤文
 * @since 1.0
 */
@Data
public class BaseEntity implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String createBy;

    private String updateBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Byte deleted;

    private String remark;

}
