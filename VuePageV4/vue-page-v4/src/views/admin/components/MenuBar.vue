<template>
    <div style="height: 100%">
        <Menu active-name="1-2" :open-names="['1']" accordion style="float: left" class="left-menu-bar">
            <img src="./goj.png" style="margin: 30px 0 0 80px" v-on:click="GoToIndex"/>
            <MenuItem name="1" to="/admin/home">
                <Icon type="ios-analytics"/>
                Dashboard
            </MenuItem>
            <Submenu name="2">
                <template slot="title">
                    <Icon type="ios-analytics"/>
                    General
                </template>
                <MenuItem name="2-1" to="/admin/user">User</MenuItem>
                <MenuItem name="2-2" to="/admin/announcement">Announcement</MenuItem>
                <MenuItem name="2-3">System Config</MenuItem>
                <MenuItem name="2-4">Judge Server</MenuItem>
            </Submenu>
            <Submenu name="3">
                <template slot="title">
                    <Icon type="ios-filing"/>
                    Problems
                </template>
                <MenuItem name="3-1" to="/admin/problems">Problem List</MenuItem>
                <MenuItem name="3-2" to="/admin/problem/create">Create Problem</MenuItem>
                <MenuItem name="3-3">Export Or Import</MenuItem>
            </Submenu>
            <Submenu name="4">
                <template slot="title">
                    <Icon type="ios-cog"/>
                    Contest
                </template>
                <MenuItem name="4-1" to="/admin/contests">Contest List</MenuItem>
                <MenuItem name="4-2" to="/admin/contest/create">Create Contest</MenuItem>
                <MenuItem name="4-3" to="/admin/contest/status">Contest Status</MenuItem>
                <MenuItem name="4-4" to="/admin/contest_apply">Contest Apply</MenuItem>
            </Submenu>
        </Menu>
    
    </div>
</template>

<script>

    export default {
        name: "MenuBar",
        data() {
            return {
                height: "height:" + window.innerHeight + "px;",
            }
        },
        methods: {
            GoToIndex() {
                window.location.href = "/index"
            }
        },
        components: {},

    }
</script>

<style scoped>
    .left-menu-bar {
        height: 100%;
    }
</style>