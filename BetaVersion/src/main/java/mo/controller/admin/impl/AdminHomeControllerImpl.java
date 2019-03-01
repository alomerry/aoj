package mo.controller.admin.impl;

import mo.controller.admin.AdminHomeController;
import mo.entity.po.User;
import mo.utils.string.StringValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(value = {StringValue.ONLINEJUDGE_SESSION_UER}, types = {User.class})
public class AdminHomeControllerImpl implements AdminHomeController {

    @Override
    @RequestMapping("/admin_home")
    public String home(ModelMap map, @ModelAttribute(StringValue.ONLINEJUDGE_SESSION_UER) User user) {
        map.put(StringValue.ONLINEJUDGE_SESSION_UER, user);
        return "admin/home";
    }
}
