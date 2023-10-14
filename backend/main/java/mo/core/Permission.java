package mo.core;

public enum Permission {//user admin [a]bc[d][e]fgh[i]jkl[m][n]opq[r][s]t[u]vwxyz
    Authority_manager(0),//权限管理者 b
    Topic_adder(1),//题目添加者 c
    Contest_organizer(2),//比赛组织者 f
    Contest_participant(3),//比赛参加者 g
    Code_viewer(4),//代码查看者 h
    Manual_judger(5),//手动判题者 j
    Remote_judger(6),//远程判题者 k
    Announcement_manager(7),//公告管理者 l
    User_manager(8);//用户管理者 o

    private final int code;

    Permission(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
