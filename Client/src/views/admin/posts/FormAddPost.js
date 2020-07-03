import React from "react";
import {
  Card,
  CardHeader,
  CardTitle,
  CardBody,
  FormGroup,
  Button,
  Label,
} from "reactstrap";
import { Formik, Field, Form } from "formik";
import * as Yup from "yup";
import { Editor } from "react-draft-wysiwyg";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import "../../../assets/scss/plugins/extensions/editor.scss";

const formSchema = Yup.object().shape({
  title: Yup.string().required("Vui lòng nhập đầy đủ"),
  slug: Yup.string().required("Vui lòng nhập đầy đủ"),
  summary: Yup.string()
    .required("Vui lòng nhập đầy đủ")
    .max(200, "Vui lòng nhập ít hơn 200 kí tự"),
  content: Yup.string()
    .required("Vui lòng nhập đầy đủ")
    .max(5000, "Vui lòng nhập ít hơn 5000 kí tự"),
});

class FormValidation extends React.Component {
  uploadImageCallBack = (file) => {
    console.log("====================================");
    console.log(file);
    console.log("====================================");
  };
  render() {
    return (
      <Card>
        <CardHeader>
          <CardTitle> Thông tin</CardTitle>
        </CardHeader>
        <CardBody>
          <Formik
            initialValues={{
              title: "",
              slug: "",
              summary: "",
              content: "",
            }}
            validationSchema={formSchema}
          >
            {({ errors, touched }) => (
              <Form>
                <FormGroup className="my-3">
                  <Label for="title">Tiêu đề</Label>
                  <Field
                    name="title"
                    id="title"
                    className={`form-control ${
                      errors.title && touched.title && "is-invalid"
                    }`}
                  />
                  {errors.title && touched.title ? (
                    <div className="invalid-tooltip mt-25">{errors.title}</div>
                  ) : null}
                </FormGroup>
                <FormGroup>
                  <Label for="slug">Slug</Label>
                  <Field
                    name="slug"
                    id="slug"
                    className={`form-control ${
                      errors.slug && touched.slug && "is-invalid"
                    }`}
                  />
                  {errors.slug && touched.slug ? (
                    <div className="invalid-tooltip mt-25">{errors.slug}</div>
                  ) : null}
                </FormGroup>
                <FormGroup>
                  <Label for="summary">Tóm tắt</Label>
                  <Field
                    name="summary"
                    id="summary"
                    component="textarea"
                    className={`form-control ${
                      errors.summary && touched.summary && "is-invalid"
                    }`}
                  />

                  {errors.summary && touched.summary ? (
                    <div className="invalid-tooltip mt-25">
                      {errors.summary}
                    </div>
                  ) : null}
                </FormGroup>
                <FormGroup>
                  <Label for="content">Nội dung</Label>
                  <Editor
                    wrapperClassName="demo-wrapper"
                    editorClassName="demo-editor"
                    toolbar={{
                      inline: { inDropdown: true },
                      list: { inDropdown: true },
                      textAlign: { inDropdown: true },
                      link: { inDropdown: true },
                      history: { inDropdown: true },
                      image: {
                        uploadCallback: this.uploadImageCallBack,
                        previewImage: true,
                        alt: { present: true, mandatory: true },
                      },
                    }}
                  />
                </FormGroup>
                <div className="d-flex justify-content-start flex-wrap">
                  <Button.Ripple
                    className="mr-1 mb-1"
                    color="primary"
                    type="submit"
                  >
                    Save Changes
                  </Button.Ripple>
                  <Button.Ripple
                    className="mb-1"
                    color="danger"
                    type="reset"
                    outline
                  >
                    Cancel
                  </Button.Ripple>
                </div>
              </Form>
            )}
          </Formik>
        </CardBody>
      </Card>
    );
  }
}
export default FormValidation;
