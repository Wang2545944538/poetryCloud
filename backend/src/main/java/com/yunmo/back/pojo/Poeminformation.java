package com.yunmo.back.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.AllArgsConstructor;
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
@TableName("poeminformation")
@AllArgsConstructor
public class Poeminformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pf_id", type = IdType.AUTO)
    private Integer pf_id;

    @TableField("pingxi")
    private String pingxi;

    @TableField("comments")
    private String comments;

    @TableField("translation")
    private String translation;

    @TableField("jiping")
    private String jiping;


}
