package mo.entity.vo.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.Contest;
import mo.entity.po.ContestApply;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ContestApplyLink {
    private ContestApply contestApply;
    private Contest contest;

    public ContestApplyLink(ContestApply contestApply, Contest contest) {
        this.contestApply = contestApply;
        this.contest = contest;
    }
}
