<template>
    <div class="card-content">
        <!--<transition name="fade" mode="out-in">-->
        <div v-if="!show_detail_flag" key="first">
            <Card style="margin: 15px">
                <div slot="title" style="height: 30px;padding-top: 5px;text-align: left;padding-left: 30px">
                    <span style="font-size: 25px;font-weight: 400;">Contests</span>
                </div>
                <div slot="extra">
                    <Button icon="md-refresh" style="float: right;margin-right: 20px" @click.native="getContests" :loading="loading_flag">Refresh</Button>
                    <br><br>
                </div>
                <Row v-for="(item,index) in contests">
                    <div style="padding-bottom: 60px;margin-bottom: 20px">
                        <Col span="12" style="float: left;text-align: left;padding-left: 90px;">
                            <router-link tag="a" :to="'/contest/'+item.contest_id" v-text="item.title" style="font-size: 21px;"></router-link>
                            <div style="margin-top: 10px">
                                <Icon size="18" color="blue" type="ios-calendar-outline"/>
                                <Time :time="item.start_at" type="datetime" style="margin-right: 10px"/>
                                <Icon size="18" color="blue" type="ios-timer-outline"/>
                                <Time :time="item.end_at" type="relative"/>
                            </div>
                        </Col>
                        <Col span="12" style="float: right;margin-top: 10px">
                            <Tag type="dot" :color="isStarted(item.start_at,item.end_at) == 0?'primary':
                                    isStarted(item.start_at,item.end_at)==1?'success':'error'">
                                {{isStarted(item.start_at,item.end_at) == 0?"Unstarted":
                                isStarted(item.start_at,item.end_at)==1?"Underway":"Finished"}}
                            </Tag>
                        </Col>
                    </div>
                    <Divider/>
                </Row>
                <Page style="float: right" :total="total" show-sizer :page-size="per_page"
                      @on-change="changePage"
                      @on-page-size-change="changePageSize"/>
                <br><br>
            </Card>
        </div>
        <!--<div v-else key="second">
        
        </div>
    </transition>-->
    </div>
</template>

<script>
    import Api from '../../components/api';

    export default {
        name: "ContestList",
        data() {
            return {
                show_detail_flag: false,

                contests: null,
                page: 1,
                per_page: 10,
                total: 1,
                loading_flag: false,
            }
        },
        methods: {
            getContests() {
                this.loading_flag = true;
                Api.findContestByPage(this.page, this.per_page).then(res => {
                    let result = res.data;
                    if (result.code == 200) {
                        this.contests = result.data.contests;
                        this.total = result.data.total;
                    } else {
                        this.$Message.error(result.message);
                    }
                    this.loading_flag = false;
                }).catch(res => {
                    console.log(res);
                    this.loading_flag = false;
                })
            },
            isStarted(start, end) {
                let now = new Date().getTime();
                if (now >= start && now <= end) {
                    return 1;
                } else if (now < start) {
                    return 0;
                } else {
                    return 2;
                }
            },
            //修改页码
            changePage(page) {
                this.page = page;
                this.getContests();
            },
            //修改每页数量
            changePageSize(pageSize) {
                this.per_page = pageSize;
                this.getContests();
            }
        },
        mounted() {
            this.getContests();
        }
    }
</script>

<style scoped>
    .card-content {
        margin: 10px 20px 0 20px;
        padding-left: 30px;
        padding-right: 30px;
    }
    
    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s;
    }
    
    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
    {
        opacity: 0;
    }
</style>