<template>
  <div class="card-content">
    <Row>
      <Col span="18" style="margin-right: 10px">
        <Card :bordered="false">
          <p class="card-title">Problem List</p>
          <Button slot="extra" style="margin-right: 60px" :loading="loading" @click.native="refresh">ReSet</Button>
          <Table :data="tableData1" :columns="tableColumns1" stripe></Table>
          <div style="margin:25px 10px 10px 10px;overflow: hidden">
            <div style="float: right;">
              <Page :total="totalPage" :current="current" :page-size="per_page" show-sizer show-elevator @on-change="changePage"></Page>
            </div>
          </div>
        </Card>
      
      </Col>
      <Col span="5">
        <Card :bordered="false">
          <p slot="title">Borderless card</p>
          <p>Content of card</p>
          <p>Content of card</p>
          <p>Content of card</p>
          <Button long ghost type="info" @click.native="" style="margin-top: 15px">Pick One</Button>
        </Card>
      </Col>
    </Row>
  
  </div>
</template>
<script>
  export default {
    name: 'ProblemList',
    data() {
      return {
        current: 1,
        totalPage: 1,
        per_page: 10,
        loading: false,
        tableData1: this.mockTableData1(),
        tableColumns1: [
          {
            title: 'title',
            key: 'title',
            width: 600,
            render: (h, params) => {
              return h('router-link', {
                style: {
                  fontSize: '15px'
                },
                attrs: {
                  to: "/problem/" + params.row.problem_id,
                },
                on: {
                  click: () => {
                  
                  }
                },
              }, params.row.title);
            }
          },
          //todo level
          /*{
          
          },*/
          {
            title: 'Total',
            key: 'submit',
          },
          {
            title: 'AC Rate',
            key: 'ac_rate',
            render: (h, params) => {
              return h('Poptip', {
                props: {
                  trigger: 'hover',
                  title: 'AC Rate',
                  placement: 'bottom'
                }
              }, [
                h('Tag', (params.row.accepted / (params.row.submit === 0 ? 1 : params.row.submit)) * 100 + "%"),
                h('div', {slot: 'content'}, [
                  h('p', {
                    style: {
                      fontSize: '15px'
                    }
                  }, "AC:" + params.row.accepted),
                  h('p', {
                    style: {
                      fontSize: '15px'
                    }
                  }, "Total:" + params.row.submit),
                ]),
              ]);
            }
          }
        ]
      }
    },
    methods: {
      mockTableData1() {
        let problems = [];
        var params = new URLSearchParams();
        params.append("page", this.current);
        params.append("per_page", this.per_page);
        params.append("resType", "simple");//需求结果类型
        this.$axios({
          url: "/api/api-oj/problems/defunct/1",
          method: 'get',
          data: params,
        }).then(res => {
          var result = res.data;
          console.log(result);
          if (result.code === 200) {
            for (let i = 0; i < result.data.total; i++) {
              var item = result.data.results[i];
              problems.push({
                accepted: item.accepted,
                click: item.click,
                create_time: item.create_time,
                defunct: item.defunct,
                description: item.description,
                hint: item.hint,
                input: item.input,
                memory_limit: item.memory_limit,
                output: item.output,
                problem_id: item.problem_id,
                sample_input: item.sample_input,
                sample_output: item.sample_output,
                source: item.source,
                submit: item.submit,
                time_limit: item.time_limit,
                title: item.title,
                user_id: item.user_id
              });
            }
          } else {
            console.log('Failed! ' + result.message);
          }
        }).catch(err => {
          console.log('An error has occurred! ' + err);
        });
        console.log(problems);
        return problems;
      },
      
      formatDate(date) {
        const y = date.getFullYear();
        let m = date.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        let d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        return y + '-' + m + '-' + d;
      },
      
      changePage() {
        // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server
        this.tableData1 = this.mockTableData1();
      }
    },
    created() {
      this.current = 1;
      //ajax
      this.totalPage = 1;
    }
  }
</script>
<style>
  .card-content {
    margin: 20px 0 0 50px;
  }
  
  p.card-title {
    text-align: left;
    font-size: 30px;
    margin: 0 0 25px 20px;
    height: 35px;
  }
</style>

