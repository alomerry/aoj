package mo.permission;


public final class PermissionManager {

    public static final int Authority_manager = 0;//权限管理者 b
    public static final int Topic_adder = 1;//题目添加者 c
    public static final int Match_organizer = 2;//比赛组织者 f
    public static final int Match_participant = 3;//比赛参加者 g
    public static final int Code_viewer = 4;//代码查看者 h
    public static final int Manual_judger = 5;//手动判题者 j
    public static final int Remote_judger = 6;//远程判题者 k
    public static final int Announcement_manager = 7;//公告管理者 l
    public static final char User_manager = 8;//用户管理者 m
    //admin-user
    private static final char[] admin_types = {'b', 'c', 'f', 'g', 'h', 'j', 'k', 'l', 'm'};//bcfghjklm

    /**
     * 判断某个分组信息是否包含指定管理员权限
     *
     * @param admin_type 需要判断的用户级别
     * @param rightstr   用户分组信息
     * @return 是否是该级别管理员
     */
    public static boolean isLegalAdmin(int admin_type, String rightstr) {
        return rightstr.contains("" + admin_types[admin_type]);
    }

}
