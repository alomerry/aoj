package mo.utils;


public final class StringValue {

    //分页级别
    public static final int little_page_num = 5;//每页数量：级别-微小
    public static final int small_page_num = 10;//每页数量：级别-小
    public static final int middle_page_num = 20;//每页数量：级别-中等
    public static final int big_page_num = 50;//每页数量：级别-大
    public static final int large_page_num = 100;//每页数量：级别-巨大

    //
    public static final String USER_GROUP_ORDINARY = "user";//用户分组 普通用户
    public static final String USER_GROUP_ADMIN = "admin";//用户分组 管理员


    public static final String ONLINEJUDGE_SESSION_UER = "online_judge_locuser";//用户登录后的session名称
    public static final String ONLINEJUDGE_SESSION_GROUP = "online_judge_locgroup";//用户分组的session名称

    //登录拦截列表
    public static final String[] LOGIN_REQUIRED_URL = {
            "problem_submit",
            "own_competition_list"
    };

    //OJ状态
    public static final int OJ_WT0 = 0;//Waiting:排队中
    public static final int OJ_WT1 = 1;//Waiting
    public static final int OJ_CI = 2;//compiling
    public static final int OJ_RI = 3;//Running
    public static final int OJ_AC = 4;//Accept:答案正确，请再接再厉。
    public static final int OJ_PE = 5;//Presentation Error:答案基本正确，但是格式不对。
    public static final int OJ_WA = 6;//Wrong Answer:答案不对，仅仅通过样例数据的测试并不一定是正确答案，一定还有你没想到的地方，点击查看系统可能提供的对比信息。
    public static final int OJ_TL = 7;//Time Out of Limit:运行超出时间限制，检查下是否有死循环，或者应该有更快的计算方法
    public static final int OJ_ML = 8;//Merrory Out of Limit:超出内存限制，数据可能需要压缩，检查内存是否有泄露
    public static final int OJ_OL = 9;//Output Out of Limit:输出超过限制，你的输出比正确答案长了两倍，一定是哪里弄错了
    public static final int OJ_RE = 10;//Runtime Error:运行时错误，非法的内存访问，数组越界，指针漂移，调用禁用的系统函数。请点击后获得详细输出
    public static final int OJ_CE = 11;//Compile Error:编译错误，请点击后获得编译器的详细输出
    public static final int OJ_CO = 12;//Competition Over
    public static final int OJ_TR = 13;
    public static final int OJ_JE = 14;//Judge Error

    //msg的key
    public static final String error404_msg = "error_msg";//向404页面发送错误信息的key
    public static final String error404_reason = "error_reason";//向404页面发送错误原因的key
    public static final String res_type_key = "msgType";
    public static final String res_key = "msg";

    //管理员权限判断
    /*public static final String Authority_manager = "Authority_manager";//权限管理者 b
    public static final String Topic_adder = "Topic_adder";//题目添加者 c
    public static final String Match_organizer = "Match_organizer";//比赛组织者 f
    public static final String Match_participant = "Match_participant";//比赛参加者 g
    public static final String Code_viewer = "Code_viewer";//代码查看者 h
    public static final String Manual_judger = "Manual_judger";//远程判题者 j
    public static final String Remote_judger = "Remote_judger";//公告管理者 k
    public static final String Announcement_manager = "Announcement_manager";//权限管理者 l
    public static final String User_manager = "User_manager";//用户管理者 o*/


}
