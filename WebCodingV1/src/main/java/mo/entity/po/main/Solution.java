package mo.entity.po.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class Solution {
    //运行id
    private String solution_id;
    //问题id
    private Integer problem_id;
    //用户id
    private Integer user_id;
    //用时（ms）
    private Integer time;
    //所用空间
    private Integer memory;
    //结果(4:AC 0:待评测)
    private Integer result;
    //所属于竞赛组
    private Integer contest_id;
    //语言
    private Integer language;
    //是否有效
    private Integer valid;
    //题目在竞赛中属于顺序号
    private Integer num;
    //代码长度
    private Integer code_lenght;
    private String ip;
    //加入时间
    private Timestamp create_at;
    //判题时间
    private Timestamp judgetime;
    //AC则为0,错误一题即减一
    private Integer point;
}
