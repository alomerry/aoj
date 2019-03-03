import io.jsonwebtoken.*;
import mo.interceptor.annotation.AuthCheck;
import mo.interceptor.annotation.RequiredType;
import org.junit.Test;
//import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
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
        Class<?> clasz = new TestMain().getClass();
        Method method = clasz.getMethod("test05", null);

        System.out.println(method.getAnnotation(AuthCheck.class));
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
}
