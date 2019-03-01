package mo.controller.admin.impl;

import mo.controller.admin.AdminProblemController;
import mo.core.Result;
import mo.entity.po.Privilege;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static mo.utils.StringValue.ONLINEJUDGE_SESSION_GROUP;

@RestController
public class AdminProblemControllerImpl implements AdminProblemController {

//    private

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/problems", method = RequestMethod.GET)
    public Result problems(@RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        Privilege privilege = (Privilege) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(ONLINEJUDGE_SESSION_GROUP);
        String level = privilege.getRightstr();
//        char defunct =
        return null;
    }
}
