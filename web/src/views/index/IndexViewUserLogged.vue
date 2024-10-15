<template>
  <div id="both">
    <SideBar @clicked="refpage"></SideBar>
    <ContextField>
      <div v-if="isRender == true">
        <ContextCards></ContextCards>
      </div>
      <div v-else>
        <ul>
          <li>请在账户设置中设置您的校园账号</li>
        </ul>
      </div>
    </ContextField>
  </div>
</template>

<script>
import router from "@/router";
import { onMounted, ref } from "vue";
import ContextField from "@/components/ContextField.vue";
import ContextCards from "@/components/ContextCards.vue";
import SideBar from "@/components/SideBar.vue";

export default {
  methods: {
    refpage(pagename) {
      this.fadeOut(pagename);
    },
  },
  components: {
    ContextField,
    ContextCards,
    SideBar,
  },
  setup() {
    let isRender = ref(false);

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

      if (localStorage.getItem("activityItemList") !== null) {
        isRender.value = true;
      }
    });

    return {
      // logout,
      fadeOut,
      isRender,
    };
  },
};
</script>
