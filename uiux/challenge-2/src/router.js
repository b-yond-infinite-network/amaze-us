import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/water',
      name: 'water',
      component: () => import(/* webpackChunkName: "water" */ './views/Water.vue')
    },
    {
      path: '/food',
      name: 'fodd',
      component: () => import(/* webpackChunkName: "food" */ './views/Food.vue')
    },
    {
      path: '/population',
      name: 'population',
      component: () => import(/* webpackChunkName: "population" */ './views/Population.vue')
    },
  ]
})
