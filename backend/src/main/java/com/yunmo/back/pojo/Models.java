package com.yunmo.back.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

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
@TableName("models")
public class Models implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "model_id", type = IdType.AUTO)
    private Integer model_id;

    @TableField("model_name")
    private String model_name;

    @TableField("url")
    private String url;

    @TableField("parent_models")
    private Integer parent_models;

    @TableField(exist = false)
    private List<Models> childMods;

    public Object getModelId() {
        return model_id;
    }
}
