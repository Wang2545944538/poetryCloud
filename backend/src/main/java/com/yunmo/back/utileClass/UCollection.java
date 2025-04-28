package com.yunmo.back.utileClass;

import com.yunmo.back.pojo.Collection;
import com.yunmo.back.pojo.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Tang
 * @Date 2024/9/20 10:06
 * @Description: 这个人很懒，什么注释都没有写
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UCollection {


    Integer user_id;
    String collection_name;
    String collection_avatar;
    Integer collection_count;
    Integer collection_id;

}
