package mo.controller.admin;

import mo.entity.po.Privilege;
import mo.entity.po.User;
import org.springframework.ui.ModelMap;

import java.util.Map;

public interface AdminContestApplyController {
    /**
     * 比赛申请页面
     * 管理员点击 比赛-申请管理进入此业务，根据
     * @param user 用户实体
     * @param page 页码
     * @param type 排序列
     * @param sort 升降序
     * @param map  数据载体
     * @return 相对路径
     */
    String contestApplyList(User user, Privilege privilege, int page, String type, String sort, ModelMap map);

    /**
     * 管理员确认加入比赛
     *
     * @param data 数据载体
     * @return
     */
    String confirmApply(Map<String, Object> data);

    /**
     * 管理员拒绝加入比赛
     *
     * @param data 数据载体
     * @return
     */
    String deleteApply(Map<String, Object> data);
}
