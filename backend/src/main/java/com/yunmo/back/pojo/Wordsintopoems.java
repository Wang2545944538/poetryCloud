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
 * @since 2024-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wordsintopoems")
public class Wordsintopoems implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "wordsIntoPoems_id", type = IdType.AUTO)
    private Integer wordsIntoPoems_id;

    @TableField("content")
    private String content;

    @TableField("correctPoem")
    private String correctPoem;


}
