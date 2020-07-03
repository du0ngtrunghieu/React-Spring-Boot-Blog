import React, { Component } from "react";
import {
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  FormGroup,
  Input,
} from "reactstrap";
import { Search, PlusCircle } from "react-feather";

const ListPermission = ({ props, roleId, addPermissionToRole }) => {
  const handleClick = (params) => {
    addPermissionToRole(params, props);
  };
  return (
    <div>
      <div className="d-flex justify-content-start align-items-center mb-1">
        <div className="user-page-info">
          <div className="text-bold-600 mb-0">{props.name}</div>
          <span className="font-small-4">{props.description}</span>
        </div>
        <div className="ml-auto">
          <div className="data-list-action">
            <PlusCircle
              className="cursor-pointer"
              size={20}
              onClick={() => {
                handleClick({ roleId: roleId, permissionId: props.id });
              }}
            />
          </div>
        </div>
      </div>
      <hr />
    </div>
  );
};
class ModalRole extends Component {
  state = {
    activeTab: "1",
    modal: false,
    timeout: 0,
    data: [],
  };

  toggleModal = () => {
    this.setState((prevState) => ({
      modal: !prevState.modal,
    }));
  };
  handleSearch(evt) {
    let searchText = evt.target.value;
    if (this.state.timeout) clearTimeout(this.state.timeout);
    this.state.timeout = setTimeout(() => {
      this.props.searchPermssionInRole(searchText, this.props.role.permissions);
    }, 500);
  }
  componentDidUpdate(prevProps, prevState) {
    if (this.props.dataSearchPermission !== prevProps.dataSearchPermission) {
      if (this.props.dataSearchPermission !== prevState.data) {
        this.setState({
          data: this.props.dataSearchPermission,
        });
      }
    }
  }
  render() {
    let { data } = this.state;

    return (
      <div>
        <Modal
          isOpen={this.state.modal}
          toggle={this.toggleModal}
          className={this.props.className}
          scrollable={true}
        >
          <ModalHeader toggle={this.toggleModal}>
            Danh Sách Permission
          </ModalHeader>
          <ModalBody>
            <h5>Nhập tên permission muốn thêm vào </h5>
            <FormGroup className="position-relative">
              <Input
                className="search-product"
                placeholder="Search Here..."
                onChange={(evt) => this.handleSearch(evt)}
              />
              <div className="form-control-position">
                <Search size={22} />
              </div>
            </FormGroup>
            <div className="suggested-block">
              {data &&
                data.map((x) => (
                  <ListPermission
                    key={x.id}
                    props={x}
                    roleId={this.props.role.id}
                    addPermissionToRole={this.props.addPermissionToRole}
                  />
                ))}
            </div>
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={this.toggleModal}>
              Xong
            </Button>{" "}
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}

export default ModalRole;
