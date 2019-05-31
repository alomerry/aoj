package mo.entity.po.mini;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JWT {
    private Integer jwt_id;
    private Integer user_id;
}
