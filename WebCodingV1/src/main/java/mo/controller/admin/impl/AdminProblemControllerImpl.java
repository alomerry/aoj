package mo.controller.admin.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.AbstractController;
import mo.controller.admin.AdminProblemController;
import mo.core.Permission;
import mo.core.PermissionManager;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Privilege;
import mo.entity.po.Problem;
import mo.entity.po.User;
import mo.entity.vo.ProblemLink;
import mo.entity.vo.UserLink;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.service.PrivilegeService;
import mo.service.ProblemService;
import mo.service.UserService;
import mo.utils.FileUtils;
import mo.utils.JWTUtils;
import mo.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static mo.utils.StringValue.ONLINEJUDGE_SESSION_GROUP;
import static mo.utils.StringValue.ONLINEJUDGE_SESSION_UER;

@RestController
public class AdminProblemControllerImpl extends AbstractController implements AdminProblemController {

    private static final Logger logger = LoggerFactory.getLogger(AdminProblemControllerImpl.class);

    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;

    @Resource
    private PrivilegeService privilegeService;

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/problems", method = RequestMethod.GET)
    public Result problems(@RequestParam(value = "resType", defaultValue = "simple") String resType,
                           @RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page) {
        logger.info("resType[{}],page[{}],per_page[{}]", resType, page, per_page);
        UserLink userLink = userService.findUserLinkByUserId(Integer.valueOf((String) JWTUtils.getBodyValue((((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("jwt")), "jti")));
        logger.info("userLink[{}]", userLink);
        String level = userLink.getPrivilege().getRightstr();
        String defunct = PermissionManager.isLegalAdmin(Permission.Topic_adder, level) ? "(0,1,2,3)" : (PermissionManager.isAdmin(level) ? "(1,2)" : "(1)");
        JSONObject problems = new JSONObject();
        switch (resType) {
            case "simple": {
                problems.put("problems", problemService.findSimpleProblemLinksByDefunct(defunct, userLink.getUser().getUser_id(), Integer.valueOf(page), Integer.valueOf(per_page)));
                break;
            }
            case "detail": {
                problems.put("problems", problemService.findProblemsByPageAndPerPage(defunct, userLink.getUser().getUser_id(), Integer.valueOf(page), Integer.valueOf(per_page)));
                break;
            }
        }
        return new Result().setCode(ResultCode.OK).setData(problems);
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/contest/{contest_id}/problems", method = RequestMethod.GET)
    public Result problems(@RequestParam(value = "resType", defaultValue = "simple") String resType,
                           @RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page,
                           @PathVariable Integer contest_id) {
        JSONObject problems = new JSONObject();
        List<ProblemLink> list;
        switch (resType) {
            case "simple": {
                list = problemService.findSimpleProblemsByPageAndContestId(Integer.valueOf(page), Integer.valueOf(per_page), contest_id);
                problems.put("problems", list);
                break;
            }
            case "detail": {
                break;
            }
        }
        return new Result().setCode(ResultCode.OK).setData(problems);
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/problem/{problem_id}", method = RequestMethod.DELETE)
    public Result deleteProblem(@PathVariable Integer problem_id) {
        /*
         * 1.判断题目是否已有提交
         * 2.无提交则判断权限是否足够，否则删除失败
         * */

        Problem problem = problemService.findProblemByProblemId(problem_id);
        if (problem.getSubmit() > 0) {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("题目已有提交，无法删除！");
        } else if (PermissionManager.isLegalAdmin(Permission.Topic_adder, privilegeService.findPrivilegeByUserId(getJWTUserId()).getRightstr())) {
            if (problemService.deleteProblemByProblemId(problem_id)) {
                return new Result().setCode(ResultCode.OK).setMessage("删除成功!");
            } else {
                return new Result().setCode(ResultCode.FORBIDDEN).setMessage("内部错误，删除失败!");
            }
        } else {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("权限不足！");
        }

    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/problem/test_case", method = RequestMethod.POST)
    public Result uploadTestCase(MultipartFile testCase) {
        if (!testCase.isEmpty()) {
            String dirName = "" + System.currentTimeMillis() + StringUtils.generateString(6);
            String path = getHttpServletRequest().getServletContext().getRealPath("/problem_cases") + File.separator + dirName;
            String filename = testCase.getOriginalFilename();
            File filepath = new File(path, filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中//TODO 优化:直接解压，去除保存到本地这一步
            try {
                File file_out = new File(path + File.separator + filename);
                testCase.transferTo(file_out);
                if (FileUtils.ZipFileDecompression(file_out, path)) {
                    file_out.delete();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("testCase_dir_id", dirName);
                    logger.info("文件保存成功,路径[{}]", path);
                    return new Result().setCode(ResultCode.OK).setData(jsonObject).setMessage("上传成功");
                } else {
                    //压缩包中包含非in/out文件
                    file_out.delete();
                    logger.info("Zip中包含非法文件");
                    return new Result().setCode(ResultCode.FORBIDDEN).setMessage("测试用例只可包含in/out文件!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new Result().setCode(ResultCode.FORBIDDEN).setMessage("文件上传失败");
            }
        } else {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("文件上传失败");
        }
    }
}
