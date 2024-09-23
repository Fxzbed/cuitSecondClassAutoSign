<template>
  <body>
    <div id="navbar">
      <NavBar />
    </div>
  </body>
  <router-view />
</template>

<style>
#navbar {
  display: none;
}
</style>

<script>
import { onMounted } from "vue";
import NavBar from "../../components/NavBar.vue";
import $ from "jquery";

export default {
  components: {
    NavBar,
  },

  setup() {
    onMounted(() => {
      $(document).ready(function () {
        $("#navbar").fadeIn(1000);
      });
    });
  },
};
</script>
