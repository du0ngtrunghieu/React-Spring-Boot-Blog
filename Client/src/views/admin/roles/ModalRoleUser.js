import React, { Component } from "react";
import {
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  FormGroup,
  Input,
  Badge,
} from "reactstrap";
import Avatar from "../../../components/@vuexy/avatar/AvatarComponent";
import { Search, PlusCircle, Check } from "react-feather";

const ListPermission = ({ props, roleId, addUserToRole }) => {
  const handleClick = (params) => {
    addUserToRole(params, props);
  };
  return (
    <div>
      <div className="d-flex justify-content-start align-items-center mb-1">
        {props.avatar ? (
          <div className="avatar mr-50">
            <img
              src={props.avatar}
              alt="avtar img holder"
              height="50"
              width="50"
            />
          </div>
        ) : (
          <Avatar
            size="lg"
            color="primary"
            className="mr-1"
            content={props.name.charAt(0).toUpperCase()}
          />
        )}
        <div className="user-page-info">
          <div className="text-bold-600 mb-0">{props.email}</div>
          <small>@{props.username}</small>
          {props.verified ? (
            <Badge
              color="primary"
              className="p-0 badge-sm align-middle ml-25"
              pill
            >
              <Check size={14} />
            </Badge>
          ) : (
            ""
          )}
        </div>
        <div className="ml-auto">
          <div className="data-list-action">
            <PlusCircle
              className="cursor-pointer"
              size={20}
              onClick={() => {
                handleClick({ roleId: roleId, userId: props.id });
              }}
            />
          </div>
        </div>
      </div>
      <hr />
    </div>
  );
};
class ModalUser extends Component {
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
      this.props.searchUserHasRole(searchText, this.props.role.userResponses);
    }, 500);
  }
  componentDidUpdate(prevProps, prevState) {
    if (this.props.dataSearchUser !== prevProps.dataSearchUser) {
      if (this.props.dataSearchUser !== prevState.data) {
        this.setState({
          data: this.props.dataSearchUser,
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
          <ModalHeader toggle={this.toggleModal}>Danh Sách Users</ModalHeader>
          <ModalBody>
            <h5>Nhập tên hoặc email hoặc username</h5>
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
                    addUserToRole={this.props.addUserToRole}
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

export default ModalUser;
