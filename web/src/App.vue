<style>
* {
  box-sizing: border-box;
  padding: 0;
  margin: 0;
}

body {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url(/src/assets/bg.jpg) no-repeat center center fixed;
  background-size: cover;
  /* display: none; */
}
</style>

<template>
  <section></section>
  <RouterView />
</template>

<!-- <script>
import $ from "jquery";

export default {
  setup() {
    $.ajax({
      url: "http://localhost:3000/user/account/token/",
      type: "post",
      data: {
        username: "admin",
        password: "admin",
      },
      success(resp) {
        console.log(resp);
      },
      error(resp) {
        console.log(resp);
      },
    });
  },
};
</script> -->
