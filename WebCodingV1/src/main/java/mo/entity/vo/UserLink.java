package mo.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.Privilege;
import mo.entity.po.User;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserLink {
    public UserLink(User user, Privilege privilege) {
        this.user = user;
        this.privilege = privilege;
    }

    private User user;
    private Privilege privilege;
}
