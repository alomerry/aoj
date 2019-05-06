<template>
    <div>
        <Row>
            <Col span="20" style="margin-right: 10px;margin-left: 30px">
                <Card>
                    <p class="card-title">Contest</p>
                    <div slot="extra">
                        <Row>
                            <Col span="2">
                                <Tag type="dot" style="margin-left: 10px;" color="warning">标签四</Tag>
                            </Col>
                        </Row>
                    </div>
                    <Table :data="infoData" :columns="infoCol" stripe
                           :loading="infoTableLoading"></Table>
                </Card>
                <div style="margin:25px 10px 10px 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="totalPage" :current="page" :page-size="per_page" show-sizer show-elevator
                              @on-change=""></Page>
                    </div>
                </div>
            </Col>
            <Col span="3">
                <Card>
                    <p slot="title" style="font-size: 18px;">Tag</p>
                    <div>
                        <Tag :color="tag_color[(item.tag_id%tag_color.length)]" v-for="(item) in tag_list" @click.native="getProblemsByTag(item.tag_id)">{{item.tagname}}</Tag>
                    </div>
                    <Button long ghost type="info" @click.native="" style="margin-top: 15px">Pick One</Button>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    export default {
        name: "Contest",
        data() {
            return {
                contest_id: this.$route.params.contest_id,
                infoTableLoading: false,
                infoCol: [],
                infoData: [],
            }
        },
        methods: {
            getContest() {

            }
        }
    }
</script>

<style scoped>
    .card-content {
        margin: 10px 0 0 50px;
    }
    
    p.card-title {
        text-align: left;
        font-size: 20px;
        margin: 0 0 10px 20px;
        height: 35px;
    }
</style>