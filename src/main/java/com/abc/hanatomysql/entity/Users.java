package com.abc.hanatomysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Z-7
 * @since 2022-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_test", type = IdType.NONE)
    private Integer idTest;

    private String nameTest;


}
