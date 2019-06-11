package mo.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.main.User;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserWithRePwd {
    private User user;
    private String repwd;
}
