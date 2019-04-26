package mo.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class News {
    private Integer news_id;//新闻Id
    private Integer user_id;//用户Id
    private String title;//新闻标题
    private String content;//新闻内容
    private Timestamp update_time;//更新时间
    private Timestamp create_at;//创造时间
    private Integer contest_id;//所属竞赛号
    private String defunct;//公开状态
}
