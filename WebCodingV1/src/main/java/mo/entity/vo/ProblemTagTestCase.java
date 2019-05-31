package mo.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.main.Problem;
import mo.entity.po.main.Tag;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProblemTagTestCase {
    Problem problem;
    List<Tag> tags;
    String testCaseId;
}
