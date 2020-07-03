import React, { Component } from "react";
import { Trash, Lock, Users, Check } from "react-feather";
import BreadCrumbs from "../../../components/@vuexy/breadCrumbs/BreadCrumb";
import Avatar from "../../../components/@vuexy/avatar/AvatarComponent";
import {
  Col,
  Card,
  CardHeader,
  CardTitle,
  Button,
  Row,
  CardBody,
  Label,
  Form,
  FormGroup,
  Badge,
} from "reactstrap";

import { connect } from "react-redux";
import {
  getInfoRole,
  removeUserInRole,
  removePermissionInRole,
  searchPermssionInRole,
  addPermissionToRole,
  searchUserHasRole,
  addUserToRole,
  updateDetailsRole,
} from "../../../redux/actions/role/Role";
import Toggle from "react-toggle";
import { Formik, Field } from "formik";
import * as Yup from "yup";
import ModalRolePermission from "./ModalRolePermission";
import ModalRoleUser from "./ModalRoleUser";
import "react-toggle/style.css";
import "@/assets/scss/plugins/forms/switch/react-toggle.scss";
const PermissionComponent = ({ props, roleId, removePermissionInRole }) => {
  return (
    <div>
      <div className="d-flex justify-content-start align-items-center mb-1">
        <div className="user-page-info">
          <div className="text-bold-600 mb-0">{props.name}</div>
          <span className="font-small-4">{props.description}</span>
        </div>
        <div className="ml-auto">
          <div className="data-list-action">
            <Trash
              className="cursor-pointer"
              size={20}
              onClick={() => {
                removePermissionInRole({
                  roleId: roleId,
                  permissionId: props.id,
                });
              }}
            />
          </div>
        </div>
      </div>
      <hr />
    </div>
  );
};

const UserComponent = ({ props, roleId, removeUserInRole }) => {
  const handleDelete = (params) => {
    removeUserInRole(params);
  };
  return (
    <div>
      <div className="d-flex justify-content-start align-items-center">
        {props.avatar ? (
          <div className="avatar mr-50">
            <img
              src={props.avatar}
              alt="avtar img holder"
              height="50"
              width="50"
            />
          </div>
        ) : (
          <Avatar
            size="lg"
            color="primary"
            className="mr-1"
            content={props.name.charAt(0).toUpperCase()}
          />
        )}

        <div className="user-page-info">
          <div className="text-bold-600 mb-0">{props.email}</div>
          <small>@{props.username}</small>
          {props.verified ? (
            <Badge
              color="primary"
              className="p-0 badge-sm align-middle ml-25"
              pill
            >
              <Check size={14} />
            </Badge>
          ) : (
            ""
          )}
        </div>
        <div className="ml-auto">
          <div className="data-list-action">
            <Trash
              className="cursor-pointer"
              size={20}
              onClick={() => {
                handleDelete({ roleId: roleId, userId: props.id });
              }}
            />
          </div>
        </div>
      </div>
      <hr />
    </div>
  );
};
const formSchema = Yup.object().shape({
  name: Yup.string().required("Vui lòng nhập đầy đủ"),
  displayName: Yup.string().required("Vui lòng nhập đầy đủ"),
});
class DetailRole extends Component {
  state = {
    id: "",
    name: "",
    displayName: "",
    staff: false,
    permissions: [],
    users: [],
  };
  componentDidUpdate(prevProps, prevState) {
    if (this.props.role !== prevProps.role) {
      if (this.props.role.id !== prevState.id) {
        this.setState({ id: this.props.role.id });
      }
      if (this.props.role.name !== prevState.name) {
        this.setState({ name: this.props.role.name });
      }
      if (this.props.role.displayName !== prevState.displayName) {
        this.setState({ displayName: this.props.role.displayName });
      }
      if (this.props.role.permissions !== prevState.permissions) {
        this.setState({ permissions: this.props.role.permissions });
      }
      if (this.props.role.userResponses !== prevState.users) {
        this.setState({ users: this.props.role.userResponses });
      }
      if (this.props.role.staff !== prevState.staff) {
        this.setState({ staff: this.props.role.staff });
      }
    }
  }
  componentDidMount() {
    this.props.getInfoRole(this.props.match.params.id);
  }
  triggerToggle = () => {
    this.modal.toggleModal();
  };
  triggerToggleUser = () => {
    this.modalUser.toggleModal();
  };
  handleSwitchChange = () => {
    this.setState({
      staff: !this.state.staff,
    });
  };
  render() {
    // let { name, displayName } = this.props.dataDetail;

    let { id, name, displayName, permissions, users, staff } = this.state;
    return (
      <React.Fragment>
        <BreadCrumbs
          breadCrumbTitle="Quản lý Role"
          breadCrumbParent="Roles & Permission"
          breadCrumbActive={this.state.displayName}
        />
        <Row>
          <Col sm="12">
            <Formik
              enableReinitialize
              initialValues={{
                id: id,
                name: name,
                displayName: displayName,
                staff: staff,
              }}
              onSubmit={(values) => {
                let dataSend = {
                  name: values.name,
                  isStaff: values.staff,
                  displayName: values.displayName,
                };
                console.log(dataSend);
                this.props.updateDetailsRole(values.id, dataSend);
              }}
              validationSchema={formSchema}
            >
              {({ values, errors, touched, handleSubmit }) => (
                <Form onSubmit={handleSubmit}>
                  <Card>
                    <CardHeader>
                      <CardTitle> Thông tin</CardTitle>
                    </CardHeader>
                    <CardBody>
                      <FormGroup className="my-3">
                        <Label for="name">Tên </Label>
                        <Field
                          name="name"
                          id="name"
                          className={`form-control ${
                            errors.name && touched.name && "is-invalid"
                          }`}
                          value={values.name || ""}
                        />
                        {errors.name && touched.name ? (
                          <div className="invalid-tooltip mt-25">
                            {errors.name}
                          </div>
                        ) : null}
                      </FormGroup>
                      <FormGroup>
                        <Label for="displayName">Tên Hiện Thị</Label>
                        <Field
                          name="displayName"
                          id="displayName"
                          value={values.displayName}
                          className={`form-control ${
                            errors.displayName &&
                            touched.displayName &&
                            "is-invalid"
                          }`}
                        />
                        {errors.displayName && touched.displayName ? (
                          <div className="invalid-tooltip mt-25">
                            {errors.displayName}
                          </div>
                        ) : null}
                      </FormGroup>
                      <label className="react-toggle-wrapper w-25">
                        <Toggle
                          checked={values.staff}
                          onChange={this.handleSwitchChange}
                          className="switch-danger"
                        />
                        <span className="label-text">
                          Đăng nhập vào hệ thống
                        </span>
                      </label>
                    </CardBody>
                  </Card>
                  <Card>
                    <CardHeader>
                      <CardTitle>
                        <Lock size={19} />
                        <span className="text-bold-500 font-medium-2 ml-50">
                          Permissions
                        </span>
                      </CardTitle>
                      <Button.Ripple
                        color="primary"
                        size="sm"
                        onClick={this.triggerToggle}
                      >
                        Thêm
                      </Button.Ripple>
                    </CardHeader>
                    <CardBody className="suggested-block">
                      {permissions.map((rs) => {
                        return (
                          <PermissionComponent
                            props={rs}
                            key={rs.id}
                            roleId={id}
                            removePermissionInRole={
                              this.props.removePermissionInRole
                            }
                          />
                        );
                      })}
                    </CardBody>
                  </Card>
                  <Card>
                    <CardHeader>
                      <CardTitle>
                        <Users size={19} />
                        <span className="text-bold-500 font-medium-2 ml-50">
                          Users
                        </span>
                      </CardTitle>
                      <Button.Ripple
                        color="primary"
                        size="sm"
                        onClick={this.triggerToggleUser}
                      >
                        Thêm
                      </Button.Ripple>
                    </CardHeader>
                    <CardBody className="suggested-block">
                      {users.map((rs) => {
                        return (
                          <UserComponent
                            props={rs}
                            key={rs.id}
                            roleId={id}
                            removeUserInRole={this.props.removeUserInRole}
                          />
                        );
                      })}
                    </CardBody>
                  </Card>
                  <div className="d-flex justify-content-start flex-wrap mt-2">
                    <Button.Ripple
                      className="mr-1 mb-1"
                      color="primary"
                      type="submit"
                    >
                      Lưu thay đổi
                    </Button.Ripple>
                    <Button.Ripple
                      className="mb-1"
                      color="danger"
                      type="reset"
                      outline
                    >
                      Huỷ
                    </Button.Ripple>
                  </div>
                </Form>
              )}
            </Formik>
          </Col>
        </Row>

        <ModalRoleUser
          ref={(modalUser) => (this.modalUser = modalUser)}
          {...this.props}
        />
        <ModalRolePermission
          ref={(modal) => (this.modal = modal)}
          {...this.props}
        />
      </React.Fragment>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    role: state.role.dataDetailRole,
    dataSearchPermission: state.role.permissionsInRole,
    dataSearchUser: state.role.usersHasRole,
  };
};

export default connect(mapStateToProps, {
  getInfoRole,
  removeUserInRole,
  removePermissionInRole,
  searchPermssionInRole,
  addPermissionToRole,
  searchUserHasRole,
  addUserToRole,
  updateDetailsRole,
})(DetailRole);
