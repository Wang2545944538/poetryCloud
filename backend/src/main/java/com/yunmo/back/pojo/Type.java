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
 * @since 2024-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("type")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("type_id")
    private Integer type_id;

    @TableField("type_name")
    private String type_name;

    @TableField("model_id")
    private Integer model_id;


}
