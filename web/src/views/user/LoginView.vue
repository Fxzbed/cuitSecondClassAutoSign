<template>
  <body>
    <section>
      <div class="form-box" id="both">
        <div class="form-value">
          <form action="">
            <h2 style="color: #3c3e43">登录</h2>
            <div class="inputbox">
              <ion-icon name="username"></ion-icon>
              <input v-model="username" type="text" required />
              <label for="" style="color: #3c3e43">账号</label>
            </div>
            <div class="inputbox">
              <ion-icon name="lock-closed-outline"></ion-icon>
              <input v-model="password" type="password" required />
              <label for="" style="color: #3c3e43">密码</label>
            </div>
            <!-- <div class="error_message">{{ error_message }}</div> -->
            <button type="button" @click="login" style="color: #3c3e43">
              登录
            </button>
            <div class="register">
              <p style="color: #3c3e43">
                还没有账号？
                <a @click="fadeOut('/register')" style="color: #3c3e43">注册</a>
              </p>
              <p style="color: #3c3e43">
                <a @click="fadeOut('/')" style="color: #3c3e43">首页</a>
              </p>
            </div>
          </form>
        </div>
      </div>
    </section>
    <!-- <script
      type="module"
      src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"
    ></script>
    <script
      nomodule
      src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"
    ></script> -->
  </body>
</template>

<script>
import { onMounted, ref } from "vue";
import router from "@/router";
import { useStore } from "vuex";
import $ from "jquery";

export default {
  methods: {
    fadeOut(url) {
      console.log("debug:", url);
      var element = document.getElementById("both");
      var opacity = 1;
      var timer = setInterval(function () {
        if (opacity <= 0.1) {
          element.style.opacity = 0;
          clearInterval(timer); // 当透明度达到1时停止定时器
          router.push(url);
        } else {
          opacity -= 0.1;
          element.style.opacity = opacity;
        }
      }, 25);
    },
  },
  setup() {
    function fadeOut(url) {
      console.log("debug:", url);
      var element = document.getElementById("both");
      var opacity = 1;
      var timer = setInterval(function () {
        if (opacity <= 0.1) {
          element.style.opacity = 0;
          clearInterval(timer); // 当透明度达到1时停止定时器
          router.push(url);
        } else {
          opacity -= 0.1;
          element.style.opacity = opacity;
        }
      }, 25);
    }

    function getActivity(pageId) {
      console.log(pageId);
      $.ajax({
        url: "http://localhost:3000/cuit/activity/list/",
        type: "get",
        async: false,
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
        data: {
          access_token: localStorage.getItem("access_token"),
          page_id: pageId,
        },
        success(resp) {
          localStorage.setItem("activityJson" + pageId, resp);
        },
        error(resp) {
          console.log(resp);
        },
      });
    }

    function readyForPage() {
      let jsonList = {};
      let jsonGroup = {};
      let itemGroupId = 0;
      let count = 0;
      for (let i = 1; i < 3; i++) {
        getActivity(i.toString());
        let currentjson = eval(
          "(" + localStorage.getItem("activityJson" + i.toString()) + ")"
        );

        for (let itemId = 0; itemId < 10; itemId++) {
          jsonGroup[count++] = currentjson.List.Items[itemId];
          if (count === 3) {
            jsonList[itemGroupId++] = jsonGroup;
            jsonGroup = {};
            count = 0;
          }
        }
      }
      localStorage.setItem("activityItemList", JSON.stringify(jsonList));
    }

    const store = useStore();
    let username = ref("");
    let password = ref("");
    let error_message = ref("");

    const login = () => {
      localStorage.removeItem("token");
      localStorage.removeItem("access_token");
      localStorage.removeItem("sc_username");
      localStorage.removeItem("sc_password");
      error_message.value = "";
      store.dispatch("login", {
        username: username.value,
        password: password.value,
        success() {
          store.dispatch("getinfo", {
            success() {
              if (localStorage.getItem("access_token") !== null) {
                console.log(localStorage.getItem("access_token"));
                readyForPage();
                if (localStorage.getItem("activityItemList") != null) {
                  fadeOut("/index");
                } else {
                  console.log("debug: error");
                }
              } else {
                fadeOut("/index");
              }
            },
            error() {
              localStorage.removeItem("token");
              localStorage.removeItem("access_token");
              localStorage.removeItem("sc_username");
              localStorage.removeItem("sc_password");
              alert("未知的错误");
              fadeOut("/login");
            },
          });
        },
        error() {
          error_message.value = "用户名密码错误";
          alert("用户名或者密码错误");
        },
      });
    };

    onMounted(() => {
      if (localStorage.getItem("token")) {
        router.push("/index");
      }

      var element = document.getElementById("both");
      var opacity = 0;
      var timer = setInterval(function () {
        if (opacity >= 0.9) {
          element.style.opacity = 1;
          clearInterval(timer); // 当透明度达到1时停止定时器
        } else {
          opacity += 0.1;
          element.style.opacity = opacity;
        }
      }, 25);
    });

    return {
      username,
      password,
      error_message,
      login,
    };
  },
};
</script>

<style>
@import url("http://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900");

* {
  margin: 0;
  padding: 0;
}

.form-box {
  position: relative;
  width: 400px;
  height: 450px;
  background: transparent;
  border: 2px solid rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(100px);
  display: flex;
  opacity: 0;
  justify-content: center;
  align-items: center;
}

h2 {
  font-size: 32px;
  color: #3c3e43;
  text-align: center;
}

.error_message {
  color: red;
}

.inputbox {
  position: relative;
  margin: 30px 0;
  width: 310px;
  border-bottom: 2px solid #3c3e43;
}

.inputbox label {
  position: absolute;
  top: 50%;
  left: 5px;
  transform: translateY(-50%);
  color: #3c3e43;
  font-size: 1em;
  pointer-events: none;
  transition: 0.5s;
}

input:focus ~ label,
input:valid ~ label {
  top: -5px;
}

.inputbox input {
  width: 100%;
  height: 50px;
  background-color: transparent;
  border: none;
  outline: none;
  font-size: 1em;
  padding: 0 35px 0 5px;
  color: #3c3e43;
}

.inputbox ion-icon {
  position: absolute;
  right: 8px;
  color: #3c3e43;
  font-size: 18px;
  top: 20px;
}

.forget {
  margin: -15px 0 15px;
  font-size: 14px;
  color: #3c3e43;
  display: flex;
  justify-content: center;
}

.forget label input {
  margin-right: 3px;
}

.forget a {
  text-decoration: none;
  color: #3c3e43;
}

.forget label a:hover {
  text-decoration: underline;
}

button {
  width: 100%;
  height: 40px;
  border-radius: 40px;
  background: #3c3e43;
  border: none;
  outline: none;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
}

.register {
  font-size: 14px;
  color: #3c3e43;
  text-align: center;
  margin: 25px 0 10px;
}

.register p a {
  text-decoration: none;
  color: #3c3e43;
  font-weight: 600;
}

.register p a:hover {
  text-decoration: underline;
}
</style>
