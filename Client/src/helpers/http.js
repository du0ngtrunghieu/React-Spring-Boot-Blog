import axios from "axios";
import qs from "qs";
import { success, error } from "./ToastifyHelper";
import { history } from "../history";
/**
 *
 * parse error response
 */
function parseError(messages) {
  // error
  if (messages) {
    if (messages instanceof Array) {
      return Promise.reject({ messages: messages });
    } else {
      return Promise.reject({ messages: [messages] });
    }
  } else {
    return Promise.reject({ messages: ["Có Lỗi !!"] });
  }
}

/**
 * parse response
 */
function parseBody(response) {
  if (response.status === 200 && response.data.code === 200) {
    // - if use custom status code
    return response.data.data;
  } else {
    return this.parseError(response.data.messages);
  }
}

/**
 * axios instance
 */
let instance = axios.create({
  baseURL: `http://127.0.0.1:8080/api/v1`,
  paramsSerializer: function (params) {
    return qs.stringify(params, { indices: false });
  },
});

// request header
instance.interceptors.request.use(
  (config) => {
    // Do something before request is sent

    // const apiToken = sessionStorage.getItem("token");
    // config.headers = { "Custom-Header-IF-Exist": apiToken };
    return config;
  },
  (error) => {
    // all 4xx/5xx responses will end here
    return Promise.reject(error);
  }
);

// response parse
instance.interceptors.response.use(
  (response) => {
    return parseBody(response);
  },
  (err) => {
    // console.warn("Error status", err.response.status);
    // // return Promise.reject(error)
    // //

    if (!err.response) {
      error("Network error - make sure API is running", {
        position: "top-right",
      });
    }
    if (err.response) {
      return parseError(err.response.data);
    } else {
      return Promise.reject(err);
    }
  }
);

export const http = instance;
