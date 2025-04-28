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
 * @since 2024-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "orde_id", type = IdType.AUTO)
    private Integer orde_id;

    @TableField("buy_count")
    private Integer buy_count;

    @TableField("ispay")
    private Integer ispay;

    @TableField("good_id")
    private Integer good_id;

    @TableField("buy_time")
    private LocalDateTime buy_time;
    @TableField("user_id")
    private int user_id;

}
