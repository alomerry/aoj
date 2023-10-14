package mo.entity.po.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SourceCode {
    private String solution_id;//运行id
    private String source;//源代码

    public SourceCode(String source) {
        this.source = source;
    }
}
