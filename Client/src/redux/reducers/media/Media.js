import {
  GET_ALL_FILES,
  UPLOAD_FILE,
  DELETE_FILES,
  CREATE_FOLDER,
  SET_UPLOAD_FILE,
  REFRESH_UPLOAD_FILE,
  SET_UPLOAD_PROGRESS,
} from "../../../constants/ActionTypes";
import { modifyFiles } from "../../../helpers/modifyFiles";

const initialState = {
  data: [],
  isLoading: true,
  message: "",
  fileProgress: [],
  listOldFile: [],
};

const transform = (data = []) => {
  const mappedData = {};
  data.forEach((item) => (mappedData[item.id] = item));
  return mappedData;
};
export default (state = initialState, action) => {
  switch (action.type) {
    case SET_UPLOAD_FILE:
      return {
        ...state,
        fileProgress: {
          ...state.fileProgress,
          ...modifyFiles(state.fileProgress, action.payload),
        },
      };

    case SET_UPLOAD_PROGRESS: {
      return {
        ...state,
        fileProgress: {
          ...state.fileProgress,
          [action.payload.id]: {
            ...state.fileProgress[action.payload.id],
            progress: action.payload.progress,
          },
        },
        listOldFile: [...state.listOldFile, action.payload.id],
      };
    }
    case REFRESH_UPLOAD_FILE:
      return {
        ...state,
        fileProgress: {},
        listOldFile: [],
      };
    case GET_ALL_FILES:
      return {
        ...state,
        data: transform(action.data),
        isLoading: false,
      };
    case UPLOAD_FILE:
      return {
        ...state,
        data: transform(
          Object.values({
            ...state.data,
            [action.param.parent]: {
              ...state.data[action.param.parent],
              childrenIds: [
                ...state.data[action.param.parent].childrenIds,
                action.data.id,
              ],
            },
            [action.data.id]: action.data,
          })
        ),
      };
    case DELETE_FILES:
      return {
        ...state,
        data: transform(
          Object.values(state.data).filter(
            (file) => file.id !== action.param.id
          )
        ),
      };
    case CREATE_FOLDER:
      return {
        ...state,
        data: transform(
          Object.values({
            ...state.data,
            [action.data.parentId]: {
              ...state.data[action.data.parentId],
              childrenIds: [
                ...state.data[action.data.parentId].childrenIds,
                action.data.id,
              ],
            },
            [action.data.id]: action.data,
          })
        ),
      };
    default:
      return state;
  }
};
