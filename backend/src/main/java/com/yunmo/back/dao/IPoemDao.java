package com.yunmo.back.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Poem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunmo.back.pojo.Poeminformation;
import com.yunmo.back.VO.PoemDTO;
import com.yunmo.back.VO.PoemMy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
public interface IPoemDao extends BaseMapper<Poem> {
    //获取诗词曲内容
    IPage<Poem> getPoems(IPage<Poem> page,
                         @Param("authorIds") List<Integer> authorIds,
                         @Param("dynastyIds") List<Integer> dynastyIds,
                         @Param("genreIds") List<Integer> genreIds,
                         @Param("themeIds") List<Integer> themeIds,
                         @Param("title") String title);
    //获取诗词曲的作品信息
    Poem getPoemDetails(Integer poem_id);
    // updatePoem方法
    Integer updatePoem(@Param("title") String title, @Param("author_id") Integer author_id,
                       @Param("content") String content, @Param("genre_id") Integer genre_id,
                       @Param("theme_id") Integer theme_id, @Param("intro") String intro,
                       @Param("poem_id") Integer poem_id);

    // 更新poeminformation表的方法
    Integer updatePoemInformation(@Param("pingxi") String pingxi, @Param("comments") String comments,
                                  @Param("translation") String translation, @Param("jiping") String jiping,
                                  @Param("pf_id") Integer pf_id);

    Integer deletePoem(@Param("poem_id") Integer poem_id);
    Integer deletePoemInformation(@Param("pf_id") Integer pf_id);

    Integer addPoemInformation(Poeminformation poeminformation);
    // updatePoem方法
    Integer addPoem(@Param("title") String title, @Param("author_id") Integer author_id,
                    @Param("content") String content, @Param("genre_id") Integer genre_id,
                    @Param("theme_id") Integer theme_id, @Param("intro") String intro,
                    @Param("pf_id") Integer pf_id);
    // 查询所有自创诗
    @Select("SELECT a.title, a.content, b.avatar ,b.nickname FROM poem a LEFT JOIN users b ON a.author_id = b.user_id WHERE a.isself = 1")
    IPage<PoemDTO> selectAllMyPoems(IPage<PoemDTO> page);

    // 查询我的已发布自创诗
    @Select("SELECT a.title, a.content, b.avatar,a.poem_id FROM poem a LEFT JOIN users b ON a.author_id = b.user_id WHERE a.isself = 1 AND a.status = 1 AND a.author_id = #{author_id}")
    IPage<PoemMy> selectMyPoems(@Param("author_id") String userId, Page<Poem> poemPage);

    // 查询我的未发布自创诗
    @Select("SELECT a.title, a.content, b.avatar, a.poem_id, a.status\n" +
            "FROM poem a\n" +
            "LEFT JOIN users b ON a.author_id = b.user_id\n" +
            "WHERE a.isself = 1\n" +
            "AND (a.status = 0 OR a.status = 2)\n" +
            "AND a.author_id = #{author_id}")
    IPage<PoemMy> selectMyNotPublishPoems(@Param("author_id") String userId, Page<Poem> poemPage);

    List<Poem> findByIdPoems(Integer author_id);

    // 查询所有未审核的自创诗
    @Select("SELECT a.title, a.content, b.avatar ,b.nickname,a.status,a.poem_id FROM poem a LEFT JOIN users b ON a.author_id = b.user_id WHERE a.isself = 1 and a.status = 0")
    IPage<PoemDTO> UncheckedPoems(IPage<PoemDTO> page);

    // 查询所有已审核的自创诗
    @Select("SELECT a.title, a.content, b.avatar ,b.nickname,a.status,a.poem_id FROM poem a LEFT JOIN users b ON a.author_id = b.user_id WHERE a.isself = 1 and a.status = 1 or a.status = 2 or a.status = 3")
    IPage<PoemDTO> CheckedPoems(IPage<PoemDTO> page);

    // 管理员审核通过，将poem表中的status字段由0改为1
    @Select("update poem set status = 1 where poem_id = #{poem_id}")
    Integer adminCheckPass(@Param("poem_id") Integer poem_id);

    // 管理员审核不通过，将poem表中的status字段由0改为2
    @Select("update poem set status = 2 where poem_id = #{poem_id}")
    Integer adminCheckNotPass(@Param("poem_id") Integer poem_id);

    // 管理员查询所有自创诗
    @Select("SELECT a.title, a.content, b.avatar ,b.nickname,a.status,a.poem_id FROM poem a LEFT JOIN users b ON a.author_id = b.user_id WHERE a.isself = 1 and a.status = 1 or a.status = 2 or a.status = 3")
    IPage<PoemDTO> adminSelectAllPoem(IPage<PoemDTO> page);

    // 管理员上架诗词
    @Select("update poem set status = 1 where poem_id = #{poem_id}")
    Integer adminShelfPoem(@Param("poem_id") Integer poem_id);

    // 管理员下架诗词
    @Select("update poem set status = 3 where poem_id = #{poem_id}")
    Integer adminUnShelfPoem(@Param("poem_id") Integer poem_id);

}
