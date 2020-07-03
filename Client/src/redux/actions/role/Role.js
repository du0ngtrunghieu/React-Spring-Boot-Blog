import {
  GET_ALL_DATA_ROLE,
  GET_DETAIL_ROLE,
  DELETE_USER_ROLE,
  DELETE_ROLE_HAS_PERMISSION,
  SEARCH_PERMISSION,
  ADD_PERMISSION_ROLE,
  SEARCH_USER,
  ADD_USER_ROLE,
  UPDATE_ROLE,
  DELETE_ROLE,
  DELETE_MULTIPLE_ROLE,
  ADD_ROLE,
} from "../../../constants/ActionTypes";
import Api from "@/helpers/Api";
import { success, error } from "@/helpers/ToastifyHelper";
import { history } from "../../../history";
export const getAllData = () => {
  return async (dispatch) => {
    await Api.get("/roles/get").then((response) => {
      dispatch({ type: GET_ALL_DATA_ROLE, data: response.data.data });
    });
  };
};

export const getInfoRole = (id) => {
  return async (dispatch) => {
    await Api.get(`/roles/${id}`)
      .then((response) => {
        dispatch({ type: GET_DETAIL_ROLE, data: response.data.data, id });
      })
      .catch((err) => {
        history.push("/404");
      });
  };
};

export const removeUserInRole = (params) => {
  return async (dispatch) => {
    await Api.delete(
      `/roles/user?roleId=${params.roleId}&userId=${params.userId}`
    ).then((rs) => {
      dispatch({ type: DELETE_USER_ROLE, data: rs.data.data, params });
      success(`${rs.data.data.message}`, {
        position: "top-right",
      });
    });
  };
};

export const removePermissionInRole = (params) => {
  return async (dispatch) => {
    await Api.delete(
      `/permissions/rolehaspermission/${params.roleId}/${params.permissionId}`
    )
      .then((rs) => {
        dispatch({
          type: DELETE_ROLE_HAS_PERMISSION,
          data: rs.data.data,
          params,
        });
        success(`${rs.data.data.message}`, {
          position: "top-right",
        });
      })
      .catch((err) => {
        console.log(err);
        error(err, {
          position: "top-right",
        });
      });
  };
};
export const searchPermssionInRole = (words, data) => {
  var url = "";
  data.forEach(function (e) {
    url += "ids" + "=" + e.id + "&";
  });
  url = url.trim("&");
  if (data.length > 0) {
    return async (dispatch) => {
      await Api.post(`/permissions/s?${url}words=${words}`).then((rs) => {
        dispatch({
          type: SEARCH_PERMISSION,
          data: rs.data.data,
        });
      });
    };
  } else {
    return async (dispatch) => {
      await Api.post(`/permissions/search?words=${words}`).then((rs) => {
        dispatch({
          type: SEARCH_PERMISSION,
          data: rs.data.data,
        });
      });
    };
  }
};

export const addPermissionToRole = (params, data) => {
  return async (dispatch) => {
    await Api.post(
      `/permissions/rolehaspermission/${params.roleId}/${params.permissionId}`
    )
      .then((rs) => {
        dispatch({
          type: ADD_PERMISSION_ROLE,
          data: data,
          params,
        });
        success(`${rs.data.data.message}`, {
          position: "top-right",
        });
      })
      .catch((err) => {
        console.log(err);
        error(err, {
          position: "top-right",
        });
      });
  };
};

export const searchUserHasRole = (words, data) => {
  var url = "";
  data.forEach(function (e) {
    url += "ids" + "=" + e.id + "&";
  });
  url = url.trim("&");
  if (data.length > 0) {
    return async (dispatch) => {
      await Api.post(`/users/s?${url}words=${words}`).then((rs) => {
        dispatch({
          type: SEARCH_USER,
          data: rs.data.data,
        });
      });
    };
  } else {
    return async (dispatch) => {
      await Api.post(`/users/search?words=${words}`).then((rs) => {
        dispatch({
          type: SEARCH_USER,
          data: rs.data.data,
        });
      });
    };
  }
};

export const addUserToRole = (params, data) => {
  return async (dispatch) => {
    await Api.put(`/users/userhasrole/${params.roleId}/${params.userId}`)
      .then((rs) => {
        dispatch({
          type: ADD_USER_ROLE,
          data: data,
          params,
        });
        success(`${rs.data.data.message}`, {
          position: "top-right",
        });
      })
      .catch((err) => {
        console.log(err);
        error(err, {
          position: "top-right",
        });
      });
  };
};

export const updateDetailsRole = (id, params) => {
  return async (dispatch) => {
    await Api.put(`/roles/${id}`, params)
      .then((rs) => {
        dispatch({ type: UPDATE_ROLE, data: rs.data.data, params, id });
        success(`${rs.data.data.message}`, {
          position: "top-right",
        });
      })
      .catch((err) => {
        console.log(err);
        error(err, {
          position: "top-right",
        });
      });
  };
};

export const deleteRole = (id) => {
  return async (dispatch) => {
    await Api.delete(`/roles/${id}`)
      .then((rs) => {
        dispatch({ type: DELETE_ROLE, data: rs.data.data, id });
        success(`${rs.data.data.message}`, {
          position: "top-right",
        });
      })
      .catch((err) => {
        console.log(err);

        error(err, {
          position: "top-right",
        });
      });
  };
};

export const deleteMultipleRoles = (params) => {
  return async (dispatch) => {
    let url = "?roleIds=" + params.join("&roleIds=");
    if (params.length > 0) {
      Api.delete(`/roles/delete-multiple-roles/${url}`)
        .then((rs) => {
          dispatch({
            type: DELETE_MULTIPLE_ROLE,
            data: rs.data.data.content,
            params,
          });
          success(`${rs.data.data.message}`, {
            position: "top-right",
          });
        })
        .catch((err) => {
          console.log(err);

          error(err, {
            position: "top-right",
          });
        });
    }
  };
};

export const addRole = (params) => {
  return async (dispatch) => {
    await Api.post(`/roles`, params)
      .then((rs) => {
        dispatch({ type: ADD_ROLE, data: rs.data.data.content });
        success(`${rs.data.data.message}`, {
          position: "top-right",
        });
      })
      .catch((err) => {
        error(err, {
          position: "top-right",
        });
      });
  };
};
