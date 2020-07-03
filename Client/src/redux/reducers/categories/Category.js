import {
  GET_DATA_CATE,
  GET_ALL_DATA_CATE,
  FILTER_DATA,
  GET_ALL_DATA_NOSUBCATE,
  ADD_CATEGORY_SUCCESS,
} from "@/constants/ActionTypes";

const initialState = {
  data: [],
  params: null,
  allData: [],
  totalPages: 0,
  filteredData: [],
  totalRecords: 0,
  sortIndex: [],
  nosubData: [],
  message: "",
};

const moveIndex = (arr, from, to) => {
  let el = arr[from];
  arr.splice(from, 1);
  arr.splice(to, 0, el);
};
const getIndex = (arr, arr2, arr3, params = {}) => {
  if (arr2.length > 0) {
    let startIndex = arr.findIndex((i) => i.id === arr2[0].id) + 1;
    let endIndex = arr.findIndex((i) => i.id === arr2[arr2.length - 1].id) + 1;
    let finalArr = [startIndex, endIndex];
    return (arr3 = finalArr);
  } else {
    let finalArr = [arr.length - parseInt(params.perPage), arr.length];
    return (arr3 = finalArr);
  }
};

export default (state = initialState, action) => {
  switch (action.type) {
    case GET_DATA_CATE:
      return {
        ...state,
        data: action.data,
        totalPages: action.totalPages,
        params: action.params,
        sortIndex: getIndex(
          state.allData,
          action.data,
          state.sortIndex,
          action.params
        ),
      };
    case FILTER_DATA:
      let value = action.value;
      let filteredData = [];
      if (value.length) {
        filteredData = state.allData
          .filter((item) => {
            let startsWithCondition =
              item.name.toLowerCase().startsWith(value.toLowerCase()) ||
              item.description.toLowerCase().startsWith(value.toLowerCase()) ||
              item.slug.toLowerCase().startsWith(value.toLowerCase());

            let includesCondition =
              item.name.toLowerCase().includes(value.toLowerCase()) ||
              item.description.toLowerCase().includes(value.toLowerCase()) ||
              item.slug.toLowerCase().includes(value.toLowerCase());

            if (startsWithCondition) {
              return startsWithCondition;
            } else if (!startsWithCondition && includesCondition) {
              return includesCondition;
            } else return null;
          })
          .slice(state.params.page - 1, state.params.perPage);
        return { ...state, filteredData };
      } else {
        filteredData = state.data;
        return { ...state, filteredData };
      }
    case GET_ALL_DATA_CATE:
      return {
        ...state,
        allData: action.data,
        totalRecords: action.data.length,
        sortIndex: getIndex(action.data, state.data, state.sortIndex),
      };
    case GET_ALL_DATA_NOSUBCATE:
      return {
        ...state,
        nosubData: action.data,
      };
    case ADD_CATEGORY_SUCCESS:
      let id = state.data.slice()[0].id + 1;

      state.data.push({ ...action.obj, id });
      state.allData.unshift({ ...action.obj, id });
      moveIndex(
        state.data,
        state.data.findIndex((i) => i.id === id)
      );
      return {
        ...state,
        data: state.data,
        totalRecords: state.allData.length,
        sortIndex: getIndex(state.allData, state.data, state.sortIndex),
        message: action.message,
      };
    default:
      return state;
  }
};
