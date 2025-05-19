package com.yunmo.back.VO;

import lombok.Data;

@Data
public class uPoem{
    String title;
    Integer author_id;
    String content;
    Integer genre_id;
    Integer theme_id;
    String intro;
    String pingxi;
    String comments;
    String translation;
    String jiping;
    Integer poem_id;
    Integer pf_id;
}
