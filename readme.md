
# Online Judge

很早就想自己写一个 OJ，趁着毕设的机会，手刃了一个垃圾版 OJ，判题部分代码见 [JudgeClient](./judge-client)

## 简介

- 判题部分基于 Docker 和 linux C++
- Web 端前后端分离，采用 Springboot 微服务和 Vue
- 图表生成用的 eCharts
- 前端 UI 用的 iview

## 前端

### 安装的插件

- axios 代替 jquery 的轻量级 ajax 插件
- Simditor 富文本编辑器
- codemirror 代码编辑器
- echarts 生成图表插件
- tar-simditor-markdown

>遇到的坑 `Simditor` 插件是模块化的，npm 装好了一直无法使用，后来找到替代品 `tar-simditor`，如果需要支持 markdown，再装一个 `tar-simditor-markdown` 就行

### 流程

- 前后端分离

### 截图

>公告页面

![home](https://cdn.alomerry.com/projects/online-judge/images/preview/home.png)

>公告详情

![alert](https://cdn.alomerry.com/projects/online-judge/images/preview/alert.png)

>题目列表

![problem-lists](https://cdn.alomerry.com/projects/online-judge/images/preview/problem-lists.png)

>题目详情

![problem-detail-1](https://cdn.alomerry.com/projects/online-judge/images/preview/problem-detail-1.png)
![problem-detail-2](https://cdn.alomerry.com/projects/online-judge/images/preview/problem-detail-2.png)

>竞赛列表

![contest](https://cdn.alomerry.com/projects/online-judge/images/preview/contest.png)

>错误详细

![wrong-details](https://cdn.alomerry.com/projects/online-judge/images/preview/wrong-details.png)

>竞赛详情

![contest-detail](https://cdn.alomerry.com/projects/online-judge/images/preview/contest-detail.png)

>判题结果列表

![judge-result](https://cdn.alomerry.com/projects/online-judge/images/preview/judge-result.png)

>排名

![rank](https://cdn.alomerry.com/projects/online-judge/images/preview/rank.png)

>login

![login](https://cdn.alomerry.com/projects/online-judge/images/preview/login.png)

>register

![register](https://cdn.alomerry.com/projects/online-judge/images/preview/register.png)

## 后端

### 模块

- 公告
- 题目
- 用户代码
- 排名浏览
- 竞赛
- 用户

## 特点

- 权限鉴定 采用 JWT(Json Web Token) 验证，以 AOP 的方式执行验证
- 主从数据库 双 MySQL 可以减小单个数据库的压力
- 权限分类
  - `Topic_adder` 题目管理
  - `Contest_organizer` 竞赛组织
  - `Contest_participant` 竞赛参与者
  - `Code_viewer` 代码查看
  - `Manual_judger` 远程判题
  - `Announcement_manager`  公告管理
  - `User_manager` 用户管理
- 判题结果
  - `OJ_WT0` Pending：等待判题
  - `OJ_WT1` Waiting：排队中
  - `OJ_CI` compiling：编译中
  - `OJ_JI` Judging：运行中
  - `OJ_AC` Accepted：答案正确
  - `OJ_PE` Presentation Error：格式错误
  - `OJ_WA` Wrong Answer：答案不对
  - `OJ_TL` Time Limit Exceeded：运行超出时间限制
  - `OJ_ML` Merrory Limit Exceeded：超出内存限制
  - `OJ_OL` Output Limit Exceeded：输出超过限制
  - `OJ_RE` Runtime Error：运行时错误
  - `OJ_CE` Compile Error：编译错误
  - `OJ_CO` Competition Over：竞赛结束
  - `OJ_PA` Partial Accepted：部分正确
  - `OJ_SE` System Error：系统错误
