package mo.entity.vo.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.main.Problem;
import mo.entity.po.main.Solution;
import mo.entity.po.main.User;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SolutionLink {
    private User user;
    private Solution solution;
    private Problem problem;

    public SolutionLink(User user, Solution solution, Problem problem) {
        this.user = user;
        this.solution = solution;
        this.problem = problem;
    }
}
