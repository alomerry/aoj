package mo.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mo.entity.po.Contest;
import mo.entity.po.Problem;

@Getter
@Setter
public class ContestProblemLink {
    private Contest contest;
    private Problem problem;
    private String title;
    private Integer num;

    public ContestProblemLink(Contest contest, Problem problem, String title, Integer num) {
        this.contest = contest;
        this.problem = problem;
        this.title = title;
        this.num = num;
    }
}
