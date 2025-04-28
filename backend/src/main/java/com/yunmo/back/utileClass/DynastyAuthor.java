package com.yunmo.back.utileClass;

import com.yunmo.back.pojo.Author;
import com.yunmo.back.pojo.Dynasty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynastyAuthor {
   private Author author;
   private Dynasty dynasty;
}
