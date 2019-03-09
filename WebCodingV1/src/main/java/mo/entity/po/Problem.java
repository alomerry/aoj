package mo.entity.po;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class Problem {
    //问题ID
    private Integer problem_id;
    //时间限制(ms)
    private Integer time_limit;
    //空间限制(MB)
    private Integer memory_limit;
    //提交次数
    private Integer submit;
    //解决次数
    private Integer accepted;
    //问题标题
    private String title;
    //输入描述
    private String input;
    //输出描述
    private String output;
    //输入
    private String sample_input;
    //输出
    private String sample_output;
    //问题来源
    private String source;
    //暗示
    private String hint;
    //点击量
    private Integer click;
    //问题描述
    private String description;
    //屏蔽-公开-部分公开-绝对私有0/1/2/3
    private char defunct;
    //创建时间
    private Timestamp created_at;
    //创建者
    private Integer create_by;
    //显示Id
    private Integer display_id;

}
