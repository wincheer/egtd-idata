import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import routes from '@/config/routes'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VCharts from 'v-charts'
import store from '@/config/store'

Vue.use(VueRouter)
Vue.use(ElementUI)
Vue.use(VCharts)

const router = new VueRouter({
    mode: 'hash',
    base: '/',
    routes: routes
})

// global event bus 
const EventBus = new Vue()
Object.defineProperties(Vue.prototype, {
  $bus: {
    get: function () {
      return EventBus
    }
  }
})
// this.$bus.$emit('my-event', { ... pass some event data ... }) --- 发布
// this.$bus.$on('my-event', this.handleMyEvent) --- 监听
// this.$bus.$off('my-event', this.handleMyEvent) --- 监听结束，在beforeDestroy()或destroyed()中释放订阅

router.beforeEach((to, from, next) => {
    if (to.path == '/login') {
        sessionStorage.removeItem('loginUser');
        sessionStorage.removeItem('myRoles');
        store.commit('clearStore');
    }
    let loginUser = JSON.parse(sessionStorage.getItem('loginUser'));
    let myRoles = JSON.parse(sessionStorage.getItem('myRoles'));
   
    if (!loginUser && to.path != '/login') {
        next({ path: '/login' })
    } else {
        store.commit("setLoginUser", loginUser);
        store.commit("setMyRoles", myRoles);
        next()
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
