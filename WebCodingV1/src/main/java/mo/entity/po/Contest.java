package mo.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Contest {
    //竞赛Id
    private Integer contest_id;
    //竞赛标题
    private String title;
    //开始时间(年月日时分)
    private Timestamp start_at;
    //结束时间(年月日时分)
    private Timestamp end_at;
    //内部/公开（0/1）
    private Integer privates;
    //主办方
    private String organizer;
    //是否可报名
    private boolean access;
    //竞赛描述
    private String describes;
    //创建者Id
    private Integer user_id;
    //状态
//    private StringValue.CONTEST_STATUS status;
    //比赛上限人数
    private Integer max;
    //当前人数
    private Integer now;

}
