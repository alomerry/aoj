import Vue from 'vue/dist/vue.js'
import Router from 'vue-router'//使用 路由
import iView from 'iview';

Vue.use(Router);
Vue.use(iView);

const routes = [
    /*       index      */
    {
        path: '/about',
        name: 'judger',
        meta: {title: 'Judger'},
        component: resolve => require(['../pages/about/Judger.vue'], resolve)
    },
    {
        path: '/FAQ',
        name: 'FAQ',
        meta: {title: 'FAQ'},
        component: resolve => require(['../pages/about/FAQ.vue'], resolve)
    },
    {
        path: '/status',
        name: 'status',
        meta: {title: 'Status'},
        component: resolve => require(['../pages/status/Status.vue'], resolve)
    },
    {
        path: '/user-home',
        name: 'user-home',
        meta: {title: 'User-Home'},
        component: resolve => require(['../pages/user/UserHome.vue'], resolve)
    },
    {
        path: '/setting',
        name: 'user-setting',
        meta: {title: 'User-Setting'},
        component: resolve => require(['../pages/user/UserSetting.vue'], resolve)
    },
    {
        path: '/contests',
        name: 'contests',
        meta: {title: 'Contests'},
        component: resolve => require(['../pages/contest/ContestList.vue'], resolve)
    },
    {
        path: '/contest/:contest_id',
        name: 'Contest',
        meta: {title: 'Contest'},
        component: resolve => require(['../pages/contest/Contest.vue'], resolve)
    },
    {
        path: '/problems',
        meta: {title: 'Problems'},
        name: 'problems',
        component: resolve => require(['../pages/problem/ProblemList.vue'], resolve)
    },
    {
        path: '/home',
        name: 'home',
        meta: {title: 'Home'},
        component: resolve => require(['../pages/Home.vue'], resolve)
    },
    {
        path: '/contest/:contest_id/problem/:problem_id',
        meta: {title: 'Problem'},
        name: 'problem',
        component: resolve => require(['../pages/problem/Problem.vue'], resolve)
    },
    {
        path: '/problem/:problem_id',
        meta: {title: 'Problem'},
        name: 'problem',
        component: resolve => require(['../pages/problem/Problem.vue'], resolve)
    },
    {
        path: '/acm-rank',
        meta: {title: 'Acm-rank'},
        name: 'acm-rank',
        component: resolve => require(['../pages/rank/ACMRank.vue'], resolve)
    },
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/index',
        redirect: '/home'
    },
];

const router = new Router({
    mode: "history",
    routes,
});

router.beforeEach((to, from, next) => {
    // iView.LoadingBar.start();
    if (to.meta.title) {
        document.title = to.meta.title;
    }
    next();
});
router.afterEach(route => {
    // iView.LoadingBar.finish();
});


export default router;
