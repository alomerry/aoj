package mo.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Tag {
    //标签id
    private Integer tag_id;
    //标签名
    private String tagname;
}
