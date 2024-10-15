import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/user/LoginView.vue'
import IndexView from '../views/index/IndexView.vue'
import RegisterView from '../views/user/RegisterView.vue'
import IndexViewUserLogged from '../views/index/IndexViewUserLogged.vue'
import DataView from '@/views/data/DataView.vue'
import AccountView from '@/views/user/AccountView.vue'
import store from '../store/index'
import TestView from '@/views/test/TestView.vue'

const routes = [
  {
    path: "/test",
    name: "test_view",
    component: TestView
  },
  {
    path: "/login",
    name: "login_view",
    component: LoginView,
  },
  {
    path: "/",
    name: "index",
    component: IndexView
  },
  {
    path: "/register",
    name: "register_view",
    component: RegisterView
  },
  {
    path: "/index",
    name: "index_view_UserLogged",
    component: IndexViewUserLogged,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/account",
    name: "account_view",
    component: AccountView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/data",
    name: "data_view",
    component: DataView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/"
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {

  let flag = 1;
  const jwt_token = localStorage.getItem("token");

  if (jwt_token) {
    store.commit("updateToken", jwt_token);
    store.dispatch("getinfo", {
      success() {
      },
      error() {
        alert("token无效，请重新登录！");
        router.push("/login");
      }
    })
  } else {
    flag = 2;
  }

  if (to.meta.requestAuth && !store.state.user.is_login) {
    if (flag === 1) {
      next();
    } else {
      alert("请先进行登录！");
      next("/login");
    }
  } else {
    next();
  }
})

export default router
