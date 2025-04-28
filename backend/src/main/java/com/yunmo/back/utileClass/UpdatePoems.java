package com.yunmo.back.utileClass;

import com.yunmo.back.pojo.Poem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Tang
 * @Date 2024/9/22 13:53
 * @Description: 这个人很懒，什么注释都没有写
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePoems {

    Integer collection_id;

    List<Poem> poems;
}
