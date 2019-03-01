package mo.controller.index.impl;

import mo.controller.index.HomeController;
import mo.entity.po.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static mo.utils.string.StringValue.ONLINEJUDGE_SESSION_UER;

@Controller
@SessionAttributes(value = {ONLINEJUDGE_SESSION_UER}, types = User.class)
public class HomeControllerImpl implements HomeController {
    @Override
    @RequestMapping("home")
    public String home(@ModelAttribute(ONLINEJUDGE_SESSION_UER) User user, ModelMap modelMap) {
        modelMap.addAttribute("online_judge_locuser", user);
        return "index/home";
    }

    @ModelAttribute("online_judge_locuser")
    public User getUser() {
        return null;
    }
}
