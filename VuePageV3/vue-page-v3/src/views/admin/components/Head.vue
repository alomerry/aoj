<template>
    <div style="background-color: #fff;height: 40px;margin-left: 240px">
        <Menu mode="horizontal" active-name="1" theme="light" style="float: right;height: 40px">
            <Submenu name="1">
                <template slot="title" class="title">
                    <span v-text="user.username"></span>
                </template>
                <MenuItem name="3-1">登出</MenuItem>
            </Submenu>
        </Menu>
    </div>
</template>

<script>
    export default {
        name: "Head",
        data() {
            return {
                user: this.$store.state.user == null ? {username: 'login'} : this.$store.state.user,
            }
        },
        created() {
        }
    }
</script>

<style scoped>
    .ivu-menu-horizontal {
        height: 40px;
        line-height: 40px;
    }
</style>