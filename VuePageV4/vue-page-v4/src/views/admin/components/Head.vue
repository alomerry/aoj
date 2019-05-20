<template>
    <div style="background-color: #fff;height: 40px;margin-left: 240px">
        <Menu mode="horizontal" active-name="1" theme="light" style="float: right;height: 40px">
            <Submenu name="1">
                <template slot="title" class="title">
                    <span v-text="user.username"></span>
                </template>
                <MenuItem name="3-1" @click.native="logout">登出</MenuItem>
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
        methods: {
            logout() {
                //TODO 请求注销
                this.$axios({
                    url: '/api-ojy' +
                        '/api-oj/logout',
                    method: 'post',
                    data: 'user_id = ' + this.user.user_id,
                }).then(res => {
                    console.info('后台返回的数据', res.data);
                    if (res.data.code === 200) {
                        window.location.replace("/admin/login")
                    } else {
                    }
                });
                this.$store.dispatch('logout');
                this.user = null;
            },
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