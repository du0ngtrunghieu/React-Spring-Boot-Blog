import React, { Component } from "react";
import BreadCrumbs from "../../../components/@vuexy/breadCrumbs/BreadCrumb";
import { Row, Col } from "reactstrap";
import ListImagesMedia from "./ListImagesMedia";

class Media extends Component {
  render() {
    return (
      <React.Fragment>
        <BreadCrumbs
          breadCrumbTitle="Media"
          breadCrumbParent="Ảnh"
          breadCrumbActive="Quản lý media"
        />
        <Row>
          <Col sm="12">
            <ListImagesMedia />
          </Col>
        </Row>
      </React.Fragment>
    );
  }
}
export default Media;
