package mo.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.Privilege;
import mo.entity.po.User;

/**
 * 管理员管理用户时使用的VO
 *  与用户po不同的是UserLink包含用户权限
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserLink {

    private User user;

    private Privilege privilege;

    public UserLink(User user, Privilege privilege) {
        this.user = user;
        this.privilege = privilege;
    }

}
