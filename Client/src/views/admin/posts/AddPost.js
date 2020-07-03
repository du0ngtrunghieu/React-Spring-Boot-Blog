import React, { Component } from "react";
import { Row, Col, Card, CardHeader, CardBody, CardTitle } from "reactstrap";
import FormAddPost from "./FormAddPost";
import ActionPost from "./ActionPost";
import CategoryTagPost from "./CategoryTagPost";
import Breadcrumbs from "../../../components/@vuexy/breadCrumbs/BreadCrumb";
class AddPost extends Component {
  state = {
    isChecked: false,
  };
  render() {
    return (
      <React.Fragment>
        <Breadcrumbs
          breadCrumbTitle="Thêm bài viết"
          breadCrumbParent="Post"
          breadCrumbActive="Thêm bài viết"
        />
        <Row>
          <Col sm="9">
            <FormAddPost />
          </Col>
          <Col sm="3">
            <Card>
              <CardHeader>
                <CardTitle>Categories & Tags</CardTitle>
              </CardHeader>
              <CardBody>
                <CategoryTagPost />
              </CardBody>
            </Card>
            <Card>
              <CardHeader>
                <CardTitle>Actions</CardTitle>
              </CardHeader>
              <CardBody>
                <ActionPost />
              </CardBody>
            </Card>
          </Col>
        </Row>
      </React.Fragment>
    );
  }
}

export default AddPost;
