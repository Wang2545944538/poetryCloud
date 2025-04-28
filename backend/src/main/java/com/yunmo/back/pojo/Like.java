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
 * @since 2024-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("likes")
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lid", type = IdType.AUTO)
    private Integer lid;

    @TableField("collection_id")
    private Integer collection_id;

    @TableField("user_id")
    private Integer user_id;


    public Like(int userId, Integer collectionId) {
        this.user_id = userId;
        this.collection_id = collectionId;
    }
}
