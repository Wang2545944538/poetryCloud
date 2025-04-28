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
@TableName("poem_collection")
@AllArgsConstructor
public class Poem_collection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "collection_id", type = IdType.AUTO)
    private Integer collection_id;

    @TableField("poem_id")
    private Integer poem_id;


}
