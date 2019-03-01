import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

const router = new Router({
  mode: "history",
  routes: [
    {
      path: '/status',
      component: resolve => require(['../views/index/status/Status'], resolve)
    },
    {
      path: '/problems',
      component: resolve => require(['../views/index/problem/ProblemList'], resolve)
    },
    {
      path: '/home',
      meta: {title: 'Home'},
      component: resolve => require(['../views/index/Home'], resolve)
    },
    {
      path: '/problem/:problem_id',
      component: resolve => require(['../views/index/problem/Problem'], resolve)
    },
  ]
});

router.beforeEach((to, from, next) => {
  next();
});

export default router;

