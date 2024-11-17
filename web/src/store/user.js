import $ from 'jquery'
import JSEncrypt from "nodejs-jsencrypt";
import store from '.';

export default {
  state: {
    id: "",
    username: "",
    photo: "",
    token: "",
    is_login: false,
  },
  getters: {
  },
  mutations: {
    updateUser(state, user) {
      state.id = user.id;
      state.username = user.username;
      state.photo = user.photo;
      state.is_login = user.is_login;
    },
    updateToken(state, token) {
      state.token = token;
    },
    getScPassword() {
      var encryptor = new JSEncrypt();
      encryptor.setPublicKey(
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCK4n2xrbtnRyBqMJ2iiDeDRdJ/F8EVmzcjSGy/vVNfEVahl6sQOjQXZTc8AEbiZdyLnP9QwX3ZkIsEGUz1VMaPUJeHLHQC5uVljRWR0ORt4oiU7mtN5ZsEl8gPQBzSbC7IpnXVRN1Mx7s/RlFsWZgkuZKbPjxcfgoA9zXyhmcHywIDAQAB"
      );
      console.log(localStorage.getItem("sc_username"));
      var encryptedValue = encryptor.encrypt(
        localStorage.getItem("sc_username")
      );
      // 将字符串转换为URL编码格式
      var encodedString = encodeURIComponent(encryptedValue);
      localStorage.setItem("password", encodedString);
      console.log(encodedString);
    },
    getAccessToken() {
      store.dispatch("getScToken", {
        password: localStorage.getItem("password"),
        success() {},
        error() {
          console.log("getSc fail");
        },
      });
    },
    getActivity(pageId) {
      console.log(pageId);
      $.ajax({
        url: "/api/cuit/activity/list/",
        type: "get",
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
    },
    logout(state) {
      state.id = "";
      state.username = "";
      state.photo = "";
      state.token = "";
      state.is_login = false;
    }
  },
  actions: {
    login(context, data) {
      $.ajax({
        url: "/api/user/account/token/",
        type: "post",
        data: {
          username: data.username,
          password: data.password,
        },
        success(resp) {
          if (resp.error_message === "success") {
            console.log("success");
            localStorage.setItem("token", resp.token);
            context.commit("updateToken", resp.token);
            data.success(resp);
          } else {
            data.error(resp);
          }
        },
        error(resp) {
          data.error(resp);
        }
      });
    },
    getinfo(context, data) {
      $.ajax({
        url: "/api/user/account/getinfo/",
        async: false,
        type: "get",
        headers: {
          Authorization: "Bearer " + context.state.token
        },
        success(resp) {
          if (resp.error_message === "success") {
            context.commit("updateUser", {
              ...resp,
              is_login: true
            });
            localStorage.setItem("sc_username", resp.sc_username);
            localStorage.setItem("sc_password", resp.sc_password);
            console.log(resp.sc_password)
            if (localStorage.getItem("password") === null && resp.sc_username !== null) {
              context.commit("getScPassword");
            }
            if (localStorage.getItem("access_token") === null && localStorage.getItem("password") !== null) {
              context.commit("getAccessToken");
            }
            data.success(resp);
          } else {
            data.error(resp);
          }
        },
        error(resp) {
          localStorage.removeItem('token');
          data.error(resp.error_message);
        }
      })
    },
    register(context, data) {
      $.ajax({
        url: "/api/user/account/register/",
        type: "post",
        data: {
          username: data.username,
          password: data.password,
          confirmedPassword: data.confirmedPassword
        },
        success(resp) {
          if (resp.error_message === "success") {
            data.success(resp);
          } else {
            data.error(resp);
          }
        },
        error(resp) {
          data.error(resp);
        }
      })
    },
    update_sc_account(context, data) {
      $.ajax({
        url: "/api/user/account/scupdate/",
        type: "post",
        headers: {
          Authorization: "Bearer " + context.state.token
        },
        data: {
          sc_username: data.sc_username,
          sc_password: data.sc_password,
        },
        success(resp) {
          if (resp.error_message === "success") {
            data.success();
          } else {
            data.error();
          }
        },
        error(resp) {
          data.error(resp.error_message);
        }
      })
    },
    update_user_photo(context, data) {
      $.ajax({
        url: "/api/user/account/updatephoto/",
        type: "post",
        headers: {
          Authorization: "Bearer " + context.state.token
        },
        data: {
          url: data.url
        },
        success(resp) {
          if (resp.error_message === "success") {

            data.success();
          } else {
            data.error();
          }
        },
        error() {
          data.error();
        }
      })
    },
    getScore(context, data) {
      $.ajax({
        url: "/api/user/account/getScore/",
        type: "post",
        async: false,
        headers: {
          Authorization: "Bearer " + context.state.token
        },
        data: {
          access_token: data.access_token
        },
        success(resp) {
          if (resp.error_message === "success") {
            data.success(resp);
          } else {
            data.error();
          }
        }
      })
    },
    getScToken(context, data) {
      $.ajax({
        url: "/api/cuit/token/",
        type: "get",
        async: false,
        headers: {
          Authorization: "Bearer " + context.state.token
        },
        data: {
          http_password: data.password
        },
        success(resp) {
          if (resp !== "") {
            // console.log(resp);
            let json = eval("(" + resp + ")")
            console.log(json);
            localStorage.setItem("access_token", json.access_token);
            data.success(resp);
          } else {
            data.error(resp)
          }
        },
        error(resp) {
          data.error(resp);
        }
      })
    },
    logout(context) {
      localStorage.removeItem("token");
      localStorage.removeItem("access_token");
      localStorage.removeItem("sc_username");
      localStorage.removeItem("sc_password");
      localStorage.removeItem("activityItemList");
      localStorage.removeItem("password");
      context.commit("logout");
    }
  },
  modules: {

  }
}
