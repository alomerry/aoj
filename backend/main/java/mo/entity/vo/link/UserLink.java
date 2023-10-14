package mo.entity.vo.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.main.Privilege;
import mo.entity.po.main.User;

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
