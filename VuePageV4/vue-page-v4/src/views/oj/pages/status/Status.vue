<template>
    <div style="margin: 0 15px 0 15px">
        <Card>
            <div slot="title">The standard card</div>
            <div style="margin-bottom: 2px;">
                <Table :columns="statusColumns"></Table>
            </div>
            <div style="float: right;margin: 5px;">
                <Page :total="totalPage" :page-size="per_page" :current="current" show-sizer/>
            </div>
            <p style="margin-top: 30px"></p>
        </Card>
    </div>
</template>

<script>
    export default {
        name: "status",
        data() {
            return {
                current: 1,
                totalPage: 1,
                per_page: 10,
                statusColumns: [
                    {
                        title: 'when',
                        key: 'when',
                        width: 600,
                        /*render: (h, params) => {
                            return h('span', {
                                style: {
                                    fontSize: '15px'
                                },
                            }, params.row.title);
                        }*/
                    },
                ],
                statusData: [
                    {
                        problem: {
                            problem_id: 7,
                            title: "方阵填数"
                        },
                        solution: {
                            code_lenght: 110,
                            contest_id: null,
                            create_at: 1554760701000,
                            ip: "127.0.0.1",
                            judgetime: null,
                            language: 2,
                            language: null,
                            num: 0,
                            problem_id: 7,
                            result: 0,
                            solution_id: 30,
                            time: null,
                            user_id: 10,
                            valid: 1
                        },
                        user: {
                            nickname: "就当一次路过丶",
                            user_id: 10,
                            username: "morizunzhu"
                        }
                    }
                ]
            }
        }
    }
</script>

<style scoped>

</style>
