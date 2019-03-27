package mo.service;

import com.alibaba.druid.sql.visitor.functions.Char;
import mo.entity.po.Contest;

import java.util.List;

public interface ContestService {

    /**
     * 根据页码查询竞赛集
     *
     * @param page     页码
     * @param per_page 每页数量
     * @return 竞赛集
     */
    List<Contest> findContestsByPageAndPerPage(Integer page, Integer per_page);

    /**
     * 根据公开级别查询竞赛集
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param defunct  公开级别
     * @return 竞赛集
     */
    List<Contest> findContestsByPageAndDefunct(Integer page, Integer per_page, Integer[] defunct);

    /**
     * 根据管理员级别查询竞赛集
     *
     * @param page     页码
     * @param per_page 每页数量
     * @param rightstr 管理员权限级别
     * @param userId   管理员Id
     * @return 竞赛集
     */
    List<Contest> findContestsByPageFromAdminPrivilege(Integer page, Integer per_page, String rightstr, Integer userId);
}
