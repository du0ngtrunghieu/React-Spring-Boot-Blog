import React, { Component } from "react";
import {
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  FormGroup,
  Form,
  Label,
} from "reactstrap";
import { Search, PlusCircle } from "react-feather";
import { Formik, Field } from "formik";
import "react-toggle/style.css";
import "@/assets/scss/plugins/forms/switch/react-toggle.scss";
import * as Yup from "yup";
import Toggle from "react-toggle";

const formSchema = Yup.object().shape({
  name: Yup.string().required("Vui lòng nhập đầy đủ"),
  displayName: Yup.string().required("Vui lòng nhập đầy đủ"),
});
class ModelAddNew extends Component {
  state = {
    activeTab: "1",
    modal: false,
    name: "",
    displayName: "",
    staff: false,
  };

  toggleModal = () => {
    this.setState((prevState) => ({
      modal: !prevState.modal,
    }));
  };
  handleSwitchChange = () => {
    this.setState({
      staff: !this.state.staff,
    });
  };
  render() {
    return (
      <div>
        <Formik
          initialValues={{
            modal: false,
            name: "",
            displayName: "",
            staff: false,
          }}
          onSubmit={(values, { resetForm }) => {
            let dataSend = {
              name: values.name,
              isStaff: values.staff,
              displayName: values.displayName,
            };
            this.props.addRole(dataSend);
            resetForm();
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
            <Form onSubmit={handleSubmit}>
              <Modal
                isOpen={this.state.modal}
                toggle={this.toggleModal}
                className={this.props.className}
                scrollable={true}
              >
                <ModalHeader toggle={this.toggleModal}>
                  Danh Sách Permission
                </ModalHeader>
                <ModalBody>
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
                  <label className="react-toggle-wrapper">
                    <Label for="staff">Đăng nhập vào hệ thống</Label>
                    <Toggle
                      checked={values.staff}
                      onChange={() => {
                        setFieldValue("staff", !values.staff);
                      }}
                      className="switch-danger ml-2"
                    />
                  </label>
                </ModalBody>
                <ModalFooter>
                  <Button color="primary" onClick={handleSubmit}>
                    Thêm mới
                  </Button>{" "}
                  <Button.Ripple color="danger" onClick={handleReset} outline>
                    Huỷ
                  </Button.Ripple>
                </ModalFooter>
              </Modal>
            </Form>
          )}
        </Formik>
      </div>
    );
  }
}

export default ModelAddNew;
