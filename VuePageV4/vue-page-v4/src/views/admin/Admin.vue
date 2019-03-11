<template>
    <div id="app"><!--style="background-color: #edecec;height:900px;[htmlHeight,bgColor]"-->
        <div v-if="!loginFlag" class="admin-container" :style="htmlHeight"><!-- :style="htmlHeight" -->
            <div style="height:100%;width: 240px;position: fixed!important;">
                <MenuBar></MenuBar>
            </div>
            <Head></Head>
            <div style="margin-left: 240px">
                <transition name="traAni"
                            enter-active-class="animated fadeIn" appear>
                    <router-view></router-view>
                </transition>
            </div>
        </div>
        <div v-else :style="htmlHeight">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    import MenuBar from './components/MenuBar';
    import Head from './components/Head';

    export default {
        name: 'App',
        data() {
            return {
                htmlHeight: "height:" + window.innerHeight + "px;",
                loading: false,
                loginFlag: window.location.href.indexOf("admin/login") !== -1,
            }
        },
        components: {
            'MenuBar': MenuBar,
            'Head': Head,
        },
        computed: {},
        created() {
            /*加载动画*/
            /*let that = this;
            setTimeout(function () {
              that.loading = false;
            }, 1000);*/
        },
        methods: {},
        mounted() {
        }
    }
</script>

<style>
    @import "../../assets/animate.css";
    @import "../../assets/loading.css";

    ul {
        height: 100%;
    }

    .admin-container {
        background-color: #edecec;
    }
</style>