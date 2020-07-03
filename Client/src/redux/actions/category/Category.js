import Api from "@/helpers/Api";
import {
  GET_DATA_CATE,
  GET_ALL_DATA_CATE,
  FILTER_DATA,
  GET_ALL_DATA_NOSUBCATE,
  ADD_CATEGORY_FAILURE,
  ADD_CATEGORY_SUCCESS,
} from "@/constants/ActionTypes";
export const getData = (params) => {
  let size = params.perPage || 10;
  let page = params.page || 1;
  params = {
    perPage: size,
    page: page,
  };
  return (dispatch) => {
    Api.get(`/categories?page=${page - 1}&size=${size}`)
      .then((response) => {
        const data = response.data.data.content || null;
        if (data) {
          dispatch({
            type: GET_DATA_CATE,
            data: response.data.data.content,
            totalPages: response.data.data.totalPages,
            params,
          });
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
};

export const getInitialData = () => {
  return async (dispatch) => {
    await Api.get("/categories/initial").then((response) => {
      dispatch({ type: GET_ALL_DATA_CATE, data: response.data.data });
    });
  };
};

export const filterData = (value) => {
  return (dispatch) => dispatch({ type: FILTER_DATA, value });
};

export const getAllDataNoSubCategory = () => {
  return async (dispatch) => {
    await Api.get("/categories/nosubcategory").then((response) => {
      dispatch({ type: GET_ALL_DATA_NOSUBCATE, data: response.data.data });
    });
  };
};
export const addCategory = (params) => {
  return (dispatch, getState) => {
    let pramsUrl = getState().category.params;
    Api.post("/categories", params)
      .then((response) => {
        const data = response.data.data.content || null;
        dispatch({
          type: ADD_CATEGORY_SUCCESS,
          obj: data,
          message: response.data.data.message,
        });
        dispatch(getData(pramsUrl));
      })
      .catch((err) => {
        console.log(err.response);

        // dispatch({
        //   type: ADD_CATEGORY_FAILURE,
        //   data: err.data,
        //   message: err.data.listMessage,
        // });
      });
  };
};
