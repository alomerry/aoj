package mo.utils;

public class StringUtils extends org.springframework.util.StringUtils {
    public static String substring(String s, int start, int end) {
        return s.substring(start, end);
    }

    public static boolean equals(String A, String B) {
        return A.equals(B);
    }
}
