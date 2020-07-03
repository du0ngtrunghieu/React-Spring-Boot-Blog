import React from "react";
import {
  Row,
  Col,
  Card,
  CardBody,
  Table,
  Button,
  Input,
  ListGroup,
  ListGroupItem,
  Badge,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownItem,
  DropdownMenu,
} from "reactstrap";
import classnames from "classnames";
import {
  Check,
  Minus,
  Search,
  Edit,
  Trash,
  ChevronDown,
  Plus,
} from "react-feather";
import Checkbox from "../../../components/@vuexy/checkbox/CheckboxesVuexy";
import "../../../assets/scss/pages/import-export.scss";
import {
  getAllPermission,
  getAllModule,
  getAllPermissionType,
  updatePermission,
  deletePermission,
  filterPermission,
} from "../../../redux/actions/permission/Permission";
import "../../../assets/scss/pages/data-list.scss";
import { connect } from "react-redux";
import BreadCrumbs from "../../../components/@vuexy/breadCrumbs/BreadCrumb";
import moment from "moment";
import Sidebar from "./SideBarPermission";
const ActionsComponent = ({ handleCurrentData, data, handleDelete }) => {
  return (
    <div className="data-list-action">
      <Edit
        className="cursor-pointer mr-1"
        size={20}
        onClick={() => {
          return handleCurrentData(data);
        }}
      />

      <Trash
        className="cursor-pointer"
        size={20}
        onClick={() => {
          return handleDelete(data.moduleId);
        }}
      />
    </div>
  );
};

class AllPermissons extends React.Component {
  state = {
    currentIdEdit: null,
    data: [],
    dataModuleType: [],
    dataPermissionType: [],
    filteredData: [],
    selectedRows: [],
    selectAll: false,
    activeList: 0,
    sidebar: false,
    currentData: null,
    value: "",
  };
  toggleList = (list) => {
    if (this.state.activeList !== list) {
      this.setState({ activeList: list });
    }
    if (list !== 0) {
      let data = this.state.data.filter((per) => per.moduleId === list);
      this.setState({ filteredData: data });
    }
  };
  handleSidebar = (boolean, addNew = false) => {
    this.setState({ sidebar: boolean });
    if (addNew === true) this.setState({ currentData: null, addNew: true });
  };
  handleCurrentData = (obj) => {
    this.setState({ currentData: obj });
    this.handleSidebar(true);
  };
  handleEdit = (id) => {
    this.setState({
      currentIdEdit: id,
    });
  };
  handleFilter = (e) => {
    // let data = this.state.data;
    // let filteredData = [];
    // let value = e.target.value;
    // this.setState({ activeList: 0 });
    // this.setState({ value });
    // if (value.length) {
    //   filteredData = data.filter((col) => {
    //     let startsWithCondition = col.name
    //       .toLowerCase()
    //       .startsWith(value.toLowerCase());
    //     let includesCondition = col.description
    //       .toLowerCase()
    //       .includes(value.toLowerCase());

    //     if (startsWithCondition) return startsWithCondition;
    //     else if (!startsWithCondition && includesCondition)
    //       return includesCondition;
    //     else return null;
    //   });
    //   this.setState({ value, filteredData });
    // }
    this.setState({ value: e.target.value });
    this.props.filterPermission(e.target.value);
  };

  handleSelect = (id) => {
    let selectedRows = this.state.selectedRows;
    if (!selectedRows.includes(id)) {
      selectedRows.push(id);
    } else if (selectedRows.includes(id)) {
      selectedRows.splice(selectedRows.indexOf(id), 1);
    } else {
      return null;
    }
    this.setState({ selectedRows });
  };

  handleSelectAll = () => {
    let selectedRows = this.state.selectedRows;
    let data = this.state.data;
    if (selectedRows.length < data.length) {
      let ids = data.map((i) => i.id);
      selectedRows = ids;
    } else if (selectedRows.length === data.length) {
      selectedRows = [];
    } else {
      return null;
    }

    this.setState({ selectedRows });
  };
  async componentDidMount() {
    await this.props.getAllPermission();
    await this.props.getAllModule();
    await this.props.getAllPermissionType();
  }
  componentDidUpdate(prevProps, prevState) {
    if (this.props.dataList !== prevProps.dataList) {
      if (this.props.dataList !== prevState.dataList) {
        this.setState({ data: Object.values(this.props.dataList) });
      }
    }
    if (this.props.dataModule !== prevProps.dataModule) {
      if (this.props.dataModule !== prevState.dataModule) {
        this.setState({ dataModuleType: Object.values(this.props.dataModule) });
      }
    }
    if (this.props.dataPermissionType !== prevProps.dataPermissionType) {
      if (this.props.dataPermissionType !== prevState.dataPermissionType) {
        this.setState({ dataPermissionType: this.props.dataPermissionType });
      }
    }
    if (this.props.dataPermissionType !== prevProps.dataPermissionType) {
      if (this.props.dataPermissionType !== prevState.dataPermissionType) {
        this.setState({ dataPermissionType: this.props.dataPermissionType });
      }
    }
  }
  // static getDerivedStateFromProps(props, state) {
  //   if (
  //     Object.values(props.dataList).length !== state.data.length ||
  //     props.dataModule.length !== state.dataModuleType.length ||
  //     Object.values(props.dataList) === state.data
  //   ) {
  //     if (props.dataList) {
  //       let obj = Object.values(props.dataList);
  //       let dataType;
  //       let dataNew = obj.map((rs) => {
  //         return {
  //           id: rs.id,
  //           name: rs.name,
  //           description: rs.description,
  //           createdAt: moment(rs.createdAt).format("DD/MM/YYYY hh:mm:ss"),
  //           active: rs.active,
  //           moduleId: rs.moduleId,
  //           permissionTypeId: rs.permissionTypeId,
  //         };
  //       });
  //       if (props.dataModule) {
  //         dataType = props.dataModule.map((rs) => {
  //           return {
  //             id: rs.id,
  //             name: rs.name,
  //             permissions: rs.permissions.length,
  //             createdAt: rs.createdAt,
  //           };
  //         });
  //       }

  //       return {
  //         currentIdEdit: null,
  //         data: dataNew,
  //         dataModuleType: dataType,
  //         dataPermissionType: props.dataPermissionType,
  //         filteredData: [],
  //         selectedRows: [],
  //         selectAll: false,
  //       };
  //     }
  //   }

  //   // Return null if the state hasn't changed
  //   return null;
  // }
  render() {
    let array =
      this.state.activeList === 0
        ? this.state.value.length
          ? this.state.filteredData
          : this.state.data
        : this.state.filteredData;

    let module = this.state.dataModuleType;
    let permissionType = this.state.dataPermissionType;
    let { sidebar, currentData } = this.state;

    let renderTableData = array.map((col) => {
      return (
        <tr
          key={col.id}
          className={classnames({
            selected: this.state.selectedRows.includes(col.id),
          })}
        >
          <td>
            <Checkbox
              size="sm"
              color="primary"
              icon={<Check className="vx-icon" size={12} />}
              label=""
              checked={this.state.selectedRows.includes(col.id)}
              onChange={() => this.handleSelect(col.id)}
            />
          </td>
          <td>{col.name}</td>
          <td>{col.description}</td>
          <td>{moment(col.createdAt).format("DD/MM/YYYY hh:mm:ss")}</td>
          <td>
            {col.active ? (
              <div className="bullet bullet-sm bullet-primary"></div>
            ) : (
              <div className="bullet bullet-sm bullet-secondary"></div>
            )}
          </td>
          <td>
            <ActionsComponent
              id={col.id}
              data={col}
              handleCurrentData={this.handleCurrentData}
              handleEdit={() => this.handleEdit(col.id)}
              handleDelete={() => this.props.deletePermission(col.id)}
            />
          </td>
        </tr>
      );
    });
    let IconTag =
      this.state.selectedRows.length !== this.state.data.length &&
      this.state.selectedRows.length
        ? Minus
        : Check;
    return (
      <React.Fragment>
        <BreadCrumbs
          breadCrumbTitle="Danh sách Permission"
          breadCrumbParent="Role & Permission"
          breadCrumbActive="Danh sách permissions"
        />
        <Row className="export-component">
          <Col sm="3">
            <Card>
              <ListGroup tag="div">
                <ListGroupItem
                  className={classnames(
                    {
                      active: this.state.activeList === 0,
                    },
                    "d-flex justify-content-between align-items-center"
                  )}
                  onClick={() => this.toggleList(0)}
                  action
                >
                  <span>Tất Cả</span>
                  <Badge color="info" pill>
                    {this.state.data.length}
                  </Badge>
                </ListGroupItem>
                {module &&
                  module.map((rs) => (
                    <ListGroupItem
                      className={classnames(
                        {
                          active: this.state.activeList === rs.id,
                        },
                        "d-flex justify-content-between align-items-center"
                      )}
                      onClick={() => this.toggleList(rs.id)}
                      key={rs.id}
                      action
                    >
                      <span>{rs.name}</span>
                      {rs.permissions.length > 0 ? (
                        <Badge color="info" pill>
                          {rs.permissions.length}
                        </Badge>
                      ) : (
                        ""
                      )}
                    </ListGroupItem>
                  ))}
              </ListGroup>
            </Card>
          </Col>
          <Col sm="9">
            <div className="data-list list-view">
              <Card>
                <CardBody>
                  <Row>
                    <Col sm="12">
                      <div className="d-flex flex-wrap justify-content-between">
                        <div className="actions-left d-flex flex-wrap">
                          <UncontrolledDropdown className="data-list-dropdown mr-1">
                            <DropdownToggle className="p-1" color="primary">
                              <span className="align-middle mr-1">Actions</span>
                              <ChevronDown size={15} />
                            </DropdownToggle>
                            <DropdownMenu tag="div" right>
                              <DropdownItem
                                tag="a"
                                onClick={() =>
                                  this.handleDeleteSeclect(
                                    this.state.selectedRows
                                  )
                                }
                              >
                                Xoá
                              </DropdownItem>
                              <DropdownItem tag="a">Archive</DropdownItem>
                              <DropdownItem tag="a">Print</DropdownItem>
                              <DropdownItem tag="a">Export</DropdownItem>
                            </DropdownMenu>
                          </UncontrolledDropdown>
                          <Button
                            className="add-new-btn"
                            color="primary"
                            outline
                            onClick={() => this.handleSidebar(true, true)}
                          >
                            <Plus size={15} />
                            <span className="align-middle">Thêm mới</span>
                          </Button>
                        </div>
                        <div className="filter position-relative has-icon-left">
                          <Input
                            type="text"
                            value={this.state.value}
                            onChange={(e) => this.handleFilter(e)}
                          />
                          <div className="form-control-position">
                            <Search size={15} />
                          </div>
                        </div>
                      </div>
                    </Col>
                    <Col sm="12">
                      <Table className="table-hover-animation mt-2" responsive>
                        <thead>
                          <tr>
                            <th>
                              <Checkbox
                                size="sm"
                                color="primary"
                                icon={<IconTag className="vx-icon" size={12} />}
                                checked={this.state.selectedRows.length}
                                onChange={(e) => this.handleSelectAll()}
                              />
                            </th>
                            <th>Tên</th>
                            <th>Mô tả</th>
                            <th>Ngày tạo</th>
                            <th>Trạng thái</th>
                            <th>Action</th>
                          </tr>
                        </thead>
                        <tbody>{renderTableData}</tbody>
                      </Table>
                    </Col>
                  </Row>
                </CardBody>
              </Card>
              <Sidebar
                show={sidebar}
                data={currentData}
                dataModule={module}
                dataPermissionType={permissionType}
                handleSidebar={this.handleSidebar}
                {...this.props}
              />
              <div
                className={classnames("data-list-overlay", {
                  show: sidebar,
                })}
                onClick={() => this.handleSidebar(false, true)}
              />
            </div>
          </Col>
        </Row>
      </React.Fragment>
    );
  }
}
const mapStateToProps = (state) => ({
  dataList: state.permission.data,
  dataFilter: state.permission.filteredData,
  dataModule: state.permission.moduleData,
  dataPermissionType: state.permission.permissionType,
});

export default connect(mapStateToProps, {
  getAllPermission,
  getAllModule,
  getAllPermissionType,
  updatePermission,
  deletePermission,
  filterPermission,
})(AllPermissons);
