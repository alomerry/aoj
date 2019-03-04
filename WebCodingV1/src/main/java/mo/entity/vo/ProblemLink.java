package mo.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.Problem;
import mo.entity.po.User;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProblemLink {
    private Problem problem;
    private User created_by;

    public ProblemLink(Problem problem, User created_by) {
        this.problem = problem;
        this.created_by = created_by;
    }
}
