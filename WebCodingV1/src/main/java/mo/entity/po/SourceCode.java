package mo.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SourceCode {
    private Integer solution_id;//运行id
    private String source;//源代码

    public SourceCode(Integer solution_id, String source) {
        this.solution_id = solution_id;
        this.source = source;
    }
}
