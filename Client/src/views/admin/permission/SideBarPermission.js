import React, { Component } from "react";
import { Label, Input, FormGroup, Button, Alert, Form } from "reactstrap";
import { X, Check } from "react-feather";
import PerfectScrollbar from "react-perfect-scrollbar";
import classnames from "classnames";
import { Formik, Field } from "formik";
import * as Yup from "yup";
import "react-toggle/style.css";
import "@/assets/scss/plugins/forms/switch/react-toggle.scss";
import Toggle from "react-toggle";
const formSchema = Yup.object().shape({
  name: Yup.string().required("Vui lòng nhập đầy đủ"),
  description: Yup.string().required("Vui lòng nhập đầy đủ"),
});
class DataListSidebar extends Component {
  state = {
    id: "",
    name: "",
    description: "",
    active: false,
    permissionTypeId: "",
    moduleId: "",
    moduleList: [],
    permissionTypeList: [],
  };
  getDataIntoSelect = (props) => {
    if (props.length > 0) {
      return props.map((e) => {
        return {
          value: e.id,
          label: e.name,
          isFixed: true,
        };
      });
    }
    return null;
  };
  addNew = false;
  submitMyForm = null;
  handleSubmitMyForm = (e) => {
    if (this.submitMyForm) {
      this.submitMyForm(e);
    }
  };
  bindSubmitForm = (submitForm) => {
    this.submitMyForm = submitForm;
  };

  componentDidUpdate(prevProps, prevState) {
    if (this.props.data !== null && prevProps.data === null) {
      if (this.props.data.id !== prevState.id) {
        this.setState({ id: this.props.data.id });
      }
      if (this.props.data.name !== prevState.name) {
        this.setState({ name: this.props.data.name });
      }
      if (this.props.data.description !== prevState.description) {
        this.setState({ description: this.props.data.description });
      }

      if (this.props.data.active !== prevState.active) {
        this.setState({ active: this.props.data.active });
      }
      if (this.props.data.permissionTypeId !== prevState.permissionTypeId) {
        this.setState({ permissionTypeId: this.props.data.permissionTypeId });
      }
      if (this.props.data.moduleId !== prevState.moduleId) {
        this.setState({ moduleId: this.props.data.moduleId });
      }
      if (this.props.dataModule.length !== prevState.moduleList) {
        this.setState({ dataModule: this.props.dataModule });
      }
    }

    if (this.props.data === null && prevProps.data !== null) {
      this.setState({
        id: "",
        name: "",
        description: "",
        active: false,
        permissionTypeId: "",
        moduleId: "",
      });
    }

    if (this.addNew) {
      this.setState({
        id: "",
        name: "",
        description: "",
        active: false,
        permissionTypeId: "",
        moduleId: "",
      });
    }

    this.addNew = false;
  }

  handleChangeName = (e) => {};

  render() {
    let {
      show,
      handleSidebar,
      data,
      dataModule,
      dataPermissionType,
    } = this.props;
    let {
      id,
      name,
      description,
      active,
      permissionTypeId,
      moduleId,
    } = this.state;

    return (
      <div
        className={classnames("data-list-sidebar", {
          show: show,
        })}
      >
        <div className="data-list-sidebar-header mt-2 px-2 d-flex justify-content-between">
          <h4>{data !== null ? "Chỉnh sửa thể loại" : "Thêm mới thể loại"}</h4>
          <X size={20} onClick={() => handleSidebar(false, true)} />
        </div>
        <PerfectScrollbar
          className="data-list-fields px-2 mt-3"
          options={{ wheelPropagation: false }}
        >
          <Formik
            enableReinitialize={true}
            initialValues={{
              id: id,
              name: name,
              description: description,
              active: active,
              permissionTypeId: permissionTypeId,
              moduleId: moduleId,
            }}
            onSubmit={(values, { resetForm, setSubmitting }) => {
              let data = {
                name: values.name,
                description: values.description,
                active: values.active,
                permissionTypeId: values.permissionTypeId,
                moduleTypeId: values.moduleId,
                roleId: 4,
              };
              console.log(data);

              values.id
                ? this.props.updatePermission(values.id, data)
                : resetForm();
              if (!values.id) {
                resetForm();
              }
            }}
            validationSchema={formSchema}
          >
            {({
              values,
              errors,
              touched,
              handleSubmit,
              setFieldValue,
              handleReset,
            }) => (
              <Form onSubmit={this.bindSubmitForm(handleSubmit)}>
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
                    <div className="invalid-tooltip mt-25">{errors.name}</div>
                  ) : null}
                </FormGroup>
                <FormGroup>
                  <Label for="displayName">Mô tả</Label>
                  <Field
                    name="description"
                    id="description"
                    value={values.description}
                    className={`form-control ${
                      errors.description && touched.description && "is-invalid"
                    }`}
                  />
                  {errors.description && touched.description ? (
                    <div className="invalid-tooltip mt-25">
                      {errors.description}
                    </div>
                  ) : null}
                </FormGroup>
                <FormGroup>
                  <label className="react-toggle-wrapper">
                    <Label for="staff">Trạng Thái</Label>
                    <Toggle
                      checked={values.active}
                      onChange={() => {
                        setFieldValue("active", !values.active);
                      }}
                      className="switch-danger ml-2"
                    />
                  </label>
                </FormGroup>
                <FormGroup>
                  <Label for="module">Module</Label>
                  <Input
                    type="select"
                    onChange={(e) => {
                      setFieldValue("moduleId", e.target.value);
                    }}
                  >
                    {dataModule &&
                      Object.values(dataModule).map((rs) =>
                        moduleId === rs.id ? (
                          <option key={rs.id} value={rs.id} selected>
                            {rs.name}
                          </option>
                        ) : (
                          <option key={rs.id} value={rs.id}>
                            {rs.name}
                          </option>
                        )
                      )}
                  </Input>
                </FormGroup>
                <FormGroup>
                  <Label for="module">Type Permission</Label>
                  <Input
                    type="select"
                    onChange={(e) => {
                      console.log(e.target.value);
                      setFieldValue("permissionType", e.target.value);
                    }}
                  >
                    {dataPermissionType &&
                      dataPermissionType.map((rs) =>
                        permissionTypeId === rs.id ? (
                          <option key={rs.id} value={rs.id} selected>
                            {rs.name}
                          </option>
                        ) : (
                          <option key={rs.id} value={rs.id}>
                            {rs.name}
                          </option>
                        )
                      )}
                  </Input>
                </FormGroup>
              </Form>
            )}
          </Formik>
        </PerfectScrollbar>
        <div className="data-list-sidebar-footer px-2 d-flex justify-content-start align-items-center mt-1">
          <Button
            color="primary"
            type="submit"
            onClick={this.handleSubmitMyForm}
          >
            {data !== null ? "Sửa" : "Thêm"}
          </Button>
          <Button
            className="ml-1"
            color="danger"
            outline
            onClick={() => handleSidebar(false, true)}
          >
            Cancel
          </Button>
        </div>
      </div>
    );
  }
}

export default DataListSidebar;
