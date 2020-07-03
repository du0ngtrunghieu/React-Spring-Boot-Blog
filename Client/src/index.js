import React, { Suspense, lazy } from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import { Layout } from "./utility/context/Layout";
import * as serviceWorker from "./serviceWorker";
import { store } from "./redux/storeConfig/store";
import Spinner from "./components/@vuexy/spinner/Fallback-spinner";
import "./index.scss";
import config from "./authServices/auth0Config.json";
import { Auth0Provider } from "./authServices/auth0Service";
import { ToastContainer } from "react-toastify";
import { AppContainer } from "react-hot-loader";
const mount = document.querySelectorAll("div.demo-mount-nested-editable");
const LazyApp = lazy(() => import("./App"));

// configureDatabase()
ReactDOM.render(
  <AppContainer>
    <Auth0Provider
      domain={config.domain}
      client_id={config.clientId}
      redirect_uri={window.location.origin + process.env.REACT_APP_PUBLIC_PATH}
    >
      <Provider store={store}>
        <Suspense fallback={<Spinner />}>
          <Layout>
            <LazyApp />
          </Layout>
          <ToastContainer />
        </Suspense>
      </Provider>
    </Auth0Provider>
  </AppContainer>,
  document.getElementById("root")
);
if (module.hot) {
  // enables hot module replacement if plugin is installed
  module.hot.accept();
}
// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
