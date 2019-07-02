

# Online Judge Web端

很早就想自己写一个OJ，趁着毕设的机会，手刃了一个垃圾版OJ
+ 判题部分基于Docker 和 linux C++
- Web端前后端分离，采用Springboot微服务和Vue
+ 图表生成用的eCharts
+ 前端UI用的iview

## 前端
### 安装的插件
```
axios 代替jquery的轻量级ajax插件
Simditor 富文本编辑器
codemirror 代码编辑器
echarts 生成图表插件
tar-simditor-markdown
```
### 遇到的坑
- Simditor插件好像是模块化的，npm装好了以后我一直不知道怎么使用，后来找到一个替代品tar-simditor,如果需要支持markdown，再装一个tar
-simditor就行
### 流程
前后端分离的话基本都差不多吧，我也不知道企业里是啥样的，没上过班。。后端提供接口，然后用axios ajax访问。

### 截图

## 后端
### 使用的库
### 编码过程中踩的坑
````
1.测试题目删除
2.题目删除时添加判断，是否题目在某场竞赛中
3.测试公告删除 
5.fastJson不建立新对象可能会引起的循环引用
6.前台竞赛列表，用户可以查询到公开竞赛和自己参加的隐私竞赛
````

## 特点

#### 5.3 linux run sh
````
docker build -t mo/judge:v1.8 .
docker run -it  -v /mnt/hgfs/webapp/problem_cases:/oj-home/problem_cases:ro -d mo/judge:v1.8
docker ps
docker rm ...
docker images 
docker rmi ...
docker exec -it ... /bin/bash 
````
#### 5.4 
```
当前版本只判断题目正误，不判分数，例如一个题目中有是个测试用例，正确一个的一部分分数
linux部分：
1.
    * 判题之前计算.in文件数量
    * 计算用户正确的题目数计算题目分数
2.
    * 为每个题目创建分值文件

```
#### 待实现
```
- 分布式文件系统FastDFS
- 添加更多的语言支持 JAVA等
```
好像想写的暂时就这么多，想到了再改吧。。