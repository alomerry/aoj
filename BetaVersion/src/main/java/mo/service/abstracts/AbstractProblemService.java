package mo.service.abstracts;

import mo.service.ProblemService;

public abstract class AbstractProblemService implements ProblemService {


    protected final static int ban_zero = 0;//0
    protected final static int public_one = 1;//1
    protected final static int protect_two = 2;//2
    protected final static int private_three = 3;//3

    /**
     * 公开的题目，屏蔽的题目，管理员可见的题目，私有的题目
     */
    private final static char[] level = {'0', '1', '2', '3'};

    /**
     * 获取公开级别
     *
     * @param defunct 公开级别
     * @return 公开级别
     */
    protected char getPublicLevel(int defunct) {
        if (defunct > level.length) {
            throw new IllegalArgumentException("长度超过限制");
        }
        return level[defunct];
    }
}
