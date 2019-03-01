package mo.controller.impl;

import mo.service.TestService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class TestController {

    @Resource
    private TestService testService;

    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("test")
    public String test() {
        return "test/test";
    }

    @RequestMapping("testUrl")
    public String testUrl(String url) {
        return url;
    }

    @RequestMapping("test_trascationB")
    public String testTrascationB(int contest_id, int user_id) {
        testService.conbimeB(user_id, contest_id);
        return "test/test";
    }

    @RequestMapping("test_trascationA")
    public String testTrascationA(int contest_id, int user_id) {
        testService.conbimeA(user_id, contest_id);
        return "test/test";
    }

    @ResponseBody
    @RequestMapping(value = "/test_searchNull", headers = "Accept=application/json")
    public String testSearchNull(@RequestBody Map<String, Object> data) {
        logger.error("查询结果", testService.searchNull(Integer.parseInt((String) data.get("problem_id"))));
        return new JSONObject().put("res", testService.searchNull(Integer.parseInt((String) data.get("problem_id")))).toString();
    }
}
