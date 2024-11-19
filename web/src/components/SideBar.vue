<template>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700&display=swap" rel="stylesheet">
  <div class="container">
    <nav>
      <div class="brand">
        <img :src="$store.state.user.photo" alt="" />
        <p>
          Good day,
          <span>{{ $store.state.user.username }}</span>
        </p>
      </div>
      <hr class="line" />
      <ul class="list">
        <li
          @click="handleClick('/index')"
          id="index"
          :class="routePath === '/index' ? 'active' : 'notactive'"
        >
          <img class="icon" src="../../src/assets/img/home.png" alt="">
          <!-- <i class="fa-solid fa-home"></i> -->
          <span>主页</span>
        </li>
        <li
          id="account"
          @click="handleClick('/account')"
          :class="routePath === '/account' ? 'active' : 'notactive'"
        >
          <!-- @click = fadeOut(url) -->
          <img class="icon" src="../../src/assets/img/account.png" alt="">
          <!-- <i class="fa-solid fa-user"></i> -->
          <span>账户</span>
        </li>
        <li
          @click="handleClick('/order')"
          :class="routePath === '/order' ? 'active' : 'notactive'"
        >
        <img class="icon" src="../../src/assets/img/task.png" alt="">
          <!-- <i class="fa-solid fa-box"></i> -->
          <span>队列</span>
        </li>
        <li
          @click="handleClick('/data')"
          :class="routePath === '/data' ? 'active' : 'notactive'"
        >
        <img class="icon" src="../../src/assets/img/data.png" alt="">
          <!-- <i class="fa-solid fa-chart-simple"></i> -->
          <span>数据</span>
        </li>
        <li
          @click="handleClick('/support')"
          :class="routePath === '/support' ? 'active' : 'notactive'"
        >
        <img class="icon" src="../../src/assets/img/message.png" alt="">
          <!-- <i class="fa-solid fa-phone"></i> -->
          <span>工单</span>
        </li>

        <hr class="line" />

        <li @click="logout">
          <img class="icon" src="../../src/assets/img/logout.png" alt="">
          <!-- <i class="fa-solid fa-right-from-bracket"></i> -->
          <span>登出</span>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import router from "@/router";
import { onMounted } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import $ from "jquery";
export default {
  methods: {
    handleClick(pagename) {
      this.$emit("clicked", pagename);
    },
  },
  setup() {
    function fadeOut(url) {
      var element = document.getElementById("both");
      var opacity = 1;
      var timer = setInterval(function () {
        if (opacity <= 0.1) {
          element.style.opacity = 0;
          clearInterval(timer);
          console.log("clear");
          router.push(url);
        } else {
          opacity -= 0.1;
          element.style.opacity = opacity;
        }
      }, 25);
    }

    const store = useStore();

    const logout = () => {
      store.dispatch("logout");
      fadeOut("/");
    };

    const route = useRoute();
    let routePath = route.path;

    onMounted(() => {
      $("li").click(function () {
        $("li").removeClass("active");
        $(this).addClass("active");
      });
    });

    return {
      logout,
      fadeOut,
      routePath,
    };
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  border: 0;
  list-style: none;
  box-sizing: border-box;
}

body {
  height: 100%;
  /* background: linear-gradient(#9fc0cf, #e5edf1); */
  font-family: "Roboto", sans-serif;
}

.icon {
  margin-top: 2px;
  width: 24px;
  height: 24px;
  display: flex;
}
 
.container {
  /* width: 100%;
  height: 100%; */
  display: flex;
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%); /* 垂直居中调整 */
  background-repeat: no-repeat;
  background-position: bottom;
}

.container nav {
  width: 100px;
  height: min-content;
  padding: 16px;
  border-radius: 12px;
  backdrop-filter: blur(18px);
  background: rgba(230, 235, 240, 0.7);
  border: 2px solid rgba(230, 235, 240, 0.7);
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.05);
  transition: 0.6s cubic-bezier(0.8, 0.5, 0.5, 0.8);
}

.container nav:hover {
  width: 280px;
}

nav .brand {
  width: 100%;
  display: flex;
  align-items: center;
}

nav .brand img {
  width: 65px;
  border-radius: 50%;
  border: 4px solid rgba(230, 235, 240, 0.8);
}

nav .brand p {
  color: #505257;
  font-weight: 500;
  font-style: italic;
  text-align: left;
  line-height: 22px;
  margin: 0 0 0 12px;
}

nav .brand p span {
  color: #3c3e43;
  display: block;
  font-size: 26px;
  font-style: normal;
}

nav .line {
  width: 100%;
  height: 2px;
  margin: 16px 0;
  border-radius: 30%;
  background: #87a3af;
}

nav .list {
  width: 100%;
}

nav .list li {
  display: flex;
  margin: 12px 0;
  padding: 20px;
  cursor: pointer;
  border-radius: 8px;
  transition: 0.4s ease-in-out;
  white-space: nowrap; /* 防止文本换行 */
  overflow: hidden; /* 隐藏溢出的文本 */
}

nav .list li:hover {
  background: rgba(111, 130, 150, 0.4);
}

nav .list li.active {
  background: #3681f6;
}

nav .list li.active span {
  color: #fff;
}

nav .list li.active i {
  background: none;
  -webkit-background-clip: unset;
  -webkit-text-fill-color: unset;
  color: #e6ebf0;
}

nav .list i {
  font-size: 20px;
  background: linear-gradient(-135deg, #3c3e43, #838487);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

nav .list span {
  color: #505257;
  font-size: 18px;
  font-weight: 500;
  margin: 0 0 0 12px;
}

nav .brand p,
nav .list span {
  opacity: 0;
  pointer-events: none;
  transform: translateX(20px);
}

@keyframes fadeIn {
  to {
    opacity: 1;
    pointer-events: unset;
    transform: translateX(0);
  }
}

nav:hover .brand p {
  animation: fadeIn 0.4s ease-out forwards;
  animation-delay: 0.4s;
}

nav:hover .list span {
  animation: fadeIn 0.4s ease-out forwards;
}

nav:hover .list li:nth-child(1) span {
  animation-delay: 0.5s;
}

nav:hover .list li:nth-child(2) span {
  animation-delay: 0.6s;
}

nav:hover .list li:nth-child(3) span {
  animation-delay: 0.7s;
}

nav:hover .list li:nth-child(4) span {
  animation-delay: 0.8s;
}

nav:hover .list li:nth-child(5) span {
  animation-delay: 0.9s;
}

nav:hover .list li:nth-child(7) span {
  animation-delay: 1s;
}
</style>
