package mo.entity.po.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContestProblem {
    private Integer problem_id;
    private Integer contest_id;
    private String title;
    private Integer num;
}
