package com.yunmo.back.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

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
@TableName("collection")
@Alias("myCollection")
@AllArgsConstructor
@NoArgsConstructor
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "collection_id", type = IdType.AUTO)
    private Integer collection_id;

    @TableField("collection_name")
    private String collection_name;

    @TableField("collection_avatar")
    private String collection_avatar;

    @TableField("collection_count")
    private Integer collection_count;


    public Collection(String collectionName, String collectionAvatar, Integer collectionCount) {
        this.collection_name = collectionName;
        this.collection_avatar = collectionAvatar;
        this.collection_count = collectionCount;
    }
}
