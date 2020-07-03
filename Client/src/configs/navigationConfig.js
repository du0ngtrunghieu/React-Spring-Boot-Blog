import React from "react";
import * as Icon from "react-feather";
const navigationConfig = [
  {
    id: "home",
    title: "Home",
    type: "item",
    icon: <Icon.Home size={20} />,
    navLink: "/",
  },
  {
    type: "groupHeader",
    groupTitle: "APPS",
  },
  {
    id: "post",
    title: "Bài Viết",
    type: "collapse",
    icon: <Icon.Feather size={20} />,
    badge: "warning",
    children: [
      {
        id: "allposts",
        title: "Tất cả bài viết",
        type: "item",
        navLink: "/admin/posts",
      },
      {
        id: "addpost",
        title: "Thêm bài viết",
        type: "item",
        navLink: "/admin/post/add",
      },
    ],
  },
  {
    id: "category",
    title: "Thể loại",
    type: "item",
    icon: <Icon.List size={20} />,
    navLink: "/admin/categories",
  },
  {
    id: "tag",
    title: "Tags",
    type: "collapse",
    icon: <Icon.Tag size={20} />,
    children: [
      {
        id: "alltags",
        title: "Tất cả Tags",
        type: "item",
        navLink: "/admin/tags",
      },
      {
        id: "addtag",
        title: "Thêm thể loại",
        type: "item",
        navLink: "/admin/tag/add",
      },
    ],
  },
  {
    id: "user",
    title: "User",
    type: "collapse",
    icon: <Icon.Users size={20} />,
    children: [
      {
        id: "alluser",
        title: "Tất cả User",
        type: "item",
        navLink: "/admin/users",
      },
      {
        id: "adduser",
        title: "Thêm User",
        type: "item",
        navLink: "/admin/user/add",
      },
      {
        id: "useroles",
        title: "User Roles",
        type: "item",
        navLink: "/admin/user/roles",
      },
    ],
  },
  {
    id: "role",
    title: "Roles & Permissions",
    type: "collapse",
    icon: <Icon.Pocket size={20} />,
    children: [
      {
        id: "allroles",
        title: "Tất cả Roles",
        type: "item",
        navLink: "/admin/roles",
      },
      {
        id: "allpermissions",
        title: "Tất cả Permissions",
        type: "item",
        navLink: "/admin/permissions",
      },
    ],
  },
  {
    id: "media",
    title: "Media",
    type: "item",
    icon: <Icon.Camera size={20} />,
    navLink: "/admin/media",
  },
  {
    id: "comment",
    title: "Comments",
    type: "item",
    icon: <Icon.MessageCircle size={20} />,
    navLink: "/admin/comments",
  },
  {
    id: "setting",
    title: "Settings",
    type: "item",
    icon: <Icon.Settings size={20} />,
    navLink: "/page3",
  },
];

export default navigationConfig;
