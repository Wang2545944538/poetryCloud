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
 * @since 2024-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("activity_type")
public class Activity_type implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "active_type_id", type = IdType.AUTO)
    private Integer active_type_id;

    @TableField("activity_type")
    private String activity_type;


}
