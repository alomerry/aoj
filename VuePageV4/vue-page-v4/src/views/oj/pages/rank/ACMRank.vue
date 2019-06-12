<template>
    <div>
        <Card style="margin: 0 150px 0 150px">
            <p class="card-title">ACM Ranklist</p>
            <div id="main" style="width: 1500px;height: 520px">

            </div>
            <Table :columns="col" :data="percentRank" stripe :loading="false"></Table>
        </Card>
        <Page style="margin-top: 10px;float: right;margin-right: 300px" :page-size="per_page" :total="total"
              @on-page-size-change="changePageSize" @on-change="changePage"></Page>
    </div>
</template>

<script>

    import Api from "../../components/api"

    let echarts = require('echarts/lib/echarts');
    // 引入柱状图
    require('echarts/lib/chart/bar');
    // 引入提示框和标题组件
    require('echarts/lib/component/tooltip');
    require('echarts/lib/component/title');
    require('echarts/theme/macarons');

    export default {
        name: "ACMRank",
        data() {
            return {
                totalRank: [],
                percentRank: [],

                total: 10,
                per_page: 10,
                page: 1,

                image_source: [],

                col: [
                    {
                        title: "NickName",
                        key: 'nickname',
                    },
                    {
                        title: "solved",
                        sortable:true,
                        key: 'solved',
                    },
                    {
                        title: "submit",
                        sortable:true,
                        key: 'submit',
                    },
                    {
                        title: "percent",
                        sortable:true,
                        render: (h, params) => {
                            return h('span', {},((params.row.solved / (params.row.submit === 0 ? 1 : params.row.submit)) * 100).toFixed(2)+"%")
                        }
                    }
                ],
            }
        },
        methods: {
            draw() {
                let option = {
                    legend: {
                        data: ['AC', 'Total'],
                    },
                    tooltip: {
                        trigger: "axis",
                    },
                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    toolbox: {
                        show: true,//是否显示工具栏组件。

                        feature: {
                            dataView: {//数据视图工具，可以展现当前图表所用的数据，编辑后可以动态更新
                                show: true,
                                readOnly: true
                            },
                            magicType: {//动态类型切换
                                show: true,
                                type: ['line', 'bar', 'stack']
                            },
                            saveAsImage: {
                                show: true,
                                type: 'png'
                            }
                        },
                        right: '10%'
                    },
                    calculable: true,
                    dataset: {
                        source: this.image_source,
                        /*[
                            ['User', 'AC', 'Total'],
                            ['Matcha Latte', 43.3, 85.8],
                            ['Milk Tea', 83.1, 73.4],
                            ['Cheese Cocoa', 86.4, 65.2],
                            ['Zie', 22.4, 13.9],
                            ['Walownie', 12.4, 53.9],
                            ['Brownie', 42.4, 53.9],
                            ['Walnut', 72.4, 93.9],
                        ]*/
                    },
                    xAxis: [
                        {
                            type: 'category',
                            axisLabel: {
                                // interval: 0,
                                showMinLabel: true,
                                showMaxLabel: true,
                                align: 'center',
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    // Declare several bar series, each will be mapped
                    // to a column of dataset.source by default.
                    series: [
                        {
                            type: 'bar',
                            markPoint: {
                                data: [
                                    {type: 'max', name: '最大值'},
                                    // {type: 'min', name: '最小值'}
                                ]
                            },
                        },
                        {
                            type: 'bar',
                            markPoint: {
                                data: [
                                    {type: 'max', name: '最大值'},
                                    // {type: 'min', name: '最小值'}
                                ]
                            },
                        },

                    ]
                };
                let myChart = echarts.init(document.getElementById('main'));// 绘制图表
                myChart.setOption(option);
            },

            changePageSize(pageSize) {
                this.per_page = pageSize;
                this.getRank();
            },
            //模态框页码发生变化
            changePage(page) {
                this.page = page;
                this.getRank();
            },

            getRank() {
                Api.getRank(this.page, this.per_page).then(res => {
                    if (res.data.code == 200) {
                        console.log(res.data);
                        this.totalRank = res.data.data.totalRank;
                        this.percentRank = res.data.data.percentRank;
                        this.total = res.data.data.total;
                        this.makeSource();

                        this.draw();
                    }
                }).catch(res => {
                    console.log(res);
                })
            },
            makeSource() {
                this.image_source = [];
                this.image_source.push(['User', 'AC', 'Total']);
                var that = this;
                this.totalRank.forEach(function (cur, i) {
                    let item = [
                        cur.username,
                        cur.solved,
                        cur.submit,
                    ]
                    that.image_source.push(item);
                });

            }
        },
        mounted() {
            // this.draw();
            this.getRank();
        }
    }
</script>

<style scoped>
    p.card-title {
        text-align: left;
        font-size: 20px;
        margin: 0 0 10px 20px;
        height: 35px;
    }
</style>