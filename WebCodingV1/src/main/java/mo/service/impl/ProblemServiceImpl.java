package mo.service.impl;

import mo.dao.*;
import mo.entity.po.*;
import mo.entity.vo.link.ProblemLink;
import mo.entity.vo.link.ProblemTagLink;
import mo.exception.ServiceException;
import mo.service.ProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    private static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);

    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ContestProblemMapper contestProblemMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ProblemTagMapper problemTagMapper;

    @Override
    public Problem findProblemByProblemId(Integer problem_id) {
        return problemMapper.findProblemByProblemId(problem_id);
    }

    @Override
    public List<Problem> findSimpleProblemsByDefunct(String defunct, Integer page, Integer per_page) {
        return problemMapper.findSimpleProblemsByDefunctAndPage(defunct, (page - 1) * per_page, per_page);
    }

    @Override
    public List<Problem> findSimpleProblemsByDefunct(String defunct, Integer user_id, Integer page, Integer per_page) {
        return problemMapper.findSimpleProblemsWithOwnByDefunctAndPage(defunct, user_id, (page - 1) * per_page, per_page);
    }

    @Override
    public List<ProblemLink> findSimpleProblemLinksByDefunct(String defunct, Integer user_id, Integer page, Integer per_page) {
        User user = userMapper.findUserByUserId(user_id);
        List<Problem> problems = problemMapper.findSimpleProblemsWithOwnByDefunctAndPage(defunct, user_id, (page - 1) * per_page, per_page);
        List<ProblemLink> problemLinks = new ArrayList<>(problems.size() + 3);
        ProblemLink pl = null;
        for (Problem p : problems) {
//            if (user.getUser_id().equals(p.getCreate_by())) {
//                problemLinks.add(new ProblemLink(p, user));
//            } else {
//            }
            problemLinks.add(new ProblemLink(p, userMapper.findUserByUserId(p.getCreate_by())));
        }
        logger.info("problemLink[{}]", problemLinks);
        return problemLinks;
    }

    @Override
    public List<Problem> findProblemsByPageAndPerPage(String defunct, Integer page, Integer per_page) {
        return problemMapper.findProblemsByDefunctAndPage(defunct, (page - 1) * per_page, per_page);
    }

    @Override
    public List<Problem> findProblemsByPageAndPerPage(String defunct, Integer user_id, Integer page, Integer per_page) {
        return problemMapper.findProblemsWithOwnByDefunctAndPage(defunct, user_id, (page - 1) * per_page, per_page);
    }

    @Override
    public List<ProblemLink> findSimpleProblemsByPageAndContestId(Integer page, Integer per_page, Integer contest_id) {
        //TODO 是否能优化
        List<ContestProblem> contestProblems = contestProblemMapper.findContestProblemByPageAndContestId((page - 1) * per_page, per_page, contest_id);
        List<ProblemLink> problemLinks = new ArrayList<>(contestProblems.size() + 5);
        Problem problem = null;
        for (ContestProblem cp : contestProblems) {
            problem = problemMapper.findProblemByProblemId(cp.getProblem_id());
            problemLinks.add(new ProblemLink(problem, userMapper.findUserByUserId(problem.getCreate_by())));
        }
        return problemLinks;
    }

    @Override
    public boolean isAbsolutePrivateProblem(Integer problemId) {
        char level = problemMapper.findProblemPublicLevelByProblemId(problemId);
        logger.info("判断题目[{}]的公开级别:[{}]", problemId, level);
        return switchProblemLevel(new int[]{3}, level);
    }

    @Override
    public boolean isDisabledProblem(Integer problemId) {
        char level = problemMapper.findProblemPublicLevelByProblemId(problemId);
        logger.info("判断题目[{}]的公开级别:[{}]", problemId, level);
        return switchProblemLevel(new int[]{0}, level);
    }

    @Override
    public boolean isProblemCreator(Integer problemId, Integer userId) {
        return userId.equals(problemMapper.findCreatorIdByProblemId(problemId));
    }

    @Override
    public int deleteProblemByProblemId(Integer problemId, File underDel) {
        if (problemMapper.deleteProblemByPorblemId(problemId) > 0) {
            if (underDel.delete()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 0;
        }
    }

    @Override
    @Transactional
    public Integer insertNewProblemAndTags(Problem problem, List<Tag> tags, Integer user_id) throws ServiceException {
        problem.setCreated_at(new Timestamp(System.currentTimeMillis()));
        if (problemMapper.insertProblem(problem, user_id) > 0) {
            problem.setProblem_id(problemMapper.findLastInsertId());
            logger.info("题目新建成功!题目Id[{}]", problem.getProblem_id());
            if (tags != null && tags.size() != 0) {
                Integer id = 0;
                for (Tag tag : tags) {
                    tag.setTag_id(tagMapper.findTagIdByTagName(tag.getTagname()));
                    if (tag.getTag_id() == null) {//tag不存在，新建tag
                        tagMapper.insertTag(tag.getTagname());
                        tag.setTag_id(tagMapper.findLastInsertId());
                        logger.info("标签新建成功!标签Id[{}]", tag.getTag_id());
                    }
                    //绑定关系
                    if (problemTagMapper.insertProblemTagWithTagIdAndProblemId(problem.getProblem_id(), tag.getTag_id()) > 0) {
                        logger.info("题目[{}]与标签[{}]绑定成功!", problem.getProblem_id(), tag.getTag_id());
                        return problem.getProblem_id();
                    } else {
                        throw new ServiceException("题目与标签绑定失败");
                    }
                }
            } else {
                return problem.getProblem_id();
            }
        } else {
            logger.info("新建题目失败");
        }
        throw new ServiceException("题目新建失败!");
    }

    @Override
    public boolean updateProblemInfo(Problem problem) throws ServiceException {
        if (problemMapper.updateProblem(problem) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Problem> findSimpleProblemsByTagId(Integer tag_id, int page, int per_page) {
        List<ProblemTag> problemTags = problemTagMapper.findProblemIdFromProblemTagByTagId(tag_id);
        //('11','12');
        if (problemTags == null || problemTags.size() == 0) {
            return null;
        }
        String ids = makeProblemIds(problemTags);
        return problemMapper.findSimpleProblemByProblemIdS(ids, (page - 1) * per_page, per_page);
    }

    @Override
    @Transactional
    public boolean updateProblemTags(List<Tag> tags, Integer problem_id) {
        /**
         * 1.查询题目已有标签
         * 2.对比当前标签 保存新增和不存在的
         * 3.删除不存在的,添加新增的
         */
        if (tags == null || tags.size() == 0) {
            //删除全部
            if (problemTagMapper.findProblemTagNumByProblemId(problem_id) > 0) {
                if (problemTagMapper.deleteProblemTagByProblemId(problem_id) > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            //修改
            List<ProblemTag> problemTags = problemTagMapper.findProblemTagByProblemId(problem_id);
            List<ProblemTagLink> problemTagLink = null;
            if (problemTags == null || problemTags.size() == 0) {
                //添加全部tags
                //TODO 性能优化 批量执行
                problemTags = new ArrayList<>(tags.size() + 3);
                //遍历Tags 判断是否存在,不存在依次插入,存在的则查询tagId
                for (Tag tag : tags) {
                    Integer tagId = 0;
                    if ((tagId = tagMapper.findTagIdByTagName(tag.getTagname())) != null) {
                        problemTags.add(new ProblemTag(tagId, problem_id));
                    } else {
                        if (tagMapper.insertTag(tag.getTagname()) > 0) {
                            problemTags.add(new ProblemTag(tagMapper.findLastInsertId(), problem_id));
                        } else {
                            throw new RuntimeException();
                        }
                    }
                }
                //遍历problemTags 全部插入
                if (problemTagMapper.insertProblemTagList(problemTags) > 0) {
                    return true;
                } else {
                    throw new RuntimeException();
                }
            } else {
                //修改部分
                List<ProblemTag> underDel = null;
                List<ProblemTag> underAdd = new ArrayList<>();

                problemTagLink = new ArrayList<>();
                for (ProblemTag p : problemTags) {
                    problemTagLink.add(new ProblemTagLink(p, tagMapper.findTagByTagId(p.getTag_id())));
                }

                Iterator oldTags = problemTagLink.iterator(), newTags = tags.iterator();
                while (oldTags.hasNext()) {
                    ProblemTagLink oldItem = (ProblemTagLink) oldTags.next();
                    while (newTags.hasNext()) {
                        Tag newItem = (Tag) newTags.next();
                        if (newItem.getTagname().equals(oldItem.getTag().getTagname())) {//如果相等,则此标签不变
                            logger.info("标签[{}]已存在无需修改!", oldItem.getTag().getTagname());
                            newTags.remove();
                            oldTags.remove();
                            break;
                        } else {//如果不等,则此标签需删除
                            if (underDel == null) {
                                underDel = new ArrayList<>();
                            }
                            logger.info("标签[{}]需要删除!", oldItem.getTag().getTagname());
                            underDel.add(oldItem.getProblemTag());
                            oldTags.remove();
                        }
                    }
                }

                //添加新的
                if (tags.size() > 0) {//遍历完毕,tags剩余元素即是待添加元素
                    if (underAdd == null) {
                        underAdd = new ArrayList<>();
                    }
                    for (Tag tag : tags) {
                        int tagId = 0;
                        if ((tagId = tagMapper.findTagIdByTagName(tag.getTagname())) != 0) {
                            underAdd.add(new ProblemTag(tagId, problem_id));
                            logger.info("标签[{}]不存在,需要添加!", tag.getTagname());
                        } else {
                            logger.info("标签[{}]存在,需要添加并绑定关系!", tag.getTagname());
                            if (tagMapper.insertTag(tag.getTagname()) > 0) {
                                underAdd.add(new ProblemTag(tagMapper.findLastInsertId(), problem_id));
                            } else {
                                throw new RuntimeException();
                            }
                        }
                    }
                    if (problemTagMapper.insertProblemTagList(underAdd) <= 0) {
                        throw new RuntimeException();
                    }
                }

                //删除需要删除的
                if (underDel != null) {
                    if (problemTagMapper.deleteProblemTagList(underDel) > 0) {
                        return true;
                    } else {
                        throw new RuntimeException();
                    }
                } else {
                    return true;
                }
            }
        }
    }

    @Override
    public int findProblemTotalNumByDefunct(String defunct) {
        return problemMapper.findProblemTotalNumsByDefunct(defunct);
    }

    @Override
    public int findProblemTotalNumByDefunctAndOwn(String defunct, Integer userId) {
        return problemMapper.findProblemTotalNumByDefunctAndOwn(defunct, userId);
    }

    @Override
    public boolean updateProblemDefunct(Integer problem_id, String defunct) {
        return problemMapper.updateDefunctByProblemId(problem_id, defunct) > 0;
    }

    @Override
    public List<Problem> findProblemsFromContest(Integer contestId) {
        return contestProblemMapper.findProblemsFromContestProblemByContestId(contestId);
    }

    /**
     * 查询题目公开级别是否满足asklLevel级别
     *
     * @param askLevels 是否是此权限
     * @param level     实际公开级别
     * @return 是否满足
     */
    private boolean switchProblemLevel(int[] askLevels, int level) {
        //屏蔽-公开-部分公开-绝对私有0/1/2/3
        for (int i = 0; i < askLevels.length; i++) {
            if (level == askLevels[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接id
     *
     * @param problemTags
     * @return
     */
    private String makeProblemIds(List<ProblemTag> problemTags) {
        StringBuilder sbf = new StringBuilder();
        sbf.append("(");
        boolean index = true;
        for (ProblemTag pId : problemTags) {
            if (index) {
                index = false;
            } else {
                sbf.append(",");
            }
            sbf.append("'").append(pId.getProblem_id()).append("'");
        }
        sbf.append(")");
        logger.info("查询题目Ids[{}]", sbf.toString());
        return sbf.toString();
    }

}
