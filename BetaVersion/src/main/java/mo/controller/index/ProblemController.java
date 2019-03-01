package mo.controller.index;

import mo.entity.po.User;
import org.springframework.ui.ModelMap;

public interface ProblemController {

    /**
     * 问题列表
     *
     * @param page     页码
     * @param modelMap 数据载体
     * @return 相对路径
     */
    String problemList(int page, ModelMap modelMap);

    /**
     * 问题描述
     *
     * @param proId    问题Id
     * @param modelMap 数据载体
     * @return 相对路径
     */
    String problemDetail(Integer proId, ModelMap modelMap,User user);

    /**
     * 问题提交页面
     *
     * @param proId    问题Id
     * @param modelMap 数据载体
     * @return 相对路径
     */
    String problemSubmit(Integer proId, ModelMap modelMap);

    /**
     * 问题确认提交请求
     *
     * @param user     用户实体
     * @param proId    问题Id
     * @param language 语言
     * @param source   源代码
     * @return 相对路径
     */
    String problemDoSubmit(User user, Integer proId, String language, String source);

}
