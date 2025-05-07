package com.yunmo.back.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("users_collection")
public class Users_collection implements Serializable {

    private static final long serialVersionUID = 1L;

    public Users_collection(Integer collection_id, Integer user_id) {
        this.collection_id = collection_id;
        this.user_id = user_id;
    }

    @TableField(value = "collection_id")
    private Integer collection_id;

    @TableField("user_id")
    private Integer user_id;

    @TableField(exist = false)
    private List<Collection> collection;

}
