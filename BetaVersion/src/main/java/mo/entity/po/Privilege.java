package mo.entity.po;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class Privilege implements Serializable {
    //用户帐号
    private Integer user_id;
    //分组
    private String rightstr;
    //是否屏蔽
    private String defunct;

    public Privilege(Integer user_id, String rightstr) {
        this.user_id = user_id;
        this.rightstr = rightstr;
    }

    public Privilege(String rightstr) {
        this.rightstr = rightstr;
    }
}
