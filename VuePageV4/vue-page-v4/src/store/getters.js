export default ({
    localUserId: function (state) {
        return state.user.user_id;
    },

    isLevel: function (state, getters) {
        return function (judgeLevel) {
            if (state.level == null) {
                return false;
            }
            switch (judgeLevel) {
                case "Authority_manager": {
                    judgeLevel = "b";
                    break;
                }
                case "Topic_adder": {
                    judgeLevel = "c";
                    break;
                }
                case "Contest_organizer": {
                    judgeLevel = "f";
                    break;
                }
                case "Contest_participant": {
                    judgeLevel = "g";
                    break;
                }
                case "Code_viewer": {
                    judgeLevel = "h";
                    break;
                }
                case "Manual_judger": {
                    judgeLevel = "j";
                    break;
                }
                case "Remote_judger": {
                    judgeLevel = "k";
                    break;
                }
                case "Announcement_manager": {
                    judgeLevel = "o";
                    break;
                }
                case "User_manager": {
                    judgeLevel = "l";
                    break;
                }
            }
            return state.level.indexOf(judgeLevel) != -1;
        }
    }
});
/**
 * Authority_manager(0),//权限管理者 b
 * Topic_adder(1),//题目添加者 c
 * Contest_organizer(2),//比赛组织者 f
 * Contest_participant(3),//比赛参加者 g
 * Code_viewer(4),//代码查看者 h
 * Manual_judger(5),//手动判题者 j
 * Remote_judger(6),//远程判题者 k
 * Announcement_manager(7),//公告管理者 l
 * User_manager(8);//用户管理者 o
 */