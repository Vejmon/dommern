import { createRouter, createWebHistory } from 'vue-router'
import LeaderBoard from '../views/LeaderBoard.vue'
import View from '@/views/Kusk/View.vue'
import Form from "@/views/Kusk/Form.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'home',
            component: LeaderBoard,
            children: [
                {
                    path: 'kusk',
                    name: 'newKusk',
                    component: Form,
                },
                {
                    path: 'kusk/:id',
                    name: 'viewKusk',
                    component: View
                }
            ]
        },
    ],
})

export default router
