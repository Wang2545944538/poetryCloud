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
 * @since 2024-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("options")
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "options_id", type = IdType.AUTO)
    private Integer options_id;

    @TableField("A")
    private String A;

    @TableField("B")
    private String B;

    @TableField("C")
    private String C;

    @TableField("D")
    private String D;


}
