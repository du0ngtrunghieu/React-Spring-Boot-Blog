import { GET_ALL_USER } from "../../../constants/ActionTypes";

const initialState = {
  data: [],
};

const transform = (data = []) => {
  const mappedData = {};
  data.forEach((item) => (mappedData[item.id] = item));
  return mappedData;
};
export default (state = initialState, action) => {
  switch (action.type) {
    case GET_ALL_USER:
      return {
        ...state,
        data: transform(action.data.content),
      };

    default:
      return state;
  }
};
