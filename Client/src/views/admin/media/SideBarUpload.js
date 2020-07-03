import React, { Component } from "react";
import {
  Button,
  TabContent,
  TabPane,
  Nav,
  NavItem,
  NavLink,
  ListGroupItem,
  ListGroup,
  Row,
  Col,
  Progress,
} from "reactstrap";
import { X } from "react-feather";
import PerfectScrollbar from "react-perfect-scrollbar";
import classnames from "classnames";
import Dropzone from "react-dropzone";
import "@/assets/scss/plugins/extensions/dropzone.scss";
import prettyNumber from "@/helpers/FileSizeHelper";

const ListFileItems = (file) => {
  let f = file.file;
  return (
    <ListGroupItem key={f.id}>
      <Row>
        <Col md="12">
          <div className="text-bold-500">
            {f.file.name}
            {"   "}
            <small>{prettyNumber(f.file.size, "si")}</small>
          </div>
        </Col>
        <Col md="12">
          <Progress
            className="progress-lg"
            value={f.progress}
            color={f.progress === 100 ? "success" : "danger"}
          >
            {f.progress !== 100 ? `${f.progress}%` : "Tải lên hoàn tất"}
          </Progress>
        </Col>
      </Row>
    </ListGroupItem>
  );
};
class SideBarUpload extends Component {
  state = {
    active: "1",
    oldFile: [],
  };
  onDrop = (acceptedFiles, rejectedFiles, e) => {
    this.props.setUploadFile(acceptedFiles);
    this.props.fileProgress
      ? Object.values(this.props.fileProgress)
          .filter((x) => !this.props.listOldFile.includes(x.id))
          .map((file) => {
            const formData = new FormData();
            formData.append("file", file.file);
            let id = file.id;
            let dataSend = {
              id: id,
              parent: this.props.folder.id,
              rootDirName: this.props.folder.pathReal.split("/").join("%2F"),
              folderInfo: this.props.folder,
            };
            this.props.uploadFile(dataSend, formData);
          })
      : null;
  };
  toggle = (tab) => {
    if (this.state.active !== tab) {
      this.setState({ active: tab });
    }
  };
  render() {
    let { show, fileProgress } = this.props;
    return (
      <div
        className={classnames("data-list-sidebar", {
          show: show,
        })}
      >
        <div className="data-list-sidebar-header mt-2 px-2 d-flex justify-content-between">
          <h4>Upload Files</h4>
          <X size={20} onClick={() => this.props.handleSidebar(false)} />
        </div>
        <PerfectScrollbar
          className="data-list-fields px-2 mt-3"
          options={{ wheelPropagation: false }}
        >
          <Nav tabs>
            <NavItem>
              <NavLink
                className={classnames({
                  active: this.state.active === "1",
                })}
                onClick={() => {
                  this.toggle("1");
                }}
              >
                General
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink
                className={classnames({
                  active: this.state.active === "2",
                })}
                onClick={() => {
                  this.toggle("2");
                }}
              >
                Help
              </NavLink>
            </NavItem>
          </Nav>
          <TabContent activeTab={this.state.active}>
            <TabPane tabId="1">
              <section>
                <Dropzone onDrop={this.onDrop}>
                  {({
                    getRootProps,
                    getInputProps,
                    isDragActive,
                    isDragReject,
                  }) => (
                    <div {...getRootProps({ className: "dropzone" })}>
                      <input {...getInputProps()} />
                      <p className="mx-1">
                        {!isDragActive &&
                          "Kéo hoặc thả một số tệp vào đây hoặc nhấp để chọn tệp!"}
                        {isDragActive &&
                          !isDragReject &&
                          "Drop it like it's hot!"}
                        {isDragReject && "File type not accepted, sorry!"}
                      </p>
                    </div>
                  )}
                </Dropzone>
                <ListGroup flush>
                  {fileProgress &&
                    Object.values(fileProgress).map((file) => (
                      <ListFileItems key={file.id} file={file} />
                    ))}
                </ListGroup>
              </section>
            </TabPane>
            <TabPane tabId="2">
              Chỉ cho phép mấy file dạng như pdf , word , images,xlx..
            </TabPane>
          </TabContent>
        </PerfectScrollbar>
        <div className="data-list-sidebar-footer px-2 d-flex justify-content-start align-items-center mt-1">
          <Button
            className="ml-1"
            color="danger"
            outline
            onClick={() => this.props.handleSidebar(false)}
          >
            Cancel
          </Button>
        </div>
      </div>
    );
  }
}

export default SideBarUpload;
