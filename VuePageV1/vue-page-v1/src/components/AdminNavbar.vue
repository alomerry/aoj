<template>
  <div id="headerDiv">
    <Modal v-model="loginModal_flag" width="360" :mask-closable="false" shadow>
      <p slot="header" style="text-align:left">
        <Icon type="ios-contact" size="21"/>
        <span>Welcome to MOoj</span>
      </p>
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
        <div style="text-align:center; padding:3px">
          <FormItem label="" prop="name">
            <Input v-model="formValidate.username" :maxlength="15" placeholder="请输入用户名" style="width: 320px" clearable type="text">
              <Icon type="ios-contact" slot="prefix"/>
            </Input>
          </FormItem>
          <FormItem label="" prop="pwd">
            <Input v-model="formValidate.pwd" placeholder="请输入密码" :maxlength="16" style="width: 320px" clearable type="password">
              <Icon type="md-key" slot="prefix"/>
            </Input>
          </FormItem>
          <input v-model="formValidate.passwd" style="display: none;" type="hidden"/>
          <Button long type="success" @click.native="loginModalOk()" style="width: 320px">Login</Button>
        </div>
      </Form>
      <div slot="footer">
        <a style="float: left;">No account? Register now!</a>
        <a style="float: right;">Forget Password</a>
        <br>
      </div>
    </Modal>
    <Row>
      <Col span="24">
        <Menu mode="horizontal" theme="light" :active-name="active_name" shadow ref="menu">
          <Col span="3" style="font-size: 20px">Online Judge</Col>
          <MenuItem name="home" to="/home">
            <!--<router-link to="/home">-->
            <Icon type="ios-paper"/>
            Home
            <!--</router-link>-->
          </MenuItem>
          <MenuItem name="problems" to="/problems">
            <Icon type="ios-people"/>
            Problems
          </MenuItem>
          <MenuItem name="contents" to="/contents">
            <Icon type="ios-stats"/>
            Contents
          </MenuItem>
          <MenuItem name="status" to="/status">
            <Icon type="ios-construct"/>
            Status
          </MenuItem>
          <MenuItem name="rank" to="/rank">
            <Icon type="ios-construct"/>
            Rank
          </MenuItem>
          <MenuItem name="about" @click.native="logger" to="/about">
            <Icon type="ios-construct"/>
            About
          </MenuItem>
          <!--<Submenu name="7">
            <template slot="title">
              <Icon type="ios-stats"/>
              Login
            </template>
            <MenuItem name="7-1">Login</MenuItem>
            <MenuItem name="7-2">Register</MenuItem>
          </Submenu>-->
          <Dropdown trigger="click">
            <template v-if="user === null">
              <a href="javascript:void(0)">
                Login
                <Icon type="ios-arrow-down"></Icon>
              </a>
              <DropdownMenu slot="list">
                <DropdownItem @click.native="loginModal_flag = true">Login</DropdownItem>
                <DropdownItem>Register</DropdownItem>
              </DropdownMenu>
            </template>
            <template v-else>
              <a href="javascript:void(0)">
                {{user.nickname}}
                <Icon type="ios-arrow-down"></Icon>
              </a>
              <DropdownMenu slot="list">
                <DropdownItem>Setting</DropdownItem>
                <DropdownItem>Messages</DropdownItem>
                <template v-if="admin">
                  <DropdownItem to="/admin">Control</DropdownItem>
                </template>
                <DropdownItem @click.native="logout">Logout</DropdownItem>
              </DropdownMenu>
            </template>
          </Dropdown>
        </Menu>
      </Col>
    </Row>
    <br>
  </div>
</template>
<script>
  import {hex_md5} from '../assets/md5.js'
  import {mapActions, mapState, mapGetters} from 'vuex' //注册 action 和 state
  
  export default {
    name: 'Heads',
    
    data() {
      return {
        active_name: "home",
        user: null,
        admin: false,
        loginModal_flag: false,
        formValidate: {
          username: '',
          passwd: '',
          pwd: '',
        },
        ruleValidate: {
          username: [
            {required: true, message: 'The name cannot be empty', trigger: 'blur'}
          ],
          pwd: [
            {required: true, message: 'Password cannot be empty', trigger: 'blur'}
          ],
        }
      }
    },
    
    methods: {
      /**
       * 登录请求
       */
      loginModalOk() {
        this.$refs['formValidate'].validate((valid) => {
          if (valid) {
            // console.log("验证登录中...");
            /*var params = new URLSearchParams();
            params.append('username', this.formValidate.name);
            params.append('passwd', hex_md5(encodeURIComponent(this.formValidate.passwd + 'onlinejudge')));*/
            this.formValidate.passwd = hex_md5(encodeURIComponent(this.formValidate.pwd + 'onlinejudge'));
            //清除passwd
            this.$axios({
              url: '/api/api-oj/login',
              method: 'post',
              data: require('querystring').stringify(this.formValidate),
            }).then(res => {
              console.info('后台返回的数据', res.data);
              if (res.data.code === 200) {
                this.$Message.success('Success!');
                this.$store.dispatch('login', res.data.data);
                this.user = this.$store.state.user;
                this.admin = this.$store.state.admin;
                this.loginModal_flag = false;
              } else {
                this.loginModal_flag = true;
                this.$Message.error(res.data.message);
              }
              
            }).catch(err => {
              console.info('报错的信息', err);
              this.$Message.error('Fail!');
              this.loginModal_flag = false;
            });
          } else {
            this.$Message.error('input Error! Please Check!');
            this.loginModal_flag = true;
          }
        });
      },
      
      /**
       * 注销请求
       */
      logout() {
        //TODO 请求注销
        this.$axios({
          url: '/api/api-oj/logout',
          method: 'post',
          data: 'user_id = ' + this.user.user_id,
        }).then(res => {
          console.info('后台返回的数据', res.data);
          if (res.data.code === 200) {
          } else {
          }
        });
        this.$store.dispatch('logout');
        this.user = null;
      },
      
      logger() {
        console.log("md5:" + hex_md5(encodeURIComponent('120211onlinejudge')));
      }
    },
    watch: {
      active_class: function (value, oldValue) {
        console.log("检测到active-class变化:" + value);
        this.active_name = value;
        this.$nextTick(function () {
          this.$refs.menu.updateActiveName();
          console.log("更新menu成功!");
        });
      }
    },
    computed: {
      active_class() {
        return this.$store.state.menu_active_name;
      }
    },
    created() {
      this.user = this.$store.state.user;
      this.admin = this.$store.state.admin;
    }
  };
</script>
