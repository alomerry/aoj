package mo.controller.index;

import mo.entity.po.User;
import org.springframework.ui.ModelMap;

import java.util.Map;

public interface ContestController {

    /**
     * 竞赛列表
     *
     * @param page 页码
     * @param map  数据载体
     * @return 相对路径
     */
    String contestList(int page, ModelMap map);

    /**
     * 竞赛详细
     *
     * @param contestId 竞赛Id
     * @param map       数据载体
     * @return 相对路径
     */
    String contestDetail(int contestId, ModelMap map);

    /**
     * 申请加入比赛
     *
     * @param user 用户实体
     * @param data 数据载体
     * @return 消息数据
     */
    String contestDoAdd(Map<String, Object> data, User user);

    /**
     * 跳转至比赛
     *
     * @param user       用户实体
     * @param contest_id 比赛号
     * @return 相对路径
     */
    String competitionList(User user, Integer contest_id, ModelMap map);

    /**
     * ajax检测信息
     *
     * @param data 数据载体
     * @return 响应数据
     */
    String competitionCheck(Map<String, Object> data, User user);
}
