import {
  FETCH_START,
  FETCH_SUCCESS,
  USER_TOKEN_SET,
  FETCH_ERROR,
} from "@/constants/ActionTypes";
import Api from "@/helpers/Api";

export const userSignIn = ({ email, password }) => {
  console.log({ email, password });

  return (dispatch) => {
    dispatch({ type: FETCH_START });
    Api.post("/auth/signin", {
      usernameOrEmail: email,
      password: password,
    })
      .then((response) => {
        console.log("userSignIn: ", response);

        const data = response.data.data || null;
        if (data) {
          localStorage.setItem("token", JSON.stringify(data.accessToken));
          localStorage.setItem("tokenType", JSON.stringify(data.tokenType));
          Api.defaults.headers.common["Authorization"] =
            data.tokenType + " " + data.accessToken;
          dispatch({ type: FETCH_SUCCESS });
          dispatch({
            type: USER_TOKEN_SET,
            payload: data.accessToken,
          });
        } else {
          dispatch({ type: FETCH_ERROR, payload: response.error });
        }
      })
      .catch((error) => {
        console.log(error.code);

        if (error.code === "ECONNABORTED") {
          dispatch({
            type: FETCH_ERROR,
            payload: "Không thể kết nối đến Server !",
          });
        }
        const _data = error.response || null;
        if (_data) {
          if (_data.data) {
            dispatch({
              type: FETCH_ERROR,
              payload: error.response.data.data.message,
            });
          } else {
            dispatch({
              type: FETCH_ERROR,
              payload: _data.message,
            });
          }
        }

        console.log("Error****:", error.message);
      });
  };
};
