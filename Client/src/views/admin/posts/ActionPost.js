import React, { Component } from "react";
import { CustomInput, Row, Col, Button } from "reactstrap";
import "react-toggle/style.css";
import "../../../assets/scss/plugins/forms/switch/react-toggle.scss";
import Toggle from "react-toggle";
import * as Icon from "react-feather";
class ActionPost extends Component {
  render() {
    return (
      <React.Fragment>
        <Row>
          <Col sm="12">
            <CustomInput
              type="switch"
              className="d-block mb-2"
              id="pushlisher"
              name="pushlisher"
              inline
            >
              <span className="mb-0 ml-sm-0 switch-label">Public bài viết</span>
            </CustomInput>
          </Col>
          <Col sm="12">
            <CustomInput
              type="switch"
              className="d-block mb-2"
              id="draft"
              name="draft"
              inline
            >
              <span className="mb-0 ml-sm-0 switch-label">
                Lưu vào bản nháp
              </span>
            </CustomInput>
          </Col>
          <Col sm="12">
            <span className="mb-2 ml-sm-0 switch-label">
              <Icon.Home size={20} /> Pushlish
            </span>

            <span className="mb-2 ml-sm-0 switch-label">
              <b> immediate</b>
            </span>
            <span className="mb-2 ml-sm-0 switch-label">
              <b> edit</b>
            </span>
          </Col>
          <Col sm="12" className="mt-2">
            <Button.Ripple className="mr-1" color="primary" size="sm">
              Pushlish
            </Button.Ripple>
            <Button.Ripple color="danger" size="sm" outline>
              Cancel
            </Button.Ripple>
          </Col>
        </Row>
      </React.Fragment>
    );
  }
}

export default ActionPost;
