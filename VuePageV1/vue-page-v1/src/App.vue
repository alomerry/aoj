<template>
  <div id="app">
    <div class="sk-loading" v-if="loading">
      <div id="loading-img">
        <div class="sk-folding-cube">
          <div class="sk-cube1 sk-cube"></div>
          <div class="sk-cube2 sk-cube"></div>
          <div class="sk-cube4 sk-cube"></div>
          <div class="sk-cube3 sk-cube"></div>
        </div>
      </div>
    </div>
    <Heads></Heads>
    <div>
      <transition name="traAni"
                  enter-active-class="animated fadeInUp" appear>
        <router-view/>
      </transition>
    </div>
  </div>
</template>

<script>
  import Heads from '@/components/Heads'
  // import router from './router'//使用 路由
  export default {
    name: 'App',
    data() {
      return {
        loading: false,
      }
    },
    components: {
      'Heads': Heads,
    },
    created() {
      let that = this;
      /*加载动画*/
      /*setTimeout(function () {
        that.loading = false;
      }, 1000);*/
    }
  }
</script>
<style>
  @import "assets/animate.css";
  @import "assets/loading.css";
  
  body {
    background-color: #eee;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  }
  
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }
  
  .CodeMirror pre {
    text-align: left;
  }

</style>
