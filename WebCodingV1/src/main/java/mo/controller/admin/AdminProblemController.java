package mo.controller.admin;

import mo.core.Result;
import mo.entity.po.Problem;
import mo.entity.po.Tag;
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
     * 更新题目
     *
     * @param problem    题目实体
     * @param tags       标签数组
     * @param testCaseId 测试用例文件夹名称
     * @return
     */
    Result updateProblem(Problem problem, List<Tag> tags, String testCaseId);
}
