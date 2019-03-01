<template>
    <Row>
        <div>
            <Col span="6" style="margin:15px 0 0 15px">
                <Card>
                    <div slot="title">
                        <Row>
                            <Col span="10"><img src="../../../../public/logo.png"
                                                style="display: inline-block;height: auto;max-width: 100%;"/>
                            </Col>
                            <Col span="10" style="margin-left: 20px">
                                <p></p>
                                <h1>root</h1>
                                <p></p>
                                <h4>Admin level</h4>
                            </Col>
                        </Row>
                    </div>
                    <h3 style="margin: 10px 0 10px 0">
                        Last Login
                    </h3>

                    <Row>
                        <div v-for="(item,key) in lastLoginInfo" style="margin: 5px 0 5px 0">
                            <Col span="5" style="text-align: right"><span v-text="item.title"></span></Col>
                            <Col span="1" style="text-align: center;">:</Col>
                            <Col span="10" style="text-align: left;margin-left: 10px"><span
                                    v-text="item.value"></span></Col>
                            <br>
                        </div>
                    </Row>
                </Card>
                <Card style="margin: 15px 0 0 0">
                    <div slot="title">
                        <p style="font-weight: 300;font-size: 20px ">System Overview</p>
                    </div>
                    <p>Content of card</p>
                    <p>Content of card</p>
                    <p>Content of card</p>
                </Card>
            </Col>
        </div>
        <div>
            <Col span="16" style="margin:15px 0 0 15px">
                <Row>
                    <Col span="8" class="three_card">
                        <Card style="padding: 0">
                            <div class="total-div-bg" style="background-color:rgba(101,101,101,0.65)">
                                <img src="../components/user.png"
                                     class="total-show">
                            </div>
                            <span class="total-span-show span-left">
                                            <p class="number" style="color: rgba(101,101,101,0.65);">15</p>
                                            <p class="word">Total Users</p>
                                        </span>

                        </Card>
                    </Col>
                    <Col span="8" class="three_card">
                        <Card style="padding: 0">
                            <div class="total-div-bg" style="background-color:rgb(16,217,65)">
                                <img src="../components/submit.png"
                                     class="total-show">
                            </div>
                            <span class="total-span-show span-left">
                                            <p class="number" style="color: rgb(16,217,65);">15</p>
                                            <p class="word">Today Submissions</p>
                                        </span>
                        </Card>
                    </Col>
                    <Col span="7" class="three_card">
                        <Card style="padding: 0">
                            <div class="total-div-bg" style="background-color:rgb(0,165,255)">
                                <img src="../components/contest.png"
                                     class="total-show">
                            </div>
                            <span class="total-span-show span-right">
                                            <p class="number" style="color: rgb(0,165,255);">15</p>
                                            <p class="word">Recent Contests</p>
                                        </span>
                        </Card>
                    </Col>
                </Row>
                <Card>
                    <div slot="title">
                        <p style="font-weight: 300;font-size: 25px;height: 25px">Release Notes ds&nbsp;<Icon type="md-help-circle" /></p>

                    </div>
                    <Collapse simple>
                        <Panel name="1">
                            史蒂夫·乔布斯
                            <p slot="content">史蒂夫·乔布斯（Steve Jobs），1955年2月24日生于美国加利福尼亚州旧金山，美国发明家、企业家、美国苹果公司联合创办人。</p>
                        </Panel>
                        <Panel name="2">
                            斯蒂夫·盖瑞·沃兹尼亚克
                            <p slot="content">斯蒂夫·盖瑞·沃兹尼亚克（Stephen Gary Wozniak），美国电脑工程师，曾与史蒂夫·乔布斯合伙创立苹果电脑（今之苹果公司）。斯蒂夫·盖瑞·沃兹尼亚克曾就读于美国科罗拉多大学，后转学入美国著名高等学府加州大学伯克利分校（UC Berkeley）并获得电机工程及计算机（EECS）本科学位（1987年）。</p>
                        </Panel>
                        <Panel name="3">
                            乔纳森·伊夫
                            <p slot="content">乔纳森·伊夫是一位工业设计师，现任Apple公司设计师兼资深副总裁，英国爵士。他曾参与设计了iPod，iMac，iPhone，iPad等众多苹果产品。除了乔布斯，他是对苹果那些著名的产品最有影响力的人。</p>
                        </Panel>
                    </Collapse>
                </Card>
                <Card style="margin: 15px 0 0 0">
                    <p slot="title">The standard card</p>
                    <p>Content of card</p>
                    <p>Content of card</p>
                    <p>Content of card</p>
                </Card>
            </Col>
        </div>
    </Row>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {
                lastLoginInfo: [
                    {
                        title: 'Time',
                        value: '2019-2-16 09:58:32',
                    },
                    {
                        title: 'IP',
                        value: '192.168.211.1',
                    },
                    {
                        title: 'OS',
                        value: 'OS X 10.12.6',
                    },
                    {
                        title: 'Browser',
                        value: 'chrome 72.0.3626',
                    }
                ],
            }
        },
    }
</script>

<style scoped>
    img.total-show {
        height: auto;
        max-width: 100%;
        margin: 15px 0 0 15px;
        padding: 0;
    }

    div.total-div-bg {
        border-radius: 5px;
        display: inline-block;
        width: 80px;
        height: 80px;
        margin: 0;
        padding: 0 0 0 0;
    }

    .three_card {
        margin-right: 15px;
        margin-bottom: 15px;
    }

    span.total-span-show {
        display: inline-block;
        text-align: center;
        margin: auto;
    }

    span.total-span-show.span-left {
        width: 240px;
    }

    span.total-span-show.span-right {
        width: 200px;
    }

    span.total-span-show > p.number {
        font-size: 25px;
        font-weight: 900;
    }

    span.total-span-show > p.word {
        font-weight: 300;
    }
</style>