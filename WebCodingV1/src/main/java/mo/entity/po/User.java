package mo.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户表
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User implements Serializable {
    private Integer user_id;//用户id（主键）
    private String username;//用户名
    private String nickname;//用户昵称
    private String passwd;//密码（加密）
    private String school;//用户所在学校
    private String email;//用户E-mail
    private String remark;//用户备注
    private Integer submit;//用户提交次数
    private Integer solved;//成功次数
    private Timestamp access_time;//用户注册时间
    private Timestamp last_login;//用户上次登录时间
    private String session_id;//sessionId

    public User(String username, String nickname, String passwd, String school, String email) {
        this.username = username;
        this.nickname = nickname;
        this.passwd = passwd;
        this.school = school;
        this.email = email;
    }
}
