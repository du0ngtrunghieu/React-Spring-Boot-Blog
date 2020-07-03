import { http } from "@/helpers/http";
import { GET_ALL_USER } from "../../../constants/ActionTypes";

export const getAllUser = () => {
  let params = {
    page: 0,
    size: "Promise.reject",
  };
  return async (dispatch) => {
    let data = await http.get(`/users`, params);
    dispatch({ type: GET_ALL_USER, data: data });
  };
};
