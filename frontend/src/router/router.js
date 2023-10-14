import Vue from 'vue/dist/vue.js'
import Router from 'vue-router'//使用 路由

Vue.use(Router);

const routes = [
    /*       index      */
    {
        path: '/status',
        name: 'status',
        meta: {title: 'Status'},
        component: resolve => require(['../views/oj/pages/status/Status.vue'], resolve)
    },
    {
        path: '/problems',
        meta: {title: 'Problems'},
        name: 'problems',
        component: resolve => require(['../views/oj/pages/problem/ProblemList.vue'], resolve)
    },
    {
        path: '/home',
        name: 'home',
        meta: {title: 'Home'},
        component: resolve => require(['../views/oj/pages/Home.vue'], resolve)
    },
    {
        path: '/problem/:problem_id',
        meta: {title: 'Problem'},
        name: 'problem',
        component: resolve => require(['../views/oj/pages/problem/Problem.vue'], resolve)
    },
    {
        path: '/',
        redirect: '/home'
    },
];

const router = new Router({
    mode: "history",
    routes,
});

router.beforeEach((to, from, next) => {
    if (to.meta.title) {
        document.title = to.meta.title;
    }
    next();
});

export default router;
