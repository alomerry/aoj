package mo.core;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PermissionManager {

    private static Map<Character, Permission> PERMISSION_MAP = new HashMap<>();

    static {
        PERMISSION_MAP.put('b', Permission.Authority_manager);
        PERMISSION_MAP.put('c', Permission.Topic_adder);
        PERMISSION_MAP.put('f', Permission.Contest_organizer);
        PERMISSION_MAP.put('g', Permission.Contest_participant);
        PERMISSION_MAP.put('h', Permission.Code_viewer);
        PERMISSION_MAP.put('j', Permission.Manual_judger);
        PERMISSION_MAP.put('k', Permission.Remote_judger);
        PERMISSION_MAP.put('l', Permission.Announcement_manager);
        PERMISSION_MAP.put('o', Permission.User_manager);
    }

    //admin-user
    private static final char[] admin_types = {'b', 'c', 'f', 'g', 'h', 'j', 'k', 'l', 'o'};//bcfghjklo

    /**
     * 判断某个分组信息是否包含指定管理员权限
     *
     * @param admin_type 需要判断的用户级别
     * @param rightstr   用户分组信息
     * @return 是否是该级别管理员
     */
    public static boolean isLegalAdmin(Permission admin_type, String rightstr) {
        return rightstr.contains("" + admin_types[admin_type.code()]);
    }

    /**
     * 判断某个分组信息是否包含全部指定管理员权限
     *
     * @param admin_types 需要判断的权限
     * @param rightstr    用户分组信息
     * @return 是否满足
     */
    public static boolean isAllLegalAdmins(Permission[] admin_types, String rightstr) {
        if (admin_types == null) {
            return true;
        }
        for (Permission type : admin_types) {
            if (!isLegalAdmin(type, rightstr)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断某个分组信息是否包含其中一个管理员权限
     *
     * @param admin_types 需要判断的权限
     * @param rightstr    用户分组信息
     * @return 是否满足
     */
    public static boolean isOneLegalAdmin(Permission[] admin_types, String rightstr) {
        for (Permission type : admin_types) {
            if (isLegalAdmin(type, rightstr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否含有管理员权限
     *
     * @param rightstr 用户分组信息
     * @return 是否是管理员
     */
    public static boolean isAdmin(String rightstr) {
        return rightstr.contains("admin");
    }

    /**
     * 判断修改的权限
     *
     * @param oldLevel 旧权限级别
     * @param newLevel 新权限级别
     * @return 修改的权限级别
     */
    public static Permission[] changedLevel(String oldLevel, String newLevel) {
        String min = oldLevel.length() < newLevel.length() ? oldLevel : newLevel;
        String max = oldLevel.length() >= newLevel.length() ? oldLevel : newLevel;
        StringBuilder builder = new StringBuilder();
        if (isAdmin(min)) {//管理员-管理员+
            String maxItem = max.substring(max.indexOf("admin") + 5);
            for (char max_i : (maxItem).toCharArray()) {
                if (!min.contains("" + max_i) && max_i != '_') {
                    builder.append("" + max_i);
                }
            }
        } else {//用户-用户+
            if (isAdmin(max)) {//用户-管理员
                max = max.substring(max.indexOf("admin") + 5);
                for (char item : max.toCharArray()) {
                    if (item != '_') {
                        builder.append("" + item);
                    }
                }
            } else {//用户-用户
                return null;
            }
        }
        min = builder.toString();
        Permission[] permissions = new Permission[min.length()];
        for (int i = 0; i < min.length(); i++) {
            permissions[i] = PERMISSION_MAP.get(min.charAt(i));
        }
        return permissions;
    }
}
