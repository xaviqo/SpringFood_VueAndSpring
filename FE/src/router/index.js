import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import MenuView from '../views/MenuView.vue'
import DashBoard from '../views/DashBoard.vue'
import MyProfile from '../views/MyProfile.vue'
import ProductsView from '../views/ProductsView.vue'
import GeneratorsView from '../views/GeneratorsView.vue'
import SfConfigure from '../views/SfConfigure.vue'
import LogIn from '../views/LogIn.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HomeView',
    component: HomeView
  },
  {
    path: '/admin/dashboard',
    name: 'DashBoard',
    component: DashBoard
  },
  {
    path: '/admin/stock',
    name: 'products',
    component: ProductsView
  },
  {
    path: '/admin/generate',
    name: 'generator',
    component: GeneratorsView
  },
  {
    path: '/login',
    name: 'login',
    component: LogIn
  },
  {
    path: '/menu',
    name: 'menu',
    component: MenuView
  },
  {
    path: '/client',
    name: 'myprofile',
    component: MyProfile
  },
  {
    path: '/admin/config',
    name: 'configure',
    component: SfConfigure
  },
  {
    path: '/admin/deliveries',
    name: 'deliveries',
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
