package com.yunmo.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Poem;
import com.yunmo.back.dao.IPoemDao;
import com.yunmo.back.pojo.Poeminformation;
import com.yunmo.back.service.IPoemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunmo.back.utileClass.PoemDTO;
import com.yunmo.back.utileClass.PoemMy;
import com.yunmo.back.utileClass.aPoem;
import com.yunmo.back.utileClass.uPoem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@Service
public class PoemServiceImpl extends ServiceImpl<IPoemDao, Poem> implements IPoemService {
    @Autowired
    IPoemDao poemDao;
    // 查询所有自创诗
    @Override
    public IPage<PoemDTO> getAllMyPoem(int page, int limit) {
        Page<PoemDTO> poemPage = new Page<>(page, limit);
        return poemDao.selectAllMyPoems(poemPage);
    }

    @Override
    public IPage<Poem> getPoems(Integer currentPage, Integer pageSize,
                                List<Integer> authorIds, List<Integer> dynastyIds,
                                List<Integer> genreIds, List<Integer> themeIds,
                                String title) {
        Page<Poem> page = new Page<>(currentPage, pageSize);
        return poemDao.getPoems(page,authorIds,dynastyIds,genreIds,themeIds,title);
    }
    @Override
    public Poem getPoemDetails(Integer poem_id) {
        return poemDao.getPoemDetails(poem_id);
    }

    @Override
    @Transactional
    public Integer updatePoem(uPoem uPoem) {
        // 更新poem表
        Integer poemUpdated = poemDao.updatePoem(
                uPoem.getTitle(),
                uPoem.getAuthor_id(),
                uPoem.getContent(),
                uPoem.getGenre_id(),
                uPoem.getTheme_id(),
                uPoem.getIntro(),
                uPoem.getPoem_id()
        );

        // 更新poeminformation表
        Integer poemInfoUpdated = poemDao.updatePoemInformation(
                uPoem.getPingxi(),
                uPoem.getComments(),
                uPoem.getTranslation(),
                uPoem.getJiping(),
                uPoem.getPf_id()
        );

        return poemUpdated + poemInfoUpdated;
    }

    @Override
    @Transactional
    public Integer deletePoem(Integer poem_id, Integer pf_id) {
        // 更新poem表
        Integer poemDeleted = poemDao.deletePoem(poem_id);

        // 更新poeminformation表
        Integer poemInfoDeleted = poemDao.deletePoemInformation(pf_id);

        return poemDeleted+poemInfoDeleted;
    }

    @Override
    @Transactional
    public Integer addPoem(aPoem aPoem) {
        Poeminformation poeminformation = new Poeminformation(
                aPoem.getPf_id(),
                aPoem.getPingxi(),
                aPoem.getComments(),
                aPoem.getTranslation(),
                aPoem.getJiping()
        );
        // 新增poeminformation
        Integer poemInfoAdded = poemDao.addPoemInformation(poeminformation);
        Integer pf_id = poeminformation.getPf_id();
        // 新增poem
        Integer poemAdded = poemDao.addPoem(
                aPoem.getTitle(),
                aPoem.getAuthor_id(),
                aPoem.getContent(),
                aPoem.getGenre_id(),
                aPoem.getTheme_id(),
                aPoem.getIntro(),
                pf_id
        );
        return poemInfoAdded + poemAdded;
    }

    // 查询我的已发布自创诗
    @Override
    public IPage<PoemMy> getMyPoem(String author_id, int page, int limit) {
        Page<Poem> poemPage = new Page<>(page, limit);
        return poemDao.selectMyPoems(author_id, poemPage);
    }

    // 查询我的未发布自创诗
    @Override
    public IPage<PoemMy> selectMyNotPublishPoems(String author_id, int page, int limit) {
        Page<Poem> poemPage = new Page<>(page, limit);
        return poemDao.selectMyNotPublishPoems(author_id, poemPage);
    }

    @Override
    public List<Poem> findByIdPoems(Integer id) {
        return poemDao.findByIdPoems(id);
    }

    // 管理员审核自创诗
    @Override
    public IPage<PoemDTO> adminCheckPoem(int page, int limit) {
        Page<PoemDTO> poemPage = new Page<>(page, limit);
        return poemDao.adminCheckPoem(poemPage);
    }

    // 管理员审核通过，将poem表中的status字段由0改为1
    @Override
    public Integer adminPassPoem(Integer poem_id) {
        return poemDao.adminCheckPass(poem_id);
    }

    // 管理员审核通过，将poem表中的status字段由0改为2
    @Override
    public Integer adminRejectPoem(Integer poem_id) {
        return poemDao.adminCheckNotPass(poem_id);
    }

    @Override
    public IPage<PoemDTO> adminSelectAllPoem(int page, int limit) {
        Page<PoemDTO> poemPage = new Page<>(page, limit);
        return poemDao.adminSelectAllPoem(poemPage);
    }

    @Override
    public Integer adminShelfPoem(Integer poem_id) {
        return poemDao.adminShelfPoem(poem_id);
    }

    @Override
    public Integer adminUnShelfPoem(Integer poem_id) {
        return poemDao.adminUnShelfPoem(poem_id);
    }
}
