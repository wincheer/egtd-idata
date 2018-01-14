import Login from '@/view/Login.vue'
import Home from '@/view/Home.vue'
import Project from '@/view/project/Project.vue'
import Calendar from '@/view/gtd/Calendar.vue'
import ParamConfig from '@/view/setup/ParamConfig.vue'
import Resource from '@/view/setup/Resource.vue'
import Template from '@/view/setup/Template.vue'

import Foo from '@/view/Foo.vue'
import DOING from '@/view/Doing.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: '我的工作台',
        iconCls: 'el-icon-location',
        children: [
            { path: '/todo', component: DOING, name: '待办事项列表' ,iconCls:'el-icon-time'},
            { path: '/todo2', component: Calendar, name: '工作日历' ,iconCls:'el-icon-date'}
        ]
    },
    {
        path: '/',
        component: Home,
        name: '项目',
        iconCls: 'el-icon-view',
        children: [
            { path: '/project', component: DOING, name: '项目总览' ,iconCls: 'el-icon-search',},
            { path: '/hello42', component: Project, name: '项目详情' ,iconCls:'el-icon-tickets'}
        ]
    },
    {
        path: '/',
        component: Home,
        name: '系统配置',
        iconCls: 'el-icon-setting',
        children: [
            { path: '/resource', component: Resource, name: '资源管理',iconCls:'el-icon-goods' },
            { path: '/template', component: Template, name: '项目模板定义' ,iconCls:'el-icon-star-on'},
            { path: '/param-config', component: ParamConfig, name: '系统参数' ,iconCls:'el-icon-edit-outline'}
        ]
    }
];

export default routes;