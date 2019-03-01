package mo.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mo.utils.string.StringValue;

import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
public class Contest {
    //竞赛Id
    private Integer contest_id;
    //竞赛标题
    private String title;
    //开始时间(年月日时分)
    private Timestamp start_time;
    //结束时间(年月日时分)
    private Timestamp end_time;
    //公开/内部（0/1）
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
    private StringValue.CONTEST_STATUS status;
    //比赛上限人数
    private Integer max;
    //当前人数
    private Integer now;

    private static Timestamp nowtime = new Timestamp(System.currentTimeMillis());

    public void updateStatus() {
        nowtime.setTime(System.currentTimeMillis());
        if (nowtime.after(end_time)) {
            status = StringValue.CONTEST_STATUS.STATUS_OVER;
        } else if (nowtime.after(start_time)) {
            status = StringValue.CONTEST_STATUS.STATUS_RUNNING;
        } else if (nowtime.before(start_time) && !access) {
            status = StringValue.CONTEST_STATUS.STATUS_READY;
        } else {
            status = StringValue.CONTEST_STATUS.STATUS_AVAILABLE;
        }
    }

    public int comparaToNow() {
        nowtime.setTime(System.currentTimeMillis());
        int res = nowtime.compareTo(start_time);
        if (res == -1) {
            return -1;
        } else if (res >= 0 && nowtime.compareTo(end_time) == -1) {
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public String toString() {
        return "Contest{" +
                "contest_id=" + contest_id +
                ", title='" + title + '\'' +
                ", privates=" + privates +
                ", access=" + access +
                ", status=" + status +
                '}';
    }

}
