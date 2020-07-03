import React, { Component } from "react";
import { Row, Col } from "reactstrap";
import ListViewConfig from "./DataCategoryConfig";
import queryString from "query-string";
import BreadCrumbs from "@/components/@vuexy/breadCrumbs/BreadCrumb";
class AllCategory extends Component {
  render() {
    return (
      <React.Fragment>
        <BreadCrumbs
          breadCrumbTitle="Tất cả thể loại"
          breadCrumbParent="Thể loại"
          breadCrumbActive="Danh sách thể loại"
        />
        <Row>
          <Col sm="12">
            <ListViewConfig
              parsedFilter={queryString.parse(this.props.location.search)}
            />
          </Col>
        </Row>
      </React.Fragment>
    );
  }
}

export default AllCategory;
