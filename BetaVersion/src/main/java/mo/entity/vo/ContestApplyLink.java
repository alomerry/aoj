package mo.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mo.entity.po.Contest;
import mo.entity.po.User;

@NoArgsConstructor
@Getter
@Setter
public class ContestApplyLink {
    private User user;
    private Contest contest;
    private int status;

    public ContestApplyLink(User user, Contest contest, int status) {
        this.user = user;
        this.contest = contest;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContestApplyLink{" +
                "user=" + user +
                ", contest=" + contest +
                ", status=" + status +
                "}\n";
    }
}
