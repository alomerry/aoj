package mo.entity.po.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CompileInfo {
    private String solution_id;
    private String error;
}
