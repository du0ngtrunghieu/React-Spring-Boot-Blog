import React from "react";
import { GoCheck, GoAlert } from "react-icons/go";
import { FaInfoCircle } from "react-icons/fa";
import { MdPriorityHigh } from "react-icons/md";
import { toast, Slide } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "@/assets/scss/plugins/extensions/toastr.scss";

/*
  Calling these toasts most likely happens in the UI 100% of the time.
  So it is safe to render components/elements as toasts.
*/

// Keeping all the toast ids used throughout the app here so we can easily manage/update over time
// This used to show only one toast at a time so the user doesn't get spammed with toast popups
export const toastIds = {
  internetOnline: "internet-online",
  internetOffline: "internet-offline",
  retryInternet: "internet-retry",
};

// Note: this toast && is a conditional escape hatch for unit testing to avoid an error.
const getDefaultOptions = (options) => ({
  position: toast && toast.POSITION.TOP_RIGHT,
  transition: Slide,
  autoClose: 2000,
  ...options,
});

const Toast = ({ children, success, error, info, warning }) => {
  let componentChildren;
  // Sometimes we are having an "object is not valid as a react child" error and children magically becomes an API error response, so we must use this fallback string
  if (!React.isValidElement(children) && typeof children !== "string") {
    componentChildren = "An error occurred";
  } else {
    componentChildren = children;
  }
  let Icon = GoAlert;

  if (success) Icon = GoCheck;
  if (error) Icon = GoAlert;
  if (info) Icon = FaInfoCircle;
  if (warning) Icon = MdPriorityHigh;

  return (
    <div style={{ paddingLeft: 10, display: "flex", alignItems: "center" }}>
      <div style={{ width: 30, height: 30 }}>
        <Icon style={{ color: "#fff", width: 30, height: 30 }} />
      </div>
      <div style={{ padding: 8, display: "flex", alignItems: "center" }}>
        &nbsp;&nbsp;
        <span style={{ color: "#fff" }}>{componentChildren}</span>
      </div>
    </div>
  );
};

export const success = (msg, opts) => {
  return toast.success(<Toast success>{msg}</Toast>, {
    className: "toast-success",
    ...getDefaultOptions(),
    ...opts,
  });
};

export const error = (msg, opts) => {
  return toast.error(<Toast error>{msg}</Toast>, {
    className: "toast-error",
    ...getDefaultOptions(),
    ...opts,
  });
};

export const info = (msg, opts) => {
  return toast.info(<Toast info>{msg}</Toast>, {
    className: "toast-info",
    ...getDefaultOptions(),
    ...opts,
  });
};

export const warn = (msg, opts) => {
  return toast.warn(<Toast warning>{msg}</Toast>, {
    className: "toast-warn",
    ...getDefaultOptions(),
    ...opts,
  });
};

export const neutral = (msg, opts) => {
  return toast(<Toast warning>{msg}</Toast>, {
    className: "toast-default",
    ...getDefaultOptions(),
    ...opts,
  });
};
