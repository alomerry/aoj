<template>
    <div>
        <Card style="margin: 0 150px 0 150px">
            <p class="card-title">ACM Ranklist</p>
            <div id="main" style="width: 1500px;height: 520px">
            
            </div>
        </Card>
    </div>
</template>

<script>
    let echarts = require('echarts/lib/echarts');
    // 引入柱状图
    require('echarts/lib/chart/bar');
    // 引入提示框和标题组件
    require('echarts/lib/component/tooltip');
    require('echarts/lib/component/title');
    require('echarts/theme/macarons');

    export default {
        name: "ACMRank",
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
                        source: [
                            ['product', 'AC', 'Total'],
                            ['Matcha Latte', 43.3, 85.8],
                            ['Milk Tea', 83.1, 73.4],
                            ['Cheese Cocoa', 86.4, 65.2],
                            ['Zie', 22.4, 13.9],
                            ['Walownie', 12.4, 53.9],
                            ['Brownie', 42.4, 53.9],
                            ['Walnut', 72.4, 93.9],
                        ]
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
        },
        mounted() {
            this.draw();
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