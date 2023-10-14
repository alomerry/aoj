package mo.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mo.entity.po.main.Contest;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ContestNumber {
    private Contest contest;
    private int apply_number;

    public ContestNumber(Contest contest, int apply_number) {
        this.contest = contest;
        this.apply_number = apply_number;
    }
}
