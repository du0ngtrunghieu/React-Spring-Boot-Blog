import React, { Component } from "react";
import DataTablePost from "./DataTablePost";
import Breadcrumbs from "../../../components/@vuexy/breadCrumbs/BreadCrumb";
import { Row, Col } from "reactstrap";
class AllPosts extends Component {
  render() {
    return (
      <React.Fragment>
        <Breadcrumbs
          breadCrumbTitle="Tất cả bài viết"
          breadCrumbParent="Post"
          breadCrumbActive="DataTables"
        />
        <Row>
          <Col sm="12">
            <DataTablePost />
          </Col>
        </Row>
      </React.Fragment>
    );
  }
}

export default AllPosts;
