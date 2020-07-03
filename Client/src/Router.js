import React, { Suspense, lazy } from "react";
import { Router, Switch, Route } from "react-router-dom";
import { history } from "./history";
import { connect } from "react-redux";
import Spinner from "./components/@vuexy/spinner/Loading-spinner";
import { ContextLayout } from "./utility/context/Layout";
import { changeNavbarType } from "./redux/actions/customizer";

// Route-based code splitting

// Private Components
const Home = lazy(() => import("./views/pages/Home"));

const Page2 = lazy(() => import("./views/pages/Page2"));
const login = lazy(() => import("./views/pages/authentication/login/Login"));
const forgotPassword = lazy(() =>
  import("./views/pages/authentication/ForgotPassword")
);
const Page3 = lazy(() => import("./views/pages/Page3"));
const AllPosts = lazy(() => import("./views/admin/posts/AllPosts"));
const AddPost = lazy(() => import("./views/admin/posts/AddPost"));
const AllUsers = lazy(() => import("./views/admin/users/AllUser"));
const UserRole = lazy(() => import("./views/admin/users/UserRole"));
const AllCategories = lazy(() =>
  import("./views/admin/categories/AllCategory")
);
const AllRoles = lazy(() => import("./views/admin/roles/AllRoles"));
const DetailRole = lazy(() => import("./views/admin/roles/DetailsRole"));
const AllPermissions = lazy(() =>
  import("./views/admin/permission/AllPermissions")
);

const Media = lazy(() => import("./views/admin/media/Media"));
//public
const HomeIndex = lazy(() => import("./views/pages/HomeIndex"));
const error404 = lazy(() => import("./views/error/404"));
// Set Layout and Component Using App Route

const RouteConfig = ({
  component: Component,
  fullLayout,
  permission,
  user,
  isClient,
  ...rest
}) => (
  <Route
    {...rest}
    render={(props) => {
      return (
        <ContextLayout.Consumer>
          {(context) => {
            let LayoutTag =
              fullLayout === true
                ? context.fullLayout
                : isClient === true
                ? context.horizontalLayout
                : context.state.activeLayout === "horizontal"
                ? context.horizontalLayout
                : context.VerticalLayout;

            return (
              <LayoutTag {...props} permission={props.user}>
                <Suspense fallback={<Spinner />}>
                  <Component {...props} />
                </Suspense>
              </LayoutTag>
            );
          }}
        </ContextLayout.Consumer>
      );
    }}
  />
);
const mapStateToProps = (state) => {
  return {
    user: state.auth,
  };
};

const AppRoute = connect(mapStateToProps)(RouteConfig);

class AppRouter extends React.Component {
  render() {
    return (
      // Set the directory path if you are deploying in sub-folder
      <Router history={history}>
        <Switch>
          <AppRoute exact path="/" component={HomeIndex} isClient />
          <AppRoute exact path="/admin" component={Home} />
          <AppRoute path="/page2" component={Page2} />
          <AppRoute path="/page3" component={Page3} />
          <AppRoute path="/pages/login" component={login} fullLayout />
          <AppRoute
            path="/pages/forgot-password"
            component={forgotPassword}
            fullLayout
          />
          <AppRoute path="/admin/posts" component={AllPosts} />
          <AppRoute path="/admin/categories" component={AllCategories} />
          <AppRoute path="/admin/post/add" component={AddPost} />
          <AppRoute path="/admin/users" component={AllUsers} />
          <AppRoute path="/admin/user/roles" component={UserRole} />
          <AppRoute path="/admin/user/roles" component={UserRole} />
          <AppRoute path="/admin/roles" component={AllRoles} />
          <AppRoute path="/admin/role/:id" component={DetailRole} />
          <AppRoute path="/admin/permissions" component={AllPermissions} />
          <AppRoute path="/admin/media" component={Media} />
          <AppRoute component={error404} fullLayout />
        </Switch>
      </Router>
    );
  }
}

export default AppRouter;
