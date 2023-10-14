package mo.utils;

import io.jsonwebtoken.*;
import mo.entity.vo.link.UserLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    //jwt签发者
    private static final String ISSUER = "mo.hyit";
    //jwt的过期时间，这个过期时间必须要大于签发时间
    private static final Integer EXPAIRTION = 20;
    //版本
    private static final String VERSION = "V 1.0.0";
    //morizunzhu
    private static final String Key = "morizunzhu";
    //秘钥
    private static final String secretKey = "mo.hyit." + Key + "." + VERSION;

    /**
     * 生成Token
     *
     * @param user 用户实体
     * @return token
     */
    public static String makeToken(UserLink user) {
        return makeToken(user, EXPAIRTION);
    }

    /**
     * 生成Token
     *
     * @param user    用户实体
     * @param minutes 过期时间
     * @return token
     */
    public static String makeToken(UserLink user, Integer minutes) {
        long now = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("level", user.getPrivilege().getRightstr());
        //TODO Redis中添加密钥用于撤消已颁发的token
        return Jwts.builder().setClaims(claims).setIssuer(ISSUER)
                .setId(String.valueOf(user.getUser().getUser_id()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 60000 * minutes))
                .setAudience(user.getUser().getUsername())
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();

    }

    /**
     * 解析签名的jwt
     *
     * @param jws 签名的请求
     * @return jws串
     * @throws ExpiredJwtException   令牌过期
     * @throws MalformedJwtException 失去载体
     * @throws SignatureException    签名不一致
     */
    public static Jws<Claims> parser(String jws) throws ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jws);
    }

    /**
     * 判断jwt是否正确
     *
     * @param jws 签名
     * @return 返回签发是否合法
     */
    public static boolean checkLegal(String jws) throws ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        Jws<Claims> jwt = null;
        try {
            jwt = parser(jws);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            throw new IllegalArgumentException();
        }
        String alg = jwt.getHeader().getAlgorithm();
        String JWS = Jwts.builder().setHeader((Map<String, Object>) jwt.getHeader()).setClaims(jwt.getBody()).signWith(SignatureAlgorithm.forName(alg), secretKey).compact();
        logger.info("\nold:[{}]\nnew:[{}]", jws, JWS);
        return JWS.equals(jws);
    }

    /**
     * 获取jwt中的body数据
     *
     * @param jws 加密串
     * @param key 数据key
     * @return 数据value
     */
    public static Object getBodyValue(String jws, Object key) {
        return parser(jws).getBody().get(key);
    }
}
