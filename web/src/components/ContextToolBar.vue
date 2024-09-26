<template>
  <div class="content-section" id="blur">
    <div class="content-section-title">工具栏</div>
    <ul>
      <li class="adobe-product" style="background-color: rgba(0, 0, 0, 0)">
        <div class="products">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-upload"
            viewBox="0 0 16 16"
          >
            <path
              d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"
            />
            <path
              d="M7.646 1.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 2.707V11.5a.5.5 0 0 1-1 0V2.707L5.354 4.854a.5.5 0 1 1-.708-.708l3-3z"
            />
          </svg>
          上传你的头像
        </div>
        <span class="status">
          <span class="status-circle green"></span>
          Updated</span
        >
        <div class="button-wrapper">
          <button class="content-button" @click="toggle_">上传</button>
        </div>
      </li>
      <li class="adobe-product" style="background-color: rgba(0, 0, 0, 0)">
        <div class="products">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-upload"
            viewBox="0 0 16 16"
          >
            <path
              d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"
            />
            <path
              d="M7.646 1.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 2.707V11.5a.5.5 0 0 1-1 0V2.707L5.354 4.854a.5.5 0 1 1-.708-.708l3-3z"
            />
          </svg>
          设置校园账号
        </div>
        <span class="status">
          <span class="status-circle green"></span>
          Updated</span
        >
        <div class="button-wrapper">
          <button type="button" class="content-button" @click="toggle">
            上传
          </button>
        </div>
      </li>
    </ul>
  </div>
  <div id="popup-setting" class="popupbox">
    <div class="glass-containar">
      <div class="inputbox">
        <!-- <GlassBox></GlassBox> -->
        <!-- <ion-icon name="username"></ion-icon> -->
        <input v-model="sc_username" type="text" required />
        <label for="" style="color: #3c3e43">账号</label>
      </div>
      <div class="inputbox">
        <!-- <GlassBox></GlassBox> -->
        <!-- <ion-icon name="username"></ion-icon> -->
        <input v-model="sc_password" type="text" required />
        <label for="" style="color: #3c3e43">密码</label>
      </div>
      <button
        type="button"
        class="content-button"
        @click="toggle"
        id="sc_account_update"
      >
        提交
      </button>
      <button
        type="button"
        class="content-button"
        @click="toggle"
        id="returnbutton"
      >
        返回
      </button>
    </div>
  </div>

  <div id="popup-url" class="popupbox">
    <div class="glass-containar">
      <div class="inputbox">
        <!-- <GlassBox></GlassBox> -->
        <!-- <ion-icon name="username"></ion-icon> -->
        <input v-model="user_photo_url" type="text" required />
        <label for="" style="color: #3c3e43">您的外部图片url</label>
      </div>
      <button
        type="button"
        class="content-button"
        @click="toggle_"
        id="user_photo_update"
      >
        提交
      </button>
      <button
        type="button"
        class="content-button"
        @click="toggle_"
        id="returnbutton"
      >
        返回
      </button>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import { onMounted, ref } from "vue";
import $ from "jquery";
import store from "@/store";

export default {
  setup() {
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

    let user_photo_url = ref("");
    let sc_username = ref("");
    let sc_password = ref("");

    function fadeOut(url) {
      // console.log("debug:", url);
      var element = document.getElementById("both");
      var opacity = 1;
      var timer = setInterval(function () {
        if (opacity <= 0.1) {
          element.style.opacity = 0;
          clearInterval(timer); // 当透明度达到1时停止定时器
          console.log("clear");
          router.push(url);
        } else {
          opacity -= 0.1;
          element.style.opacity = opacity;
        }
      }, 25);
    }

    function toggle() {
      let blur = document.getElementById("blur");
      blur.classList.toggle("active");
      let popup = document.getElementById("popup-setting");
      popup.classList.toggle("active");
    }

    function toggle_() {
      let blur = document.getElementById("blur");
      blur.classList.toggle("active");
      let popup = document.getElementById("popup-url");
      popup.classList.toggle("active");
    }

    onMounted(() => {
      $("#sc_account_update").click(() => {
        store.dispatch("update_sc_account", {
          sc_username: sc_username.value,
          sc_password: sc_password.value,
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
            alert("设置成功");
          },
          error() {
            alert("设置失败");
          },
        });
      });

      $("#user_photo_update").click(() => {
        store.dispatch("update_user_photo", {
          url: user_photo_url.value,
          success() {
            alert("上传成功");
            location.reload();
          },
          error() {
            alert("上传失败");
          },
        });
      });
    });

    return {
      fadeOut,
      toggle,
      toggle_,
      sc_username,
      sc_password,
      user_photo_url,
    };
  },
};
</script>

<style scoped>
@import url(../assets/css/context.css);

h2 {
  font-weight: 600;
  margin-bottom: 10px;
  color: #333;
}

.content-section.active {
  filter: blur(20px);
  /* pointer-events: none;
  user-select: none; */
}

#popup-setting {
  position: fixed;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  box-sizing: 0, 5px, 30px, rgb(0, 0, 0, 0.3);
  background: transparent;
  visibility: hidden;
  opacity: 0;
  transition: 0.5s;
}

#popup-setting.active {
  visibility: visible;
  opacity: 1;
  transition: 0.5s;
}

#popup-url {
  position: fixed;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  box-sizing: 0, 5px, 30px, rgb(0, 0, 0, 0.3);
  background: transparent;
  visibility: hidden;
  opacity: 0;
  transition: 0.5s;
}

#popup-url.active {
  visibility: visible;
  opacity: 1;
  transition: 0.5s;
}

.popupbox {
  /* display: flex; */
  width: auto;
  height: auto;
}

.glassbutton {
  background: transparent;
}

.glass-containar {
  background-color: var(--content-bg);
  border-radius: 14px;
  border: 1px solid var(--theme-bg-color);
  padding: 20px;
  cursor: pointer;
  transition: 0.3s ease;
}

input {
  background: transparent;
}
</style>
