import { combineReducers } from "redux";
import customizer from "./customizer/";
import Auth from "./auth/Auth";
import navbar from "./navbar/Index";
import Common from "./Common";
import Category from "./categories/Category";
import Role from "./role/Role";
import Media from "./media/Media";
import Permission from "./permission/Permission";
import User from "./user/User";
const rootReducer = combineReducers({
  customizer: customizer,
  commonData: Common,
  auth: Auth,
  navbar: navbar,
  category: Category,
  role: Role,
  media: Media,
  permission: Permission,
  user: User,
});

export default rootReducer;
