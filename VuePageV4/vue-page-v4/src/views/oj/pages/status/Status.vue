<template>
    <div style="margin: 0 15px 0 15px">
        <Card>
            <div slot="title">The standard card</div>
            <Table :columns="statusColumns"></Table>
            <div style="float: right;margin: 5px;">
                <Page :total="totalPage" :page-size="per_page" :current="current" show-sizer/>
            </div>
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
                statusColumns: [],
            }
        }
    }
</script>

<style scoped>

</style>
