import Login from '@/view/Login.vue'
import Home from '@/view/Home.vue'
import Project from '@/view/project/Project.vue'
import ProjectInit from '@/view/project/ProjectInit.vue'
import Calendar from '@/view/gtd/Calendar.vue'
import ParamConfig from '@/view/setup/ParamConfig.vue'
import Resource from '@/view/setup/Resource.vue'
import Template from '@/view/setup/Template.vue'
import DashBoard from '@/view/gtd/DashBoard.vue'

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
        blackList:[],
        children: [
            { path: '/dashboard', component: DashBoard, name: '工作台' ,iconCls: 'el-icon-picture-outline',blackList:[]},
            { path: '/calendar', component: Calendar, name: '工作日历' ,iconCls:'el-icon-date',blackList:[]},
           
        ]
    },
    {
        path: '/',
        component: Home,
        name: '项目',
        iconCls: 'el-icon-view',
        blackList:[],
        children: [
            { path: '/project-init', component: ProjectInit, name: '项目管理' ,iconCls:'el-icon-edit-outline',blackList:["R10","R30"]},
            { path: '/project', component: Project, name: '项目进程' ,iconCls:'el-icon-time',blackList:[]},
        ]
    },
    {
        path: '/',
        component: Home,
        name: '系统配置',
        iconCls: 'el-icon-setting',
        blackList:["R01","R10","R20","R30","R40"],
        children: [
            { path: '/resource', component: Resource, name: '资源管理',iconCls:'el-icon-goods',blackList:["R01","R10","R20","R30","R40"] },
            { path: '/template', component: Template, name: '项目模板定义' ,iconCls:'el-icon-star-on',blackList:["R01","R10","R20","R30","R40"]},
            { path: '/param-config', component: ParamConfig, name: '系统参数' ,iconCls:'el-icon-edit-outline',blackList:["R01","R02","R03","R04","R10","R20","R30","R40"]}
        ]
    }
];

export default routes;