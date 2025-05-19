package com.yunmo.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunmo.back.pojo.Poem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yunmo.back.VO.PoemDTO;
import com.yunmo.back.VO.PoemMy;
import com.yunmo.back.VO.aPoem;
import com.yunmo.back.VO.uPoem;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
public interface IPoemService extends IService<Poem> {
    // 查询所有自创诗
    IPage<PoemDTO> getAllMyPoem(int page, int limit);

    // 查询当前用户已发布的自创诗
    IPage<PoemMy> getMyPoem(String author_id, int page, int limit);

    IPage<Poem> getPoems(Integer currentPage, Integer pageSize,
                         List<Integer> authorIds, List<Integer> dynastyIds,
                         List<Integer> genreIds, List<Integer> themeIds,
                         String title);
    Poem getPoemDetails(Integer poem_id);
    Integer updatePoem(uPoem uPoem);
    Integer deletePoem(Integer poem_id,Integer pf_id);
    Integer addPoem(aPoem aPoem);
    // 查询我的未发布自创诗
    IPage<PoemMy> selectMyNotPublishPoems(String author_id, int page, int limit);

    List<Poem> findByIdPoems(Integer id);

    // 查询所有未审核的自创诗
    IPage<PoemDTO> getUncheckedPoems(int page, int limit);

    // 查询所有已审核的自创诗
    IPage<PoemDTO> getCheckedPoems(int page, int limit);


    // 管理员审核通过，将poem表中的status字段由0改为1
    Integer adminPassPoem(Integer poem_id);

    // 管理员审核不通过，将poem表中的status字段由0改为2
    Integer adminRejectPoem(Integer poem_id);

    // 管理员查询所有自创诗
    IPage<PoemDTO> adminSelectAllPoem(int page, int limit);

    // 管理员上架诗词
    Integer adminShelfPoem(Integer poem_id);

    // 管理员下架诗词
    Integer adminUnShelfPoem(Integer poem_id);





}
