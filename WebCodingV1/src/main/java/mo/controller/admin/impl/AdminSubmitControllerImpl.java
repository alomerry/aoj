package mo.controller.admin.impl;

import mo.controller.admin.AbstractAdminController;
import mo.controller.admin.AdminSubmitController;
import mo.core.Result;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminSubmitControllerImpl extends AbstractAdminController implements AdminSubmitController {

    private static final Logger logger = LoggerFactory.getLogger(AdminSubmitControllerImpl.class);


    @Override
    @AuthCheck({RequiredType.JWT})
    @RequestMapping(value = "/admin/submit",consumes = "application/json")
    public Result insertSubmit(@RequestBody Map<String,Object> submit) {

        return null;
    }
}
