package com.yunmo.back.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalTime;
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
@TableName("author")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "author_id", type = IdType.AUTO)
    private Integer author_id;

    @TableField("author_name")
    private String author_name;

    @TableField("author_gender")
    private String author_gender;

    @TableField("dynasty_id")
    private Integer dynasty_id;

    @TableField("author_intro")
    private String author_intro;

    @TableField("author_img")
    private String author_img;

    @TableField("author_style")
    private String author_style;

    @TableField(exist = false)
    private Dynasty dynasty;
}
