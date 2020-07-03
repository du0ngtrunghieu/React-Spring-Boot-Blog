import {
  GET_ALL_FILES,
  UPLOAD_FILE,
  DELETE_FILES,
  CREATE_FOLDER,
  UPLOAD_MULTIPLE_FILES,
  SET_UPLOAD_PROGRESS,
  SET_UPLOAD_FILE,
  REFRESH_UPLOAD_FILE,
} from "../../../constants/ActionTypes";
import { http } from "@/helpers/http";
import { success, error } from "@/helpers/ToastifyHelper";
export const getAllFile = () => {
  return async (dispatch) => {
    await http
      .get("/media")
      .then((rs) => {
        dispatch({ type: GET_ALL_FILES, data: rs });
      })
      .catch((err) => {
        console.log("====================================");
        console.log(err);
        console.log("====================================");
      });
  };
};

export const uploadFile = (param, formData) => {
  return async (dispatch) => {
    const config = {
      headers: { "Content-Type": "multipart/form-data" },
      onUploadProgress: (progress) => {
        const { loaded, total } = progress;
        const percentageProgress = Math.floor((loaded / total) * 100);
        dispatch({
          type: SET_UPLOAD_PROGRESS,
          payload: { id: param.id, progress: percentageProgress },
        });
      },
    };
    await http
      .post(
        `/media/upload-file/${param.rootDirName}?parent=${param.parent}`,
        formData,
        config
      )
      .then((rs) => {
        dispatch({ type: UPLOAD_FILE, data: rs, param });
      })
      .catch((err) => {
        console.log("====================================");
        console.log(err);
        console.log("====================================");
      });
  };
};

export const deleteFile = (param) => {
  console.log(param);

  return async (dispatch) => {
    await http
      .delete(`/media/${param.pathReal.split("/").join("%2F")}%2F${param.id}`)
      .then((rs) => {
        dispatch({ type: DELETE_FILES, data: rs, param });
        success(`${rs.message}`, {
          position: "top-right",
        });
      })
      .catch((err) => {
        console.log("====================================");
        console.log(err);
        console.log("====================================");
      });
  };
};

export const createFolder = (param, newFolder) => {
  console.log("====================================");
  console.log(newFolder);
  console.log("====================================");
  return async (dispatch) => {
    await http
      .post(
        `/media/create-folder/${param.pathReal
          .split("/")
          .join("%2F")}%2F${newFolder}`
      )
      .then((rs) => {
        dispatch({ type: CREATE_FOLDER, data: rs, param, newFolder });
        success(`Tạo Folder Thành Công !`, {
          position: "top-right",
        });
      })
      .catch((err) => {
        console.log("====================================");
        console.log(err);
        console.log("====================================");
      });
  };
};
export const uploadMultipeFile = (param, formData) => {
  const config = { headers: { "Content-Type": "multipart/form-data" } };
  return async (dispatch) => {
    await http
      .post(
        `/media/upload-multipe-file/${param.rootDirName}?parent=${param.parent}`,
        formData,
        config
      )
      .then((rs) => {
        console.log(rs);
      })
      .catch((err) => {
        console.log("====================================");
        console.log(err);
        console.log("====================================");
      });
  };
};

/// Progress Bar upload

export const setUploadFile = (data) => ({
  type: SET_UPLOAD_FILE,
  payload: data,
});
export const refreshUploadFile = () => ({
  type: REFRESH_UPLOAD_FILE,
});

export const setUploadProgress = (id, progress) => ({
  type: SET_UPLOAD_PROGRESS,
  payload: {
    id,
    progress,
  },
});

export const successUploadFile = (id) => ({
  type: SUCCESS_UPLOAD_FILE,
  payload: id,
});

export const failureUploadFile = (id) => ({
  type: FAILURE_UPLOAD_FILE,
  payload: id,
});
