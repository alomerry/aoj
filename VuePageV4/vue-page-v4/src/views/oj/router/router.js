import Vue from 'vue/dist/vue.js'
import Router from 'vue-router'//使用 路由
import iView from 'iview';

Vue.use(Router);
Vue.use(iView);

const routes = [
    /*       index      */
    {
        path: '/status',
        name: 'status',
        meta: {title: 'Status'},
        component: resolve => require(['../pages/status/Status'], resolve)
    },
    {
        path: '/problems',
        meta: {title: 'Problems'},
        name: 'problems',
        component: resolve => require(['../pages/problem/ProblemList'], resolve)
    },
    {
        path: '/home',
        name: 'home',
        meta: {title: 'Home'},
        component: resolve => require(['../pages/Home'], resolve)
    },
    {
        path: '/problem/:problem_id',
        meta: {title: 'Problem'},
        name: 'problem',
        component: resolve => require(['../pages/problem/Problem'], resolve)
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
