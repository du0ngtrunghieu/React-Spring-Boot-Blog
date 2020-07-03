import React, { Component } from "react";
import { Label, Input, FormGroup, Button, Alert } from "reactstrap";
import { X } from "react-feather";
import PerfectScrollbar from "react-perfect-scrollbar";
import classnames from "classnames";
import { connect } from "react-redux";
import {
  getAllDataNoSubCategory,
  addCategory,
} from "../../../redux/actions/category/Category";
import CreatableSelect from "react-select/creatable";
import Select from "react-select";
import makeAnimated from "react-select/animated";
import { generateSlug } from "@/helpers/GenerateSlug";
const colourOptions = [
  { value: "ocean", label: "Ocean", color: "#00B8D9", isFixed: true },
  { value: "blue", label: "Blue", color: "#0052CC", isFixed: true },
  { value: "purple", label: "Purple", color: "#5243AA", isFixed: true },
  { value: "red", label: "Red", color: "#FF5630", isFixed: false },
  { value: "orange", label: "Orange", color: "#FF8B00", isFixed: false },
  { value: "yellow", label: "Yellow", color: "#FFC400", isFixed: false },
];
const animatedComponents = makeAnimated();
class DataListSidebar extends Component {
  state = {
    id: "",
    name: "",
    description: "",
    slug: "",
    parent_id: 0,
    enabled: true,
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

  componentDidMount() {
    this.props.getAllDataNoSubCategory();
  }

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

      if (this.props.data.slug !== prevState.slug) {
        this.setState({ slug: this.props.data.slug });
      }
    }

    if (this.props.data === null && prevProps.data !== null) {
      this.setState({
        id: "",
        name: "",
        description: "",
        slug: "",
        parent_id: 0,
        enabled: true,
      });
    }

    if (this.addNew) {
      this.setState({
        id: "",
        name: "",
        description: "",
        slug: "",
        parent_id: 0,
        enabled: true,
      });
    }

    this.addNew = false;
  }

  handleSubmit = (obj) => {
    if (this.props.data !== null) {
      this.props.updateData(obj);
    } else {
      this.addNew = true;
      this.props.addCategory(obj);
    }
    let params = Object.keys(this.props.dataParams).length
      ? this.props.dataParams
      : { page: 1, perPage: 10 };
    this.props.handleSidebar(true, true);
    this.props.getData(params);
  };

  handleChangeName = (e) => {
    this.setState({ name: e.target.value });
    this.setState({ slug: generateSlug(e.target.value) });
  };

  render() {
    let { show, handleSidebar, data } = this.props;
    let { name, slug, description } = this.state;

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
          {/* {this.props.messageSuccess && data === null ? (
            <Alert color="success">{this.props.messageSuccess}</Alert>
          ) : null} */}
          {data !== null ? (
            <FormGroup>
              <Label for="data-name">Thể loại con</Label>
              <Select
                closeMenuOnSelect={false}
                components={animatedComponents}
                defaultValue={this.getDataIntoSelect(
                  this.props.data.subCategories
                )}
                isMulti
                options={this.getDataIntoSelect(this.props.dataList)}
                className="React"
                classNamePrefix="select"
              />
            </FormGroup>
          ) : (
            <FormGroup>
              <Label for="data-name">Thể loại cha</Label>
              <CreatableSelect
                isClearable={true}
                options={this.getDataIntoSelect(this.props.dataList)}
                className="React"
                classNamePrefix="select"
                onChange={(e) => {
                  e == null
                    ? this.setState({ parent_id: 0 })
                    : this.setState({ parent_id: e.value });
                }}
              />
            </FormGroup>
          )}

          <FormGroup>
            <Label for="data-name">Name</Label>
            <Input
              type="text"
              value={name}
              placeholder="Tên thể loại"
              onChange={(e) => {
                this.handleChangeName(e);
              }}
              id="data-name"
            />
          </FormGroup>
          <FormGroup>
            <Label for="data-name">Slug</Label>
            <Input
              type="text"
              value={slug}
              placeholder="the-loai"
              readOnly
              onChange={(e) => this.setState({ slug: e.target.value })}
            />
          </FormGroup>
          <FormGroup>
            <Label for="data-name">Description</Label>
            <Input
              type="textarea"
              value={description}
              placeholder="the-loai"
              onChange={(e) => this.setState({ description: e.target.value })}
            />
          </FormGroup>
        </PerfectScrollbar>
        <div className="data-list-sidebar-footer px-2 d-flex justify-content-start align-items-center mt-1">
          <Button color="primary" onClick={() => this.handleSubmit(this.state)}>
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
const mapStateToProps = (state) => {
  return {
    dataList: state.category.nosubData,
    messageSuccess: state.category.message,
  };
};
export default connect(mapStateToProps, {
  getAllDataNoSubCategory,
  addCategory,
})(DataListSidebar);
