import React, { Component } from "react";
import { Row, Col } from "reactstrap";
import Select from "react-select";
import makeAnimated from "react-select/animated";

const animatedComponents = makeAnimated();

const colourOptions = [
  { value: "ocean", label: "Ocean", color: "#00B8D9", isFixed: true },
  { value: "blue", label: "Blue", color: "#0052CC", isFixed: true },
  { value: "purple", label: "Purple", color: "#5243AA", isFixed: true },
  { value: "red", label: "Red", color: "#FF5630", isFixed: false },
  { value: "orange", label: "Orange", color: "#FF8B00", isFixed: false },
  { value: "yellow", label: "Yellow", color: "#FFC400", isFixed: false },
];
class CategoryTagPost extends Component {
  render() {
    return (
      <React.Fragment>
        <Row>
          <Col sm="12">
            <h5 className="text-bold-600 my-1">Categories</h5>
            <Select
              closeMenuOnSelect={false}
              components={animatedComponents}
              defaultValue={[colourOptions[4]]}
              isMulti
              options={colourOptions}
              className="React"
              classNamePrefix="select"
            />
          </Col>
          <Col sm="12">
            <h5 className="text-bold-600 my-1">Tags</h5>
            <Select
              closeMenuOnSelect={false}
              components={animatedComponents}
              defaultValue={[colourOptions[2]]}
              isMulti
              options={colourOptions}
              className="React"
              classNamePrefix="select"
            />
          </Col>
        </Row>
      </React.Fragment>
    );
  }
}

export default CategoryTagPost;
