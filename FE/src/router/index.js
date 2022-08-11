import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import TeamView from '../views/TeamView.vue'
import DashBoard from '../views/DashBoard.vue'
import ProductsView from '../views/ProductsView.vue'
import GeneratorsView from '../views/GeneratorsView.vue'
import LogIn from '../views/LogIn.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HomeView',
    component: HomeView
  },
  {
    path: '/admin',
    name: 'DashBoard',
    component: DashBoard
  },
  {
    path: '/team',
    name: 'team',
    component: TeamView
  },
  {
    path: '/products',
    name: 'products',
    component: ProductsView
  },
  {
    path: '/generate',
    name: 'generator',
    component: GeneratorsView
  },
  {
    path: '/login',
    name: 'login',
    component: LogIn
  },
  {
    path: '*',
    name: 'error404',
    component: HomeView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
