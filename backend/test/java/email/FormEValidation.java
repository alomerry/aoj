package email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FormEValidation {
    private String remark;
    private String emails;
    private int emailSate;

    public FormEValidation(int emailSate) {
        this.emailSate = emailSate;
    }
}
