<template>
  <body class="contextField">
    <div class="dark-light">
      <svg
        viewBox="0 0 24 24"
        stroke="currentColor"
        stroke-width="1.5"
        fill="none"
        stroke-linecap="round"
        stroke-linejoin="round"
      >
        <path d="M21 12.79A9 9 0 1111.21 3 7 7 0 0021 12.79z" />
      </svg>
    </div>
    <div class="app">
      <div class="wrapper">
        <div class="main-container">
          <div class="content-wrapper">
            <slot></slot>
          </div>
        </div>
      </div>
      <div class="overlay-app"></div>
    </div>
  </body>
</template>

<style scoped>
@import url(../assets/css/context.css);
</style>

<script>
import { onMounted } from "vue";
import $ from "jquery";

export default {
  setup() {
    onMounted(() => {
      $(function () {
        $(".menu-link").click(function () {
          $(".menu-link").removeClass("is-active");
          $(this).addClass("is-active");
        });
      });

      $(function () {
        $(".main-header-link").click(function () {
          $(".main-header-link").removeClass("is-active");
          $(this).addClass("is-active");
        });
      });

      const dropdowns = document.querySelectorAll(".dropdown");
      dropdowns.forEach((dropdown) => {
        dropdown.addEventListener("click", (e) => {
          e.stopPropagation();
          dropdowns.forEach((c) => c.classList.remove("is-active"));
          dropdown.classList.add("is-active");
        });
      });

      $(".search-bar input")
        .focus(function () {
          $(".header").addClass("wide");
        })
        .blur(function () {
          $(".header").removeClass("wide");
        });

      $(document).click(function (e) {
        var container = $(".status-button");
        var dd = $(".dropdown");
        if (!container.is(e.target) && container.has(e.target).length === 0) {
          dd.removeClass("is-active");
        }
      });

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

      $(function () {
        $(".status-button:not(.open)").on("click", function () {
          $(".overlay-app").addClass("is-active");
        });
        $(".pop-up .close").click(function () {
          $(".overlay-app").removeClass("is-active");
        });
      });

      $(".status-button:not(.open)").click(function () {
        $(".pop-up").addClass("visible");
      });

      $(".pop-up .close").click(function () {
        $(".pop-up").removeClass("visible");
      });

      const toggleButton = document.querySelector(".dark-light");

      toggleButton.addEventListener("click", () => {
        document.body.classList.toggle("light-mode");
      });
    });
  },
};
</script>
