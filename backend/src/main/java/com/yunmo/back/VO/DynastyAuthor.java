package com.yunmo.back.VO;

import com.yunmo.back.pojo.Author;
import com.yunmo.back.pojo.Dynasty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynastyAuthor {
   private Author author;
   private Dynasty dynasty;
}
