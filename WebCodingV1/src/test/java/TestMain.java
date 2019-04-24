import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import mo.entity.po.Privilege;
import mo.entity.po.User;
import mo.entity.vo.UserLink;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import mo.utils.FileUtils;
import mo.utils.JWTUtils;
import org.junit.Test;
//import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestMain {


    @Test
    public void test01() {
        Timestamp timestamp = new Timestamp(1550048298062L);
        System.out.println(timestamp.toLocalDateTime());
    }

    @Test
    public void test02() {
//        Jedis jedis = new Jedis("192.168.211.128", 6379);
//        jedis.auth("120211");
//        System.out.println(jedis.ping());
    }

    @Test
    public void test03() {
        System.out.println(getJWS());
    }

    @Test
    public void test04() {
        try {
            Map<String, Object> map = parser(getJWS());//eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJvaiIsImxldmVsIjoiYWRtaW4iLCJpc3MiOiJtbyIsImV4cCI6MTU1MDY1NDk1MSwiaWF0IjoxNTUwNjU0OTUxLCJqdGkiOiIzMzAifQ.1Z3VJtlRUXKAZc650412bZ7aqBcOsM827-gPZ-70SVk
            for (Map.Entry entry : map.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            System.out.println("token 过期");
        }
    }

    @Test
    @AuthCheck({RequiredType.JWT, RequiredType.LOGIN})
    public void test05() {
        String jws = getJWS();
//        System.out.println(jws);
        Jws<Claims> claimsJws = parser(jws, 1);
        String newJws = Jwts.builder().setHeader((Map<String, Object>) claimsJws.getHeader()).setClaims(claimsJws.getBody()).signWith(SignatureAlgorithm.forName(claimsJws.getHeader().getAlgorithm()), "morizunzhu").compact();
        System.out.println(newJws.equals(jws));
//        System.out.println(parser(jws, 1));
//        System.out.println(Jwts.builder().setClaims(parser(jws, 1)).compact());
    }

    public static void main(String[] a) throws NoSuchMethodException {
        /*Class<?> clasz = new TestMain().getClass();
        Method method = clasz.getMethod("test05", null);

        System.out.println(method.getAnnotation(AuthCheck.class));*/
        User user = new User();
        user.setNickname("mo");
        Privilege privilege = new Privilege();
        privilege.setRightstr("admin");
        UserLink userLink = new UserLink(user, privilege);
        String old = JWTUtils.makeToken(userLink);
        Jws<Claims> jwt = JWTUtils.parser(old);
        System.out.println(jwt.getBody().get("level"));
    }

    private String getJWS() {
        long now = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("level", "admin");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("mo")
                .setId(String.valueOf(330))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 60000 * 10))
                .setAudience("oj")
                .signWith(SignatureAlgorithm.HS256, "morizunzhu").compact();
    }

    private static Claims parser(String jws) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey("morizunzhu").parseClaimsJws(jws).getBody();
    }

    private static Jws<Claims> parser(String jws, int i) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey("morizunzhu").parseClaimsJws(jws);
    }

    @Test
    public void test06() {
        String test = "666";
        JSONObject json = new JSONObject();
        json.put("name", test);
        System.out.println(json);
    }

    @Test
    public void test07() {
        InetAddress address = InetAddress.getLoopbackAddress();
        System.out.println(address.getHostAddress());
    }

    @Test
    public void testUnzip() {
        File file = new File("C:\\Users\\wu1ji\\Desktop\\Project\\OnlineJudge\\WebCodingV1\\src\\main\\webapp\\problem_cases\\1\\20977.zip");
        String path = "C:\\Users\\wu1ji\\Desktop\\Project\\OnlineJudge\\WebCodingV1\\src\\main\\webapp\\problem_cases\\1";
        try {
            FileUtils.ZipFileDecompression(file, path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
