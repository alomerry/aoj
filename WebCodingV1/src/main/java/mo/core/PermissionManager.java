package mo.core;


public final class PermissionManager {

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
     * 判断是否含有管理员权限
     *
     * @param rightstr 用户分组信息
     * @return 是否是管理员
     */
    public static boolean isAdmin(String rightstr) {
        return rightstr.contains("admin");
    }
}
