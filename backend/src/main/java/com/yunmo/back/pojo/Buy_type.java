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
@TableName("buy_type")
public class Buy_type implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "buy_id", type = IdType.AUTO)
    private Integer buy_id;

    @TableField("buy_name")
    private String buy_name;


}
