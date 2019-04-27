package mo.entity.vo.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.ProblemTag;
import mo.entity.po.Tag;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ProblemTagLink {
    private ProblemTag problemTag;
    private Tag tag;

    public ProblemTagLink(ProblemTag problemTag, Tag tag) {
        this.problemTag = problemTag;
        this.tag = tag;
    }
}
