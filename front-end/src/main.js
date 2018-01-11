import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import routes from '@/config/routes'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from '@/config/store'

Vue.use(VueRouter)
Vue.use(ElementUI)

const router = new VueRouter({
    mode: 'hash',
    base: '/',
    routes: routes
})

router.beforeEach((to, from, next) => {
    if (to.path == '/login') {
        store.commit('removeLoginUser');
        sessionStorage.removeItem('loginUser');
    }
    let loginUser = JSON.parse(sessionStorage.getItem('loginUser'));
    store.commit('setLoginUser',loginUser);
    //let loginUser = store.state.loginUser;
    if (!loginUser && to.path != '/login') {
        next({ path: '/login' });
    } else {
        next();
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
