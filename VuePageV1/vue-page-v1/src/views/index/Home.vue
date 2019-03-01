<template>
  <div class="card-content">
    <Card :bordered="false">
      <p class="card-title" v-text="header_info.title"></p>
      <Button slot="extra" style="margin-right: 60px" :loading="loading" @click.native="refresh">{{header_info.button_info}}</Button>
      <div v-if="news_show === 0">
        <Row v-if="data == null">
          <p>No Announcements</p>
        </Row>
        <div v-else>
          <Row v-for="(value,index) in data" >
            <Col span="16">
              <div class="news-item item-name"><a @click="getNewsInfo(value.id)">{{value.name}}</a></div>
            </Col>
            <Col span="5">
              <div class="news-item item-time">{{value.date}}</div>
            </Col>
            <Col span="3">
              <div class="news-item item-creator">{{value.creator.username}}</div>
            </Col>
            <Divider/>
          </Row>
        </div>
        <div style="margin: 10px;overflow: hidden">
          <div style="float: right;">
            <Page :total="totalPage" :current="current" :page-size="per_page" show-sizer show-elevator @on-change="changePage"></Page>
          </div>
        </div>
      </div>
      <div v-else>
        <div id="news-item-content" v-text="news_item.content"></div>
      </div>
    </Card>
  </div>
</template>
<script>
  export default {
    name: 'Home',
    data() {
      return {
        current: 1,
        totalPage: 1,
        per_page: 10,
        loading: false,
        news_show: 0,//显示新闻列表
        header_info: {
          button_info: '',
          title: '',
        },
        data: [
          {
            id: 12,
            name: 'New York No. 1 Lake Park New York',
            date: '2016-10-03',
            creator: {
              username: 'root'
            },
            content: '<p>因为目前只有一台机器，而且机器负载一直较高，如举办比赛可能会遇到一些问题，所以请至少提前3天提出申请。</p><p>请使用密码 <code>3US7JidxfIYWi1k5</code> 填写<a href=\\"http://virusdefender-blog.mikecrm.com/NnVdz5V\\" target=\\"_blank\\">表单</a></p>'
          },
          {
            id: '10',
            name: 'London No. 1 Lake Park',
            date: '2016-10-01',
            creator: {
              username: 'root'
            },
            content: '<p>老版本的用户数据是完整迁移过来的，老版本的地址目前是 <a href=\\"http://old.qduoj.com/\\" target=\\"_blank\\">http://old.qduoj.com/</a> ssl 证书正在申请</p><p>有任何问题请加 QQ群 496710125</p>'
          },
          {
            id: '1',
            name: 'Sydney No. 1 Lake Park',
            date: '2016-10-02',
            creator: {
              username: 'root'
            },
            content: '<p>经过三个月的紧张开发，我们终于将迎来2.0版，新版我们带来了诸多新特性：</p><ul><li>基于 Docker，真正一键部署</li><li>前后端分离，模块化编程，微服务</li><li>ACM/OI 两种比赛模式、实时/非实时评判 任意选择</li><li>丰富的可视化图表，一图胜千言</li><li>支持 Template Problem，可以添加函数题甚至填空题</li><li>更细致的权限划分，超级管理员和普通管理员各司其职</li><li>多语言支持：<code>C</code>, <code>C++</code>, <code>Java</code>, <code>Python2</code>, <code>Python3</code>，题目可以选择使用的语言</li><li>Markdown & MathJax 支持</li><li>比赛用户IP限制 (CIDR ranges)</li></ul><p>敬请期待</p>'
          },
          {
            id: '13',
            name: 'Ottawa No. 2 Lake Park',
            date: '2016-10-04',
            creator: {
              username: 'root'
            },
            content: '<p>==</p>'
          }],
        news_item: {
          name: '',
          content: ''
        },
      }
    },
    methods: {
      refresh() {
        if (this.news_show === 1) {
          this.news_show = 0;
        } else {
          this.loading = true;
        }
        //查询data
        /*var params = new URLSearchParams();
        params.set('limit', 10);
        params.set('offset', 0);
        this.$axios({
          url: '/api/api-oj/news',
          method: 'post',
          data: params
        }).then(res => {
        
        }).catch(err => {
        
        })
        this.loading = false;
        */
        var that = this;
        setTimeout(function () {
          that.loading = false;
        }, 3000);
        
        this.header_info.button_info = this.news_show === 0 ? 'Refresh' : 'Back';
        this.header_info.title = this.news_show === 0 ? 'Annocement' : '';
      },
      getNewsInfo(id) {
        var that = this;
        for (var item of this.data) {
          if (item.id === id) {
            that.news_item = item;
            break;
          }
        }
        this.news_show = 1;
        this.header_info.button_info = this.news_show === 0 ? 'Refresh' : 'Back';
        this.header_info.title = this.news_show === 0 ? 'Annocement' : this.news_item.name;
      },
      changePage() {
        // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server
        this.tableData1 = this.mockTableData1();
      }
    },
    created() {
      //TODO
      this.refresh();
    }
  }
</script>
<style>
  .card-content {
    margin: 20px 80px 0 80px;
    padding-left: 30px;
    padding-right: 30px;
  }
  
  p.card-title {
    text-align: left;
    font-size: 30px;
    margin: 0 0 25px 20px;
    height: 35px;
  }
  
  .news-item {
    font-size: 15px;
    font-weight: 700;
  }
  
  div.item-name {
    text-align: left;
    margin-left: 20px;
  }
</style>
