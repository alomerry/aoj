package mo.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mo.entity.po.Privilege;
import mo.entity.po.User;

@Getter
@Setter
@NoArgsConstructor
public class UserLink {
    public UserLink(User user, Privilege privilege) {
        this.user = user;
        this.privilege = privilege;
    }

    private User user;
    private Privilege privilege;
}
