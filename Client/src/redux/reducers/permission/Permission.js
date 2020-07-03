import {
  GET_ALL_PERMISSION,
  GET_ALL_PERMISSION_TYPE,
  GET_ALL_MODULE,
  UPDATE_PERMISSION,
  ADD_PERMISSION,
  DELETE_PERMISSION,
  FILTER_PERMISSION,
} from "../../../constants/ActionTypes";

const initialState = {
  data: [],
  moduleData: [],
  permissionType: [],
  filteredData: [],
  isLoading: true,
  message: "",
};
const transform = (data = []) => {
  const mappedData = {};
  data.forEach((item) => (mappedData[item.id] = item));
  return mappedData;
};
export default (state = initialState, action) => {
  switch (action.type) {
    case GET_ALL_PERMISSION:
      return {
        ...state,
        data: transform(action.data),
        isLoading: false,
      };
    case GET_ALL_MODULE:
      return {
        ...state,
        moduleData: action.data,
      };
    case UPDATE_PERMISSION:
      let dataUP = Object.values({
        ...state.data,
        [action.id]: {
          ...state.data[action.id],
          id: action.id,
          name: action.params.name,
          description: action.params.description,
          moduleId: action.params.moduleTypeId,
          active: action.params.active,
        },
      });
      return {
        ...state,
        data: transform(dataUP),
      };
    case ADD_PERMISSION:
      return {
        ...state,
        data: transform(
          Object.values({
            ...state.data,
            [action.id]: [...state.data[action.id], action.params],
          })
        ),
      };
    case DELETE_PERMISSION:
      return {
        ...state,
        data: transform(
          Object.values(state.data).filter((p) => p.id !== action.id)
        ),
      };
    case GET_ALL_PERMISSION_TYPE:
      return {
        ...state,
        permissionType: action.data,
      };
    case FILTER_PERMISSION:
      let value = action.value;
      let filteredData = [];
      if (value.length) {
        filteredData = Object.values(state.data).filter((item) => {
          let startsWithCondition = item.name
            .toLowerCase()
            .startsWith(value.toLowerCase());
          let includesCondition = item.description
            .toLowerCase()
            .includes(value.toLowerCase());

          if (startsWithCondition) return startsWithCondition;
          else if (!startsWithCondition && includesCondition)
            return includesCondition;
          else return [];
        });
        return {
          ...state,
          filteredData: transform(Object.values(filteredData)),
        };
      } else {
        filteredData = state.data;
        return {
          ...state,
          filteredData: transform(Object.values(filteredData)),
        };
      }

    default:
      return state;
  }
};
