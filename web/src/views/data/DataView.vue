<template>
  <div id="both">
    <SideBar @clicked="refpage"></SideBar>
    <ContextField class="chartField">
      <div class="box">
        <EchartsData></EchartsData>
      </div>
    </ContextField>
  </div>
</template>

<script>
import SideBar from "@/components/SideBar.vue";
import router from "@/router";
import ContextField from "@/components/ContextField.vue";
import EchartsData from "@/components/EchartsData.vue";

export default {
  methods: {
    refpage(pagename) {
      this.fadeOut(pagename);
    },
  },
  components: {
    SideBar,
    ContextField,
    EchartsData,
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

    return {
      fadeOut,
    };
  },
};
</script>

<style scoped>
.chartField {
  width: 1000px;
}

.box {
  height: 350px;
}

EchartsData {
  width: 1300px;
}
</style>
