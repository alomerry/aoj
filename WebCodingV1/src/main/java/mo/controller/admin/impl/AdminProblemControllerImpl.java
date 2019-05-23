package mo.controller.admin.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import mo.controller.AbstractController;
import mo.controller.admin.AdminProblemController;
import mo.core.Permission;
import mo.core.PermissionManager;
import mo.core.Result;
import mo.core.ResultCode;
import mo.entity.po.Privilege;
import mo.entity.po.Problem;
import mo.entity.po.Tag;
import mo.entity.vo.ProblemTagTestCase;
import mo.entity.vo.link.ProblemLink;
import mo.entity.vo.link.UserLink;
import mo.exception.ServiceException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        String defunct = PermissionManager.isLegalAdmin(Permission.Topic_adder, level) ? "(0,1,2,3)" : (PermissionManager.isAdmin(level) ? "(1,2,3)" : "(1)");
        JSONObject problems = new JSONObject();
        switch (resType) {
            case "simple": {
                problems.put("problems", problemService.findSimpleProblemLinksByDefunct(defunct, userLink.getUser().getUser_id(), Integer.valueOf(page), Integer.valueOf(per_page)));
//                logger.info("json problems[{}]", JSON.toJSONString(problems, SerializerFeature.DisableCircularReferenceDetect));
//                logger.info("json result[{}]", new JSONObject());
                //查询页码信息
                logger.info("题目总数[{}]", problemService.findProblemTotalNumByDefunct(defunct));
                problems.put("total", problemService.findProblemTotalNumByDefunct(defunct));
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
    @RequestMapping(value = "/admin/defunct/{defunct}/problems", method = RequestMethod.GET)
    public Result problems(@RequestParam(value = "resType", defaultValue = "simple") String resType,
                           @RequestParam(value = "page", defaultValue = "1") String page,
                           @RequestParam(value = "per_page", defaultValue = "10") String per_page,
                           @PathVariable String defunct) {
        JSONObject problems = new JSONObject();
        switch (resType) {
            case "simple": {
                problems.put("problems", problemService.findSimpleProblemLinksByDefunct("(" + defunct + ")", getJWTUserId(), Integer.valueOf(page), Integer.valueOf(per_page)));
                //查询页码信息
                problems.put("total", problemService.findProblemTotalNumByDefunctAndOwn("(" + defunct + ")", getJWTUserId()));
                break;
            }
            case "detail": {
                problems.put("problems", problemService.findProblemsByPageAndPerPage("(" + defunct + ")", getJWTUserId(), Integer.valueOf(page), Integer.valueOf(per_page)));
                problems.put("total", problemService.findProblemTotalNumByDefunctAndOwn("(" + defunct + ")", getJWTUserId()));
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
            File underDel = new File(getHttpServletRequest().getServletContext().getRealPath("/problem_cases") + File.separator + problem_id);
            switch (problemService.deleteProblemByProblemId(problem_id, underDel)) {
                case -1: {
                    return new Result().setCode(ResultCode.FORBIDDEN).setMessage("测试文件删除失败!");
                }
                case 1: {
                    return new Result().setCode(ResultCode.OK).setMessage("删除成功!");
                }
                default: {
                    return new Result().setCode(ResultCode.FORBIDDEN).setMessage("内部错误，删除失败!");
                }
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

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/problem", method = RequestMethod.POST)
    public Result createNewProblem(@RequestParam("problem") String problem,
                                   @RequestParam("tags") String tags,
                                   @RequestParam("testCaseId") String testCaseId) {
        /**
         * 1.判断测试文件是否存在
         * 2.插入题目
         *  2.1判断标签是否存在，不存在则新建
         *  2.2绑定标签和题目
         * 3.更改文件夹名称
         */
        //json转JavaBean
        Problem pro = JSON.parseObject(problem, new TypeReference<Problem>() {
        });
        List<Tag> tagList = JSON.parseObject(tags, new TypeReference<ArrayList<Tag>>() {
        });
        logger.info("Problem[{}]\nTags[{}]\nTestCaseId[{}]", pro, tagList, testCaseId);

        File testcase = new File(getHttpServletRequest().getServletContext().getRealPath("/problem_cases") + File.separator + testCaseId);
        if (!testcase.exists()) {
            //文件不存在
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("Please upload test case!");
        }
        try {
            pro.setProblem_id(problemService.insertNewProblemAndTags(pro, tagList, getJWTUserId()));
            testcase.renameTo(new File(getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + pro.getProblem_id()));
            return new Result().setCode(ResultCode.OK).setMessage("题目新建成功!");
        } catch (ServiceException e) {
            e.printStackTrace();
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage(e.getMessage());
        }
    }

    @Override
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/problem", method = RequestMethod.PUT, consumes = "application/json")
    public Result updateProblem(@RequestBody ProblemTagTestCase problemTagTestCase) {
        /**
         * 1.判断权限
         * 2.判断测试文件是否存在
         * 3.插入题目
         *  3.1 题目信息更新
         *  3.2 Tag更新
         *   3.2.1
         *  3.3 文件更新
         *   3.3.1 修改旧文件名称,保存
         *   3.3.2 修改新文件名称
         *   3.3.3 删除就文件
         */
        Integer operator_id = getJWTUserId();
        logger.info("获取到更新数据[{}]", problemTagTestCase);
        Problem problem = problemTagTestCase.getProblem();
        List<Tag> tags = problemTagTestCase.getTags();
        String testCaseId = problemTagTestCase.getTestCaseId();
        if (problem.getCreate_by().equals(operator_id) || PermissionManager.isLegalAdmin(Permission.Topic_adder, privilegeService.findPrivilegeByUserId(operator_id).getRightstr())) {
            //题目信息更新
            if (problemService.updateProblemInfo(problemTagTestCase.getProblem())) {
                //更新文件
                if (testCaseId != null && testCaseId != "") {
                    File oldCase = new File(getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + problem.getProblem_id());
                    File oldTemp = new File(getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + (System.currentTimeMillis() + StringUtils.generateString(6)));
                    if (oldCase.renameTo(oldTemp)) {
                        File newCase = new File(getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + testCaseId);
                        if (newCase.renameTo(new File(getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + problem.getProblem_id()))) {
                            for (File cases : oldTemp.listFiles()) {
                                cases.delete();
                            }
                            oldTemp.delete();
                        } else {
                            oldTemp.renameTo(new File(getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + problem.getProblem_id()));
                            for (File cases : newCase.listFiles()) {
                                newCase.delete();
                            }
                            newCase.delete();
                            return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("测试用例更新失败!");
                        }
                    } else {
                        File newCase = new File(getHttpServletRequest().getServletContext().getRealPath("problem_cases") + File.separator + testCaseId);
                        newCase.delete();
                        return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("测试用例更新失败!");
                    }
                }
                //更新Tag
                if (problemService.updateProblemTags(tags, problem.getProblem_id())) {
                    return new Result().setCode(ResultCode.OK);
                } else {
                    return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("题目标签更新失败!");
                }
            } else {
                return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("题目信息更新失败!");
            }
        } else {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("权限不足!");
        }
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/problem/{id}", method = RequestMethod.GET)
    public Result problem(@PathVariable Integer id) {
        ProblemLink problem = new ProblemLink();
        problem.setProblem(problemService.findProblemByProblemId(id));
        if (problem.getProblem() == null) {
            return new Result().setCode(ResultCode.NOT_FOUND).setMessage("问题不存在!");
        } else {
            problem.setCreated_by(userService.findUserByUserId(problem.getProblem().getCreate_by()));
            JSONObject json = new JSONObject();
            json.put("result", problem);
            return new Result().setCode(ResultCode.OK).setData(json);
        }
    }

    @Override
    @ResponseBody
    @AuthCheck({RequiredType.JWT, RequiredType.ADMIN})
    @RequestMapping(value = "/admin/problem/{problem_id}/state", method = RequestMethod.PUT)
    public Result problemDisableState(@PathVariable Integer problem_id, @RequestParam("state") String state) {
        Privilege privilege = privilegeService.findPrivilegeByUserId(getJWTUserId());
        Problem problem = problemService.findProblemByProblemId(problem_id);
        if (problem.getCreate_by() != getJWTUserId() && !PermissionManager.isLegalAdmin(Permission.Topic_adder, privilege.getRightstr())) {
            return new Result().setCode(ResultCode.FORBIDDEN).setMessage("权限不足！");
        } else {
            if (problemService.updateProblemDefunct(problem_id, state)) {
                return new Result().setCode(ResultCode.OK).setMessage("修改成功！");
            } else {
                return new Result().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("修改失败！");
            }
        }
    }
}
