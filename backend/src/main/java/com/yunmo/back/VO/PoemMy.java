package com.yunmo.back.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PoemMy {
    private String title;
    private String content;
    private String author_id; // 或者使用 author_id，取决于您的命名约定
    private String poem_id;
    private String avatar; // 添加 avatar 字段以包含用户头像
    private String status;
}
