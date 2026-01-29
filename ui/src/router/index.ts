import { createRouter, createWebHistory } from 'vue-router'
import LeaderBoard from '@/views/LB/LeaderBoard.vue'
import LBHome   from "@/views/LB/LBHome.vue";
import UserHome from "@/views/User/UserHome.vue";
import UserView from "@/views/User/UserView.vue";
import KuskForm from "../views/Kusk/KuskForm.vue";
import KuskView from "../views/Kusk/KuskView.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/lb',
            name: 'serverHome',
            component: LBHome,
            children: [
                {
                    path: '',
                    name: 'leaderBoard',
                    component: LeaderBoard,
                    children: [
                        {
                            path: 'kusk',
                            name: 'lbNewKusk',
                            component: KuskForm,
                        },
                        {
                            path: 'kusk/:id',
                            name: 'viewKusk',
                            component: KuskView
                        }
                    ]
                }
            ]
        },
        {
            path: '/',
            name: 'userHome',
            component: UserHome,
            children: [
                {
                    path: 'user/:id',
                    name: 'userView',
                    component: UserView
                },
                {
                    path: 'kusk',
                    name: 'newKusk',
                    component: KuskForm,
                }
            ]
        },
        {
            path: '/:pathMatch(.*)*',
            redirect: '/',
        },
    ],
})

export default router
