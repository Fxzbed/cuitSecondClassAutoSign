<template>
  <body id="both">
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
          <li class="active">
            <i class="fa-solid fa-home"></i>
            <span>Dashboard</span>
          </li>
          <li id="account">
            <!-- @click = fadeOut(url) -->
            <i class="fa-solid fa-user"></i>
            <span>Accounts</span>
          </li>
          <li>
            <i class="fa-solid fa-box"></i>
            <span>Orders</span>
          </li>
          <li>
            <i class="fa-solid fa-chart-simple"></i>
            <span>Charts</span>
          </li>
          <li>
            <i class="fa-solid fa-phone"></i>
            <span>Support</span>
          </li>

          <hr class="line" />

          <li @click="logout">
            <i class="fa-solid fa-right-from-bracket"></i>
            <span>Logout</span>
          </li>
        </ul>
      </nav>
    </div>
    <ContextField>
      <ContextCards></ContextCards>
    </ContextField>
  </body>
</template>

<script>
import router from "@/router";
import { onMounted } from "vue";
import { useStore } from "vuex";
import $ from "jquery";
import ContextField from "@/components/ContextField.vue";
import ContextCards from "@/components/ContextCards.vue";

export default {
  components: {
    ContextField,
    ContextCards,
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

    onMounted(() => {
      let element = document.getElementById("both");
      console.log(element);
      let opacity = 0;
      let timer = setInterval(function () {
        if (opacity >= 0.9) {
          element.style.opacity = 1;
          clearInterval(timer);
        } else {
          opacity += 0.1;
          element.style.opacity = opacity;
        }
      }, 25);

      $("li").click(function () {
        $("li").removeClass("active");
        $(this).addClass("active");
      });

      $("#account").click(() => {
        fadeOut("/account");
      });
    });

    return {
      logout,
      fadeOut,
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
  width: 320px;
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
  margin: 32px 0;
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
