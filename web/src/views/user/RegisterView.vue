<template>
  <body>
    <section>
      <div class="form-box" id="both">
        <div class="form-value">
          <form action="">
            <h2 style="color: #3c3e43">注册</h2>
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
            <div class="inputbox">
              <ion-icon name="lock-closed-outline"></ion-icon>
              <input v-model="confirmedPassword" type="password" required />
              <label for="" style="color: #3c3e43">确定密码</label>
            </div>
            <button type="button" @click="register">注册</button>
            <div class="register">
              <p style="color: #3c3e43">
                我已经拥有账号！
                <a @click="fadeOut('/login')" style="color: #3c3e43">登录</a>
              </p>
              <p>
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

    const store = useStore();
    let username = ref("");
    let password = ref("");
    let confirmedPassword = ref("");
    let error_message = ref("");

    const register = () => {
      if (username.value.length > 20) {
        alert("用户名过长！");
        return;
      }

      if (username.value.length < 2) {
        alert("用户名过短");
        return;
      }

      if (password.value.length < 6) {
        alert("密码应大于六位");
        return;
      }

      if (password.value != confirmedPassword.value) {
        alert("密码不一致");
        return;
      }

      error_message.value = "";
      store.dispatch("register", {
        username: username.value,
        password: password.value,
        confirmedPassword: confirmedPassword.value,
        success(resp) {
          console.log(resp);
          alert("您已成为新用户");
          fadeOut("/login");
        },
        error(resp) {
          alert(resp.error_message);
        },
      });
    };

    onMounted(() => {
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
      confirmedPassword,
      register,
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
  height: 500px;
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
  color: #fff;
  text-align: center;
}

.inputbox {
  position: relative;
  margin: 30px 0;
  width: 310px;
  border-bottom: 2px solid #fff;
}

.inputbox label {
  position: absolute;
  top: 50%;
  left: 5px;
  transform: translateY(-50%);
  color: #fff;
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
  color: #fff;
}

.inputbox ion-icon {
  position: absolute;
  right: 8px;
  color: #fff;
  font-size: 18px;
  top: 20px;
}

.forget {
  margin: -15px 0 15px;
  font-size: 14px;
  color: #fff;
  display: flex;
  justify-content: center;
}

.forget label input {
  margin-right: 3px;
}

.forget a {
  text-decoration: none;
  color: #fff;
}

.forget label a:hover {
  text-decoration: underline;
}

button {
  width: 100%;
  height: 40px;
  border-radius: 40px;
  background: #fff;
  border: none;
  outline: none;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
}

.register {
  font-size: 14px;
  color: #fff;
  text-align: center;
  margin: 25px 0 10px;
}

.register p a {
  text-decoration: none;
  color: #fff;
  font-weight: 600;
}

.register p a:hover {
  text-decoration: underline;
}
</style>
