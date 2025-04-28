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
@TableName("goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "good_id", type = IdType.AUTO)
    private Integer good_id;

    @TableField("good_name")
    private String good_name;

    @TableField("good_count")
    private Integer good_count;

    @TableField("price")
    private Double price;

    @TableField("img")
    private String img;

    @TableField("Typeid")
    private Integer Typeid;

    @TableField("point_price")
    private Double point_price;

    @TableField("buy_id")
    private Integer buy_id;
    @TableField("detail")
    private String detail;

    @TableField(exist=false)
    private Product_type product_type;
    @TableField(exist=false)
    private Buy_type buy_type;
}
