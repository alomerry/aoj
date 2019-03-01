package mo.service;

import mo.entity.po.Problem;
import mo.utils.DIYMessage;

import java.util.List;

public interface ProblemService {

    int delFail = 0, delSuccess = 1;

    /**
     * @param page    页数
     * @param pageNum 每页的数量
     * @return
     */
    List<Problem> findPublicProblemsByPage(int page, int pageNum);

    /**
     * 根据Id查找指定问题
     *
     * @param problem_id 问题Id
     * @return 问题实体
     */
    Problem findProblemByProblemId(Integer problem_id);

    /**
     * 查询指定问题是否公开
     *
     * @param problem_id 问题Id
     * @return 公开=真
     */
    boolean checkProblemIsPublic(Integer problem_id, Integer user_id);

    /**
     * 根据创建者Id查询题目集
     *
     * @param user_id 创建者Id
     * @return 题目集
     */
    List<Problem> findCreatorProblemsByUserId(Integer user_id);

    /**
     * 根据公开级别查询问题
     *
     * @param page    页码
     * @param pageNum 每页数量
     * @param defunct 公开级别
     * @return 问题集
     */
    List<Problem> findProblemsByDefunct(int defunct, int page, int pageNum);

    /**
     * 根据公开级别查询问题
     *
     * @param defunct 公开级别
     * @return 问题集
     */
    List<Problem> findProblemsByDefunct(int defunct);

    /**
     * 根据页码查询所有题目
     *
     * @param page    页码
     * @param pageNum 每页数量
     * @return 题目集
     */
    List<Problem> findProblemsByPage(int page, int pageNum);

    /**
     * 根据页码按指定列排序查询题目
     *
     * @param page    页码
     * @param pageNum 每页数量
     * @param type    排序列
     * @param sort    升降序
     * @return
     */
    List<Problem> findProblemsByPageOrderByTypeAndSort(int page, int pageNum, String type, String sort);

    /**
     * 根据id删除题目
     *
     * @param problem_id 题目Id
     * @return 自定义结果
     */
    DIYMessage delProblemByProblemId(Integer problem_id);
}
