import React from "react";
import * as Icon from "react-feather";

const horizontalMenuConfig = [
  {
    id: "home",
    title: "Home",
    type: "item",
    icon: <Icon.Home size={20} />,
    permissions: ["admin", "editor"],
    navLink: "/",
  },
  {
    id: "page2",
    title: "Page 2",
    type: "item",
    icon: <Icon.File size={20} />,
    permissions: ["admin", "editor"],
    navLink: "/page2",
  },
  {
    id: "page3",
    title: "Page 3",
    type: "item",
    icon: <Icon.FilePlus size={20} />,
    permissions: ["admin", "editor"],
    navLink: "/page3",
  },
];

export default horizontalMenuConfig;
