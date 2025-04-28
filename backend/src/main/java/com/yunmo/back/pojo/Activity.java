package com.yunmo.back.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@TableName("activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "active_id", type = IdType.AUTO)
    private Integer active_id;

    @TableField("active_content")
    private String active_content;

    @TableField("active_time")
    private LocalDateTime active_time;

    @TableField("active_type_id")
    private String active_type_id;

    @TableField("active_img")
    private String active_img;

    @TableField("active_profile")
    private String active_profile;

    @TableField(exist = false)
    private String active_type;

    @TableField(exist = false)
    private Activity_type activityType;

}
