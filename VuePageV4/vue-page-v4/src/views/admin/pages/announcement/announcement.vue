<template>
    <div>
        <Modal v-model="modal_falg" width="900">
            <div slot="header">
                <span style="font-size: 20px;font-weight: 400;">Create Announcement</span>
            </div>
            <div>
                <div class="anno-item">
                    <span class="item-require-red">*</span> Title
                    <Input size="large" style="margin-top: 15px"></Input>
                </div>
                <div class="anno-item">
                    <div style="margin-bottom: 15px">
                        <span class="item-require-red">*</span> Content
                    </div>
                    <Simditor>
                    </Simditor>
                </div>
                <div class="anno-item">
                    <span style="margin-right: 10px">
                        Status
                    </span>
                    <i-switch>
                        <span slot="open">️</span>
                        <span slot="close"></span>
                    </i-switch>
                </div>
            </div>
            <div slot="footer" style="height: 35px;">
                <Button @click="modal_falg = false">Close</Button>
                <Button type="primary" @click="createAnno">Save</Button>
            </div>
        </Modal>
        <Card style="margin: 15px">
            <div slot="title" style="height: 30px;padding-top: 10px">
                <span style="font-size: 25px;font-weight: 400;">Problems</span>
            </div>
            <div slot="extra">
                <Button>Refresh</Button>
            </div>
            <Table :columns="anno_column" :data="anno_data" :loading="anno_loading_flag"></Table>
            <Button @click="modal_falg = true" style="margin-top: 15px" type="info">Create</Button>
            <Page :current="page" :page-size="per_page" size="small" style="float: right;margin-top: 20px"/>
        </Card>
    </div>
</template>

<script>
    import Simditor from '../../components/Simditor'
    import Api from "../../components/api"

    export default {
        name: "announcement",
        data() {
            return {
                page: 1,
                per_page: 10,
                modal_falg: false,
                anno_loading_flag: false,
                anno_column: [
                    {
                        title: "Title",
                        key: 'news.title',
                        align: 'center',
                        render: (h, params) => {
                            return h("span", {}, params.row.news.title);
                        }
                    },
                    {
                        title: "Create Time",
                        key: "news.create_at",
                        render: (h, params) => {
                            return h('Time', {
                                props: {
                                    time: params.row.news.create_at,
                                    type: 'datetime'
                                },
                            })
                        }
                    },
                    {
                        title: "Last Update Time",
                        key: "news.update_time",
                        render: (h, params) => {
                            return h('Time', {
                                props: {
                                    time: params.row.news.update_time,
                                    type: 'datetime'
                                },
                            })
                        }
                    },
                    {
                        title: "Anthor",
                        key: "user.username",
                        align: 'center',
                        render: (h, params) => {
                            return h("span", {}, params.row.user.username);
                        }
                    },
                    {
                        title: "Visable",
                        key: "news.defunct",
                        render: (h, params) => {
                            return h("i-switch", {
                                props: {
                                    "true-value": params.row.news.defunct,
                                }
                            });
                        }
                    },
                    {
                        title: "Option",
                        align: 'center',
                        render: (h, params) => {
                            return h("div", {}, [
                                h("Tooltip", {
                                    props: {
                                        placement: "top",
                                        theme: "light",
                                        content: "编辑"
                                    }
                                }, [h("Button", {
                                    props: {
                                        type: 'default',
                                        size: 'large',
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                        }
                                    }
                                }, [
                                    h('Icon', {
                                        props: {
                                            type: "ios-create",
                                            size: 17,
                                            // color: "#e1a331"
                                        }
                                    }),
                                ])]),
                                h("Tooltip", {
                                    props: {
                                        placement: "top",
                                        theme: "light",
                                        content: "删除"
                                    }
                                }, [h("Button", {
                                    props: {
                                        type: 'default',
                                        size: 'large',
                                    },
                                    on: {
                                        click: () => {
                                        }
                                    }
                                }, [h('Icon', {
                                        props: {
                                            type: "md-trash",
                                            size: 17,
                                            color: "#e1a331"
                                        }
                                    }
                                )])]),
                            ])
                        }
                    }
                ],
                anno_data: null,
                formAnno: {
                    news_id: null,//新闻Id
                    user_id: null,//用户Id
                    title: null,//新闻标题
                    content: null,//新闻内容
                    update_time: null,//更新时间
                    create_at: null,//创造时间
                    contest_id: null,//所属竞赛号
                    defunct: null,//公开状态
                }
            }
        },
        components: {
            Simditor,
        },
        mounted() {

        },
        methods: {
            createAnno() {
                modal_falg = false;
                
            },
            getNews() {
                anno_loading_flag = true;
                Api.findNews(this.page, this.per_page, this.$store.state.token).then(res => {
                    this.anno_data = [
                        {
                            news: {
                                news_id: 0,//新闻Id
                                title: "dsafdas",//新闻标题
                                content: "asdfsd",//新闻内容
                                update_time: "2016-10-03 07:08:16",//更新时间
                                create_at: "2016-10-03 07:08:16",//创造时间
                                contest_id: 123,//所属竞赛号
                                defunct: 0,//公开状态
                            },
                            user: {
                                username: "ad",
                                nickname: "ad",
                            }
                        }
                    ];
                    this.anno_loading_flag = false;
                }).catch(res => {
                    this.anno_loading_flag = false;
                });
            }
        }
    }
</script>

<style scoped>
    span.item-require-red {
        color: red;
    }
    
    div.anno-item {
        margin: 10px 0 20px 0;
        font-size: 15px;
        padding: 5px;
    }
</style>