package mo.entity.vo.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.Contest;
import mo.entity.po.ContestApply;
import mo.entity.po.User;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ContestApplyLink {
    private ContestApply contestApply;
    private Contest contest;
    private User user;

    public ContestApplyLink(ContestApply contestApply, Contest contest, User user) {
        this.contestApply = contestApply;
        this.contest = contest;
        this.user = user;
    }
}
