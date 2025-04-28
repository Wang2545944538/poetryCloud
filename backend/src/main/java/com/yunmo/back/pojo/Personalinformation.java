package com.yunmo.back.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("personalinformation")
public class Personalinformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "personal_id", type = IdType.AUTO)
    private Integer personal_id;

    @TableField("user_id")
    private Integer user_id;

    @TableField("name")
    private String name;

    @TableField("sex")
    private String sex;

    @TableField("age")
    private Integer age;

    @TableField("money")
    private Double money;

    @TableField("point")
    private Integer point;


}
