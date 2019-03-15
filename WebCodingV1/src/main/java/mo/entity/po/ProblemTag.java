package mo.entity.po;

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
}
