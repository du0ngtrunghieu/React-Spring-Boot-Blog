import React from "react";
import {
  Row,
  Col,
  Card,
  CardBody,
  Table,
  Button,
  Input,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownItem,
  DropdownMenu,
  Badge,
} from "reactstrap";
import classnames from "classnames";
import {
  Check,
  Minus,
  Search,
  Edit,
  Trash,
  Users,
  ChevronDown,
  Plus,
  UserCheck,
} from "react-feather";
import Checkbox from "../../../components/@vuexy/checkbox/CheckboxesVuexy";
import "../../../assets/scss/pages/import-export.scss";
import {
  getAllData,
  deleteRole,
  deleteMultipleRoles,
  addRole,
} from "../../../redux/actions/role/Role";
import { connect } from "react-redux";
import BreadCrumbs from "../../../components/@vuexy/breadCrumbs/BreadCrumb";
import ModelAddNew from "./ModalRoleAddNew";
import { FaUserCog } from "react-icons/fa";
const ActionsComponent = (props) => {
  return (
    <div className="data-list-action">
      <Edit
        className="cursor-pointer mr-1"
        size={20}
        onClick={() => {
          props.history.push(`/admin/role/${props.id}`);
        }}
      />

      <Trash
        className="cursor-pointer"
        size={20}
        onClick={() => {
          props.deleteRole(props.id);
        }}
      />
    </div>
  );
};
class AllRoles extends React.Component {
  state = {
    currentIdEdit: null,
    data: [],
    filteredData: [],
    selectedRows: [],
    selectAll: false,
  };
  handleEdit = (id) => {
    this.setState({
      currentIdEdit: id,
    });
  };
  handleFilter = (e) => {
    let data = this.state.data;
    let filteredData = [];
    let value = e.target.value;
    this.setState({ value });
    if (value.length) {
      filteredData = data.filter((col) => {
        let startsWithCondition = col.displayName
          .toLowerCase()
          .startsWith(value.toLowerCase());
        let includesCondition = col.displayName
          .toLowerCase()
          .includes(value.toLowerCase());

        if (startsWithCondition) return startsWithCondition;
        else if (!startsWithCondition && includesCondition)
          return includesCondition;
        else return null;
      });
      this.setState({ value, filteredData });
    }
  };
  triggerToggle = () => {
    this.modal.toggleModal();
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
    await this.props.getAllData();
  }

  static getDerivedStateFromProps(props, state) {
    if (Object.values(props.dataList.data).length !== state.data.length) {
      if (props.dataList.data) {
        let obj = Object.values(props.dataList.data);
        let dataNew = obj.map((rs) => {
          return {
            id: rs.id,
            name: rs.name,
            displayName: rs.displayName,
            users: rs.userResponses.length,
            permissions: rs.permissions.length,
            staff: rs.staff,
          };
        });
        return {
          currentIdEdit: null,
          data: dataNew,
          filteredData: [],
          selectedRows: [],
          selectAll: false,
        };
      }
    }

    // Return null if the state hasn't changed
    return null;
  }

  handleDeleteSeclect = (e) => {
    this.props.deleteMultipleRoles(e);
  };
  render() {
    let { currentIdEdit } = this.state;
    let array = this.state.value ? this.state.filteredData : this.state.data;
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
          <td>
            <Users size={20} />
            {"  "}
            {col.displayName}
          </td>
          <td>{col.users}</td>
          <td>{col.permissions}</td>
          <td>
            {col.staff ? (
              <Badge color="primary" className="mr-1 mb-1">
                <FaUserCog size={20} />
              </Badge>
            ) : (
              ""
            )}
          </td>
          <td>
            <ActionsComponent
              id={col.id}
              {...this.props}
              handleEdit={() => this.handleEdit(col.id)}
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
          breadCrumbTitle="Danh Sách Role"
          breadCrumbParent="Roles & Permission"
          breadCrumbActive="Role & Group"
        />
        <Row className="export-component">
          <Col sm="12">
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
                          onClick={() => this.triggerToggle()}
                          outline
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
                          <th>Name</th>
                          <th>Users</th>
                          <th>Permissions</th>
                          <th>Staff</th>
                          <th>Action</th>
                        </tr>
                      </thead>
                      <tbody>{renderTableData}</tbody>
                    </Table>
                  </Col>
                </Row>
              </CardBody>
            </Card>
          </Col>
        </Row>
        <ModelAddNew ref={(modal) => (this.modal = modal)} {...this.props} />
      </React.Fragment>
    );
  }
}
const mapStateToProps = (state) => ({
  dataList: state.role,
});

export default connect(mapStateToProps, {
  getAllData,
  deleteRole,
  deleteMultipleRoles,
  addRole,
})(AllRoles);
