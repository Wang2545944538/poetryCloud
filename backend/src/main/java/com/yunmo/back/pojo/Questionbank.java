package com.yunmo.back.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
 * @since 2024-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("questionbank")
@AllArgsConstructor
@NoArgsConstructor
public class Questionbank implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "questionBank_id",type = IdType.AUTO)
    private Integer questionBank_id;

    @TableField("question")
    private String question;

    @TableField("options_id")
    private Integer options_id;

    @TableField("answer")
    private String answer;

    @TableField(exist = false)
    private Options options;

}
