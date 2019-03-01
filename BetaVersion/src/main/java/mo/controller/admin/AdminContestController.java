package mo.controller.admin;

import mo.entity.po.Contest;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.utils.DIYMessage;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

public interface AdminContestController {


    /**
     * 竞赛页面
     * 根据传入的数据以type列按sort序列查询page页的竞赛
     *
     * @param page      页码
     * @param type      排序列
     * @param sort      排序方式
     * @param privilege 管理员级别
     * @param map       数据载体
     * @return 相对路径
     */
    String listContest(int page, String type, String sort, User user, Privilege privilege, ModelMap map);

    /**
     * 确认添加竞赛
     *
     * @param map 数据载体
     * @return 路径
     */
    String addContest(DIYMessage msg, ModelMap map);

    /**
     * 添加竞赛业务
     * 管理员录入竞赛信息后点击确定后进入此业务，此业务将数据录入数据库后并携带闪存信息
     *
     * @param contest 竞赛实体
     * @param map     数据载体
     * @param user    用户实体
     * @return 返回添加页面
     */
    String contestDoAdd(Contest contest, RedirectAttributes map, User user);


    /**
     * 确认删除竞赛
     * 管理员在竞赛列表点击确认删除后进行Ajax异步删除操作访问本业务
     *
     * @param data 数据载体
     * @return 操作结果
     */
    String contestDoDelete(Map<String, Object> data);

    /**
     * 查看竞赛信息
     *
     * @param contest_id 竞赛号
     * @param map        数据载体
     * @return jsp路径
     */
    String contestView(Integer contest_id, ModelMap map);

    /**
     * 编辑竞赛信息
     *
     * @param contest_id 竞赛号
     * @param map        数据载体
     * @return jsp路径
     */
    String contestEdit(Integer contest_id, ModelMap map);

    /**
     * ajax异步添加题目到竞赛
     *
     * @param data 包含key-contest_id problem_id
     * @return json数据
     */
    String addProblemsToContest(Map<String, Object> data);

    /**
     * ajax异步删除竞赛中的题目
     *
     * @param data 包含key-contest_id problem_id
     * @return json数据
     */
    String delProblemsFromContest(Map<String, Object> data);

    /**
     * ajax 异步修改竞赛题目序号
     *
     * @param data 包含key-problem_id,num_value
     * @return 结果，json数据
     */
    String changContestProblemNum(Map<String, Object> data);
}
