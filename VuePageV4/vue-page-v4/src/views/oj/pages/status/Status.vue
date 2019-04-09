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
            }
        }
    }
</script>

<style scoped>

</style>
