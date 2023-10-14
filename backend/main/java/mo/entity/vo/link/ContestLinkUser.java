package mo.entity.vo.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.main.Contest;
import mo.entity.po.main.User;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ContestLinkUser {
    private Contest contest;
    private User created_by;

    public ContestLinkUser(Contest contest, User created_by) {
        this.contest = contest;
        this.created_by = created_by;
    }
}
