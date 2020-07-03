import {
  GET_ALL_DATA_ROLE,
  GET_DETAIL_ROLE,
  DELETE_USER_ROLE,
  DELETE_ROLE_HAS_PERMISSION,
  SEARCH_PERMISSION,
  ADD_PERMISSION_ROLE,
  ADD_USER_ROLE,
  SEARCH_USER,
  UPDATE_ROLE,
  DELETE_ROLE,
  DELETE_MULTIPLE_ROLE,
  ADD_ROLE,
} from "../../../constants/ActionTypes";

const initialState = {
  data: [],
  dataDetailRole: [],
  permissionsInRole: [],
  usersHasRole: [],
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
    case GET_ALL_DATA_ROLE:
      return {
        ...state,
        data: transform(action.data),
        isLoading: false,
      };
    case GET_DETAIL_ROLE:
      return {
        ...state,
        dataDetailRole: action.data,
        isLoading: false,
      };
    case DELETE_USER_ROLE:
      let dataDUR = Object.values(
        state.data.length > 0
          ? {
              ...state.data,
              [action.params.roleId]: {
                ...state.data[action.params.roleId],
                userResponses: state.data[
                  action.params.roleId
                ].userResponses.filter(
                  (user) => user.id !== action.params.userId
                ),
              },
            }
          : []
      );
      return {
        ...state,
        data: dataDUR,
        dataDetailRole: {
          ...state.dataDetailRole,
          userResponses: state.dataDetailRole.userResponses.filter(
            (user) => user.id !== action.params.userId
          ),
        },
        isLoading: false,
      };
    case SEARCH_USER:
      return {
        ...state,
        usersHasRole: action.data,
        isLoading: true,
      };
    case ADD_USER_ROLE:
      let dataAUR = Object.values(
        state.data.length > 0
          ? {
              ...state.data,
              [action.params.roleId]: {
                ...state.data[action.params.roleId],
                userResponses: [
                  ...state.data[action.params.roleId].userResponses,
                  action.data,
                ],
              },
            }
          : []
      );
      return {
        ...state,
        data: dataAUR,
        dataDetailRole: {
          ...state.dataDetailRole,
          userResponses: [...state.dataDetailRole.userResponses, action.data],
        },
        usersHasRole: state.usersHasRole.filter(
          (user) => user.id !== action.data.id
        ),
      };
    case DELETE_ROLE_HAS_PERMISSION:
      let dataDRHP = Object.values(
        state.data.length > 0
          ? {
              ...state.data,
              [action.params.roleId]: {
                ...state.data[action.params.roleId],
                permissions: state.data[
                  action.params.roleId
                ].permissions.filter(
                  (per) => per.id !== action.params.permissionId
                ),
              },
            }
          : []
      );
      return {
        ...state,
        data: dataDRHP,
        dataDetailRole: {
          ...state.dataDetailRole,
          permissions: state.dataDetailRole.permissions.filter(
            (per) => per.id !== action.params.permissionId
          ),
        },
      };
    case SEARCH_PERMISSION:
      return {
        ...state,
        permissionsInRole: action.data,
        isLoading: true,
      };
    case ADD_PERMISSION_ROLE:
      let dataAPR = Object.values(
        state.data.length > 0
          ? {
              ...state.data,
              [action.params.roleId]: {
                ...state.data[action.params.roleId],
                permissions: [
                  ...state.data[action.params.roleId].permissions,
                  action.data,
                ],
              },
            }
          : []
      );
      return {
        ...state,
        data: transform(dataAPR),
        dataDetailRole: {
          ...state.dataDetailRole,
          permissions: [...state.dataDetailRole.permissions, action.data],
        },
        permissionsInRole: state.permissionsInRole.filter(
          (per) => per.id !== action.data.id
        ),
      };
    case UPDATE_ROLE:
      console.log(state.data[action.id]);
      if (state.data[action.id] !== undefined) {
        return {
          ...state,
          data: {
            ...state.data,
            [action.id]: {
              ...state.data[action.id],
              name: action.params.name,
              displayName: action.params.displayName,
              staff: action.params.isStaff,
              administrator: action.data.administrator,
            },
          },
          dataDetailRole: {
            ...state.dataDetailRole,
            name: action.params.name,
            displayName: action.params.displayName,
            staff: action.params.isStaff,
            administrator: action.data.administrator,
          },
        };
      } else {
        return {
          ...state,
          dataDetailRole: {
            ...state.dataDetailRole,
            name: action.params.name,
            displayName: action.params.displayName,
            staff: action.params.isStaff,
            administrator: action.data.administrator,
          },
        };
      }

    case DELETE_ROLE:
      let data = Object.values(state.data);
      return {
        ...state,
        data: transform(data.filter((role) => role.id !== action.id)),
      };
    case DELETE_MULTIPLE_ROLE:
      let obj = Object.values(state.data);
      return {
        ...state,
        data: transform(
          obj.filter((role) => !action.data.id.includes(role.id))
        ),
      };
    case ADD_ROLE:
      const objAdd = Object.values(state.data);
      return {
        ...state,
        data: transform([
          ...objAdd,
          {
            id: action.data.id,
            name: action.data.name,
            displayName: action.data.displayName,
            staff: action.data.staff,
            administrator: action.data.administrator,
            permissions: [],
            userResponses: [],
          },
        ]),
      };

    default:
      return state;
  }
};
