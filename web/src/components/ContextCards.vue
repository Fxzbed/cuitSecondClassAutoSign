<template>
  <div id="blur">
    <div
      class="apps-card"
      id="card"
      v-for="(itemGroup, itemGroupIndex) in activityItemList"
      :key="`itemGroup-${itemGroupIndex}`"
    >
      <div
        class="app-card"
        v-for="(item, itemId) in itemGroup"
        :key="`item-${itemId}`"
        @click="toggle(itemGroupIndex, itemId)"
      >
        <span>
          <div id="imagebox">
            <img :src="item.ShowImges" alt="" id="activityImg" />
          </div>
          <p id="activityName">
            {{ item.ActivityName }}
          </p>
        </span>
        <div class="app-card__subtext">活动时间：{{ item.ActivityTime }}</div>
        <div class="app-card-buttons">
          <button type="button" class="content-button">
            {{ item.StatusName }}
          </button>
          <div class="menu"></div>
        </div>
      </div>
    </div>
  </div>
  <div
    v-for="(itemGroup, itemGroupIndex) in activityItemList"
    :key="`itemGroup-${itemGroupIndex}`"
  >
    <div
      v-for="(item, itemId) in itemGroup"
      :key="itemId"
      :id="itemGroupIndex + itemId"
      class="popupbox"
    >
      <div class="glass-containar">
        <ul>
          <li>
            <h4>{{ item.ActivityName }}</h4>
          </li>
          <li>活动地点：{{ item.ActivityAddress }}</li>
          <li>活动时间：{{ item.ActivityTime }}</li>
          <li>活动类型：{{ item.ModuleCode }}</li>
          <li>活动状况：{{ item.StatusName }}</li>
        </ul>
        <button
          type="button"
          class="content-button"
          @click="toggle_postActivity(itemGroupIndex, itemId)"
          id="user_photo_update"
        >
          添加到签到队列
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import $ from "jquery";

export default {
  setup() {
    let activityItemList;

    function toggle(activityGroup, activityName) {
      let blur = document.getElementById("blur");
      blur.classList.toggle("active");
      let popup = document.getElementById(activityGroup + activityName);
      popup.classList.toggle("active");
    }

    function toggle_postActivity(activityGroup, activityName) {
      let blur = document.getElementById("blur");
      blur.classList.toggle("active");
      let popup = document.getElementById(activityGroup + activityName);
      popup.classList.toggle("active");

      $.ajax({
        url: "http://localhost:3000/cuit/sign/add/",
        type: "post",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
        data: {
          activity_id: activityItemList.value[activityGroup][activityName].Id,
          password: localStorage.getItem("password"),
        },
        success(resp) {
          if (resp.error_message === "success") {
            alert("已为您报名并添加到您的签到队列");
          } else {
            if (resp.error_message === "signTypeError") {
              alert("该活动不是扫码类型，无法添加到队列");
            } else {
              alert("添加失败，该活动可能已在队列中！");
            }
          }
        },
        error() {
          console.log("error!!!");
        },
      });
    }

    if (localStorage.getItem("activityItemList") !== null) {
      activityItemList = ref(
        eval("(" + localStorage.getItem("activityItemList") + ")")
      );
    }

    onMounted(() => {
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

      readyForPage();

      $(function () {
        $(".dropdown").on("click", function (e) {
          $(".content-wrapper").addClass("overlay");
          e.stopPropagation();
        });
        $(document).on("click", function (e) {
          if ($(e.target).is(".dropdown") === false) {
            $(".content-wrapper").removeClass("overlay");
          }
        });
      });
    });

    console.log(activityItemList);
    return {
      activityItemList,
      toggle,
      toggle_postActivity,
    };
  },
};
</script>

<style scoped>
@import url(../assets/css/context.css);

#card {
  margin: 10px;
}

#activityImg {
  max-width: 100%; /* 图片最大宽度为容器宽度 */
  max-height: 100%; /* 图片最大高度为容器高度 */
  position: absolute; /* 绝对定位，允许图片在容器内缩放 */
  top: 50%; /* 图片垂直居中 */
  left: 50%; /* 图片水平居中 */
  transform: translate(-50%, -50%); /* 将图片中心点移动到容器的中心 */
  border: 2px solid #333; /* 添加边框 */
}

#imagebox {
  width: 100px; /* 设置图片容器的宽度 */
  height: 100px; /* 设置图片容器的高度 */
  overflow: hidden; /* 隐藏超出容器的图片部分 */
  position: relative; /* 相对定位，为图片绝对定位提供参照 */
  margin-right: 5px;
}

#activityName {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

#blur.active {
  filter: blur(20px);
  pointer-events: none;
  user-select: none;
}

.popupbox.active {
  visibility: visible;
  opacity: 1;
  transition: 0.5s;
}

.popupbox {
  position: fixed;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  box-sizing: auto, rgb(0, 0, 0, 0.3);
  background: transparent;
  visibility: hidden;
  opacity: 0;
  transition: 0.5s;
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
</style>
