<template>
    <div>
        <Card style="margin: 15px">
        </Card>
        <div v-if="location_flag">
            <Card style="margin: 15px">
                <Table :columns="contest_col" :data="contest_data" :loading="contest_table_loading"></Table>
            </Card>
        </div>
        <div v-else>
            <Card style="margin: 15px">
                <Table :columns="apply_col"></Table>
            </Card>
        </div>
    
    </div>
</template>

<script>
    import Api from "../../components/api"

    export default {
        name: "ContestApply",
        data() {
            return {
                location_flag: true,//true:contest  false:apply
                contest_table_loading: false,//竞赛表加载标记
                contest_data: [
                    {
                        "access": true,
                        "contest_id": 18,
                        "describes": "<p>为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过 1200 余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。</p>",
                        "end_at": 1558627200000,
                        "max": 2000,
                        "now": 0,
                        "organizer": "教育部高等学校计算机科学与技术教学指导委员会",
                        "privates": 1,
                        "start_at": 1557417600000,
                        "title": "蓝桥杯",
                        "user_id": 12
                    },
                ],
                contest_col: [
                    {
                        title: "Contest Id",
                        align: "center",
                        key: "contest_id",
                    },
                    {
                        title: "Contest Name",
                        align: "center",
                        key: "title",
                    },
                    {
                        title: "Number",
                        align: "center",
                        key: "number",
                        render: (h, params) => {
                            return h("Progress", {
                                props: {
                                    status: "active",
                                    "hide-info": false,
                                    "success-percent": 0,
                                    percent: (params.row.now / params.row.max),
                                }
                            })
                        }
                    },
                    {
                        title: "View",
                        align: "center",
                        render: (h, params) => {
                            return h("Badge", {
                                props: {
                                    count: 100,
                                    type: "info",
                                    "overflow-count": 999,
                                    offset: [10, 0],
                                }
                            }, [
                                h("Button", {
                                    props: {
                                        size: "large"
                                    }
                                }, "View"),
                            ])
                        }
                    },

                ],
                apply_col: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center',
                    },
                    {
                        title: "用户"
                    },
                    {
                        title: "操作"
                    }
                ],

            }
        },
        methods: {
            getContests() {
                this.contest_table_loading = true;
                Api.getContestByCreator(this.$store.state.token).then(res => {
                    let result = res.data;
                    if (result.code === 200) {
                        this.contest_data = result.data.contests;
                    } else {
                        this.$Message.error(result.message);
                    }
                    this.contest_table_loading = false;
                }).catch(res => {
                    console.log(res);
                    this.contest_table_loading = false;
                });
            },
            getApplyNums(){
            
            },
        },
        mounted() {
            this.getContests();
        },
    }
</script>

<style scoped>

</style>