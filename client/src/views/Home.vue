<template>
    <div>
        <sidebar-menu :menu="menu" :collapsed="true"></sidebar-menu>
        <router-view></router-view>
    </div>
</template>

<script>
import { SidebarMenu } from 'vue-sidebar-menu'
import 'vue-sidebar-menu/dist/vue-sidebar-menu.css'

export default {
    name:'Home',
    data(){
        return {
            menu: [
                {
                    header: true,
                    title: 'Main Navigation',
                    hiddenOnCollapse: true
                },
                {
                    href: { path: '/new' },
                    title: 'New certificate',
                    icon: 'far fa-plus-square'
                },
                {
                    href: { path: '/revoke' },
                    title: 'Revoke certificate',
                    icon: 'fas fa-eraser'
                },
                {
                    href: '/validity',
                    title: 'Check validity',
                    icon: 'fas fa-check-double'
                },
                {
                    href: '/',
                    title: 'All certificates',
                    icon: 'fas fa-list'
                },
                
            ]
        }
    },
    components: {
        SidebarMenu
    }
}
</script>

<style>

</style>