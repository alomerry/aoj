<template>
    <div>
        <Card style="margin: 15px">
        </Card>
        <div v-if="location_flag">
            <Card style="margin: 15px">
                <Table :columns="contest_col" :data="contest_data"></Table>
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
                contest_data: [
                    {
                        "access": true,
                        "contest_id": 18,
                        "max": 100,
                        "now": 0,
                        "privates": 1,
                        "title": "蓝桥杯",
                    },
                    {
                        "access": true,
                        "contest_id": 18,
                        "max": 100,
                        "now": 0,
                        "privates": 1,
                        "title": "蓝桥杯",
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
                                    "success-percent": 30,
                                    percent: 60,
                                }
                            })
                        }
                    },
                    {
                        title: "View",
                        align: "center",
                        render: (h, params) => {
                            return h("Button", {
                                props: {}
                            }, "view")
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
        }
    }
</script>

<style scoped>

</style>