<template>
    <div>
        <Card style="margin: 15px">
            <Collapse accordion @on-change="showContestInfo">
                <Panel v-for="(item) in contests" :name="item.contest.contest_id+''">
                    {{item.contest.title}}
                    <Tabs value="user" slot="content">
                        <TabPane label="User" name="user">
                            <Table :data="userDate" :columns="userCol"></Table>
                        </TabPane>
                        <TabPane label="Submit" name="submit">标签二的内容</TabPane>
                        <TabPane label="标签三" name="name3">标签三的内容</TabPane>
                    </Tabs>
                </Panel>
            </Collapse>
        
        </Card>
    </div>
</template>

<script>
    import Api from "../../components/api"

    export default {
        name: "ContestStatus",
        data() {
            return {
                contest: [],
                contests: [
                    /*{
                        "contest": {
                            access: false,
                            contest_id: 1,
                            describes: "Codeforces是一家为计算机编程爱好者提供在线评测系统的俄罗斯网站。该网站由萨拉托夫国立大学的\r\n一个团体创立并负责运营。参赛范围：各大学本科生",
                            end_at: 1569918812000,
                            max: 40,
                            now: 2,
                            organizer: "Codeforces",
                            privates: 1,
                            start_at: 1548428366000,
                            title: "Codeforces Round #534 (Div. 1)",
                            user_id: 12
                        },
                        "created_by": {
                            nickname: "admin",
                            user_id: 12,
                            username: "admin"
                        },
                    },*/
                ],
                page: 1,
                per_page: 10,

                userDate: [],
                userCol: [
                    {
                        title: "username",
                        render: (h, params) => {
                            return h('span', {}, params.row)
                        }
                    }
                ],

                submitDate: [],
                submitCol: [],

            }
        },
        methods: {
            getContests() {
                Api.getContestsByPagePer_PageAndCreator(this.page, this.per_page, this.$store.state.token).then(res => {
                    let result = res.data;
                    // console.log(result);
                    if (result.code === 200) {
                        this.contests = result.data.contests;
                    } else if (result.code === 401) {
                        this.$Message.error("签名过期,请重新登录!");
                        window.location.replace("/admin/login");
                    }
                }).catch(res => {
                    console.log(res);
                });
            },
            showContestInfo(contest_ids) {
                console.log(contest_ids[0]);
            }
        },
        mounted() {
            this.getContests();
        }
    }
</script>

<style scoped>

</style>