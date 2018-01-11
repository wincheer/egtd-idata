import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

/**
 * read:store.state ，this.$store.state.count;
 * write:store.commit('add'),this.$store.commit('add',123);
 */

//定义状态
const state = {
    loginUser: null
};
//定义操作
const mutations = {
    setLoginUser(state, employee) {
        state.loginUser = employee;
    },
    removeLoginUser(state) {
        state.loginUser = null;
    }
};

export default new Vuex.Store({
    state,
    mutations
});