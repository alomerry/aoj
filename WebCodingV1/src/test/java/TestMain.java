import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import mo.entity.po.main.Privilege;
import mo.entity.po.main.User;
import mo.entity.vo.link.UserLink;
import mo.utils.JWTUtils;
import org.junit.Test;
import org.springframework.util.DigestUtils;
//import redis.clients.jedis.Jedis;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMain {


    public void test01() {
        Timestamp timestamp = new Timestamp(1550048298062L);
        System.out.println(timestamp.toLocalDateTime());
    }

    public void test02() {
//        Jedis jedis = new Jedis("192.168.211.128", 6379);
//        jedis.auth("120211");
//        System.out.println(jedis.ping());
    }

    public void test03() {
        System.out.println(getJWS());
    }

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

    //    @AuthCheck({RequiredType.JWT, RequiredType.LOGIN})
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

    public void test06() {
        String test = "666";
        JSONObject json = new JSONObject();
        json.put("name", test);
        System.out.println(json);
    }

    public void test07() {
        InetAddress address = InetAddress.getLoopbackAddress();
        System.out.println(address.getHostAddress());
    }

    public void test08() {
        List<String> a = null;
        for (String s : a) {
            System.out.println(s);
        }
    }

    @Test
    public void test09() {
        String imageName = "vue.jpeg";
        System.out.println(imageName.lastIndexOf("."));
        System.out.println(imageName.substring(imageName.lastIndexOf(".") + 1));
    }

    @Test
    public void test10() {
        String pwd = "8473ff4338e3da18e3e4568101e429fc";
        String salt = "#&***#$#@";
        System.out.println(DigestUtils.md5DigestAsHex((pwd + salt).getBytes()));
    }

    @Test
    public void test11() {
        Object obj = new Object();
        System.out.println(obj instanceof String);
    }
}
