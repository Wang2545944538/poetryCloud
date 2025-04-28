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
@TableName("poem")
public class Poem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "poem_id", type = IdType.AUTO)
    private Integer poem_id;

    @TableField("author_id")
    private Integer author_id;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("intro")
    private String intro;

    @TableField("genre_id")
    private int genre_id;

    @TableField("theme_id")
    private int theme_id;

    @TableField("pf_id")
    private int pf_id;

    @TableField("status")
    private int status;

    @TableField("isself")
    private int isself;

    @TableField(exist = false)
    private Author author;

    @TableField(exist = false)
    private Genre genre;

    @TableField(exist = false)
    private Theme theme;

    @TableField(exist = false)
    private Poeminformation poeminformation;
}
