package com.yunmo.back.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PoemDTO {
    private String title;
    private String content;
    private String nickname;
    private String avatar;
    private String status;
    private String poem_id;
}
