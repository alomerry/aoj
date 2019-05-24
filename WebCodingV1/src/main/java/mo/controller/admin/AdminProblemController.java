package mo.controller.admin;

import mo.core.Result;
import mo.entity.po.Problem;
import mo.entity.po.Tag;
import mo.entity.vo.ProblemTagTestCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminProblemController {
    /**
     * 查询题目集
     *
     * @param resType  结果类型
     * @param page     页码
     * @param per_page 每页数量
     * @return 题目集
     */
    Result problems(String resType, String page, String per_page);

    /**
     * 根据公开级别查询题目集（包括自己创建的）
     *
     * @param resType  结果类型
     * @param page     页码
     * @param per_page 每页数量
     * @param defunct  公开级别
     * @return
     */
    Result problems(String resType, String page, String per_page, String defunct);

    /**
     * 查询指定比赛题目集
     *
     * @param resType    结果类型
     * @param page       页码
     * @param per_page   每页数量
     * @param contest_id 比赛Id
     * @return 题目集
     */
    Result problems(String resType, String page, String per_page, Integer contest_id);

    /**
     * 删除指定题目
     *
     * @param problem_id 题目Id
     * @return 是否成功
     */
    Result deleteProblem(Integer problem_id);

    /**
     * 上传测试文件
     *
     * @param testCase 测试文件
     * @return
     */
    Result uploadTestCase(MultipartFile testCase);

    /**
     * 新建题目
     *
     * @param problem    题目实体
     * @param tags       标签数组
     * @param testCaseId 测试用例文件夹名称
     * @return
     */
//    Result createNewProblem(Problem problem, List<Tag> tags, String testCaseId);
    Result createNewProblem(String problem, String tags, String testCaseId);

    /**
     * 新建题目至竞赛
     *
     * @param contest_id 竞赛Id
     * @param problem    题目实体
     * @param tags       标签数组
     * @param testCaseId 测试用例文件夹名称
     * @return
     */
    Result createNewProblemToContest(Integer contest_id, String problem, String tags, String testCaseId);

    /**
     * 更新题目
     *
     * @param problemTagTestCase 题目Vo
     * @return
     */
    Result updateProblem(ProblemTagTestCase problemTagTestCase);


    /**
     * 查询指定题目
     *
     * @param id 题目Id
     * @return 题目
     */
    Result problem(Integer id);

    /**
     * 根据关键词查询题目集
     *
     * @param keycode 关键字
     * @return
     */
    Result problems(String keycode);

    /**
     * 修改指定题目的禁用状态
     *
     * @param problem_id 题目
     * @param state      状态
     * @return
     */
    Result problemDisableState(Integer problem_id, String state);
}
