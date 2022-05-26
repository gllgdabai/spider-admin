import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'layout',
    component: Layout,
    redirect: "/user",
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import("@/views/UserView"),
      },
      {
        path: 'spider/spiderMission',
        name: 'spider/spiderMission',
        component: () => import("@/views/SpiderMissionView"),
      },
      {
        path: 'spider/spiderConfig',
        name: 'spider/spiderConfig',
        component: () => import("@/views/SpiderConfigView"),
      },
      {
        path: 'article',
        name: 'article',
        component: () => import("@/views/ArticleView"),
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import("@/views/LoginView")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/RegisterView")
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
