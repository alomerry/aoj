package mo.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户表
 */
@NoArgsConstructor
@Setter
@Getter
public class User implements Serializable {
    //用户id（主键）
    private Integer user_id;
    //用户名
    private String username;
    //用户昵称
    private String nickname;
    //密码（加密）
    private String passwd;
    //用户所在学校
    private String school;
    //用户E-mail
    private String email;
    //用户备注
    private String remark;
    //用户提交次数
    private Integer submit;
    //成功次数
    private Integer solved;
    //用户注册时间
    private Timestamp access_time;
    //sessionId
    private String session_id;

    public User(String username, String nickname, String passwd, String school, String email) {
        this.username = username;
        this.nickname = nickname;
        this.passwd = passwd;
        this.school = school;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", access_time=" + access_time +
                '}';
    }
}
