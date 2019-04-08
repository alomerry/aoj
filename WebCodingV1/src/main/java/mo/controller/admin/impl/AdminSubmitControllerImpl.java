package mo.controller.admin.impl;

import mo.controller.admin.AbstractAdminController;
import mo.controller.admin.AdminSubmitController;
import mo.core.Result;
import mo.entity.po.Solution;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.utils.InetAddressUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Map;

@RestController
public class AdminSubmitControllerImpl extends AbstractAdminController implements AdminSubmitController {

    private static final Logger logger = LoggerFactory.getLogger(AdminSubmitControllerImpl.class);


    @Override
    @AuthCheck({RequiredType.JWT})
    @RequestMapping(value = "/admin/submit", consumes = "application/json")
    public Result insertSubmit(@RequestBody Map<String, Object> submit) {

        return null;
    }

    private Solution initBySubmit(Map<String, Object> submit) {
        Solution solution = new Solution();
        solution.setIp(InetAddressUtils.getIpAddr(getHttpServletRequest()));
        solution.setCode_lenght((Integer) submit.get("code_lenght"));
//        solution.setContest_id();
        solution.setCreate_at(new Timestamp(System.currentTimeMillis()));
        solution.setLanguage((Integer) submit.get("language"));
        solution.setProblem_id((Integer) submit.get("problem_id"));
        solution.setUser_id(getJWTUserId());
        return solution;
    }
}
