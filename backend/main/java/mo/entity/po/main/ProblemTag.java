package mo.entity.po.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProblemTag {
    private Integer id;
    private Integer tag_id;
    private Integer problem_id;

    public ProblemTag(Integer tag_id, Integer problem_id) {
        this.tag_id = tag_id;
        this.problem_id = problem_id;
    }
}
