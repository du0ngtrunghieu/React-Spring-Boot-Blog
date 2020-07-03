import Api from "@/helpers/Api";
import { success, error } from "@/helpers/ToastifyHelper";
import { history } from "../../../history";
import {
  GET_ALL_PERMISSION,
  GET_ALL_PERMISSION_TYPE,
  GET_ALL_MODULE,
  UPDATE_PERMISSION,
  DELETE_PERMISSION,
  FILTER_PERMISSION,
} from "../../../constants/ActionTypes";

export const getAllPermission = () => {
  return async (dispatch) => {
    await Api.get(`/permissions`).then((rs) => {
      dispatch({ type: GET_ALL_PERMISSION, data: rs.data.data });
    });
  };
};

export const getAllModule = () => {
  return async (dispatch) => {
    await Api.get(`/permissions/module-type`).then((rs) => {
      dispatch({ type: GET_ALL_MODULE, data: rs.data.data });
    });
  };
};

export const getAllPermissionType = () => {
  return async (dispatch) => {
    await Api.get(`/permissions/type`).then((rs) => {
      dispatch({ type: GET_ALL_PERMISSION_TYPE, data: rs.data.data });
    });
  };
};

export const updatePermission = (id, params) => {
  return async (dispatch) => {
    await Api.put(`/permissions/${id}`, params)
      .then((rs) => {
        dispatch({ type: UPDATE_PERMISSION, data: rs.data.data, id, params });
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

export const deletePermission = (id) => {
  return async (dispatch) => {
    dispatch({ type: DELETE_PERMISSION, id });
  };
};

export const filterPermission = (value) => {
  return (dispatch) => {
    dispatch({ type: FILTER_PERMISSION, value });
  };
};
