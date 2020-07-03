// Import React as usual
import React from "react";

// Import Chonky
import "chonky/style/main.css";
import { FileBrowser, FileView } from "chonky";
import { success, error } from "@/helpers/ToastifyHelper";
import { Card, CardBody, Input, Row, Col, FormGroup } from "reactstrap";
import { connect } from "react-redux";
import classnames from "classnames";
import "../../../assets/scss/pages/data-list.scss";
import {
  getAllFile,
  uploadFile,
  deleteFile,
  createFolder,
  uploadMultipeFile,
  setUploadFile,
  refreshUploadFile,
} from "../../../redux/actions/media/Media";
import ModalUpload from "./ModalUpload";
import { FilePlus, FolderPlus } from "react-feather";
import SideBarUpload from "./SideBarUpload";
import { boolean } from "yup";

class MediaLib extends React.Component {
  state = {
    currentFolderId: "uploads",
    fileMap: [],
    addNew: false,
    nameFolder: "",
    sidebar: false,
    fileProgress: [],
    listOldFile: [],
  };
  componentDidMount() {
    this.props.getAllFile();
  }
  componentDidUpdate(prevProps, prevState) {
    if (this.props.data !== prevProps.data) {
      if (this.props.data !== prevState.fileMap) {
        this.setState({
          fileMap: this.props.data,
        });
      }
    }
    if (this.props.fileProgress !== prevProps.fileProgress) {
      if (this.props.fileProgress !== prevState.fileProgress) {
        this.setState({
          fileProgress: this.props.fileProgress,
        });
      }
    }
    if (this.props.listOldFile !== prevProps.listOldFile) {
      if (this.props.listOldFile !== prevState.listOldFile) {
        this.setState({
          listOldFile: this.props.listOldFile,
        });
      }
    }
  }
  triggerToggle = () => {
    this.modal.toggleModal();
  };
  handleFileOpen = (file) => {
    if (file.isDir) {
      this.setState({ currentFolderId: file.id });
    } else {
      const type = file.isDir ? "folder" : "file";
      const text = `You tried to open a ${type}: ${file.name}`;

      success(text, {
        position: "top-right",
      });
    }
  };

  handleFolderCreate = () => {
    this.setState({
      addNew: !this.state.addNew,
    });
  };

  handleDeleteFiles = (files, inputEvent) => {
    this.props.deleteFile(files);
  };
  handleUploadFiles = () => {
    this.setState({ sidebar: true });
    // this.triggerToggle();
  };

  thumbGenerator = (file) => {
    if (!file.thumbnailUrl) return null;
    return new Promise((resolve, reject) => {
      const image = new Image();
      image.onload = () => resolve(file.thumbnailUrl);
      image.onerror = () =>
        reject(`Failed to load thumbnail for ${file.name}.`);
      image.src = file.thumbnailUrl;
    }).catch((error) => console.error(error));
  };
  handleSubmitCreateFolder = (event, folder) => {
    if (event.keyCode === 13) {
      this.props.createFolder(folder, this.state.nameFolder);
      this.setState({
        nameFolder: "",
        addNew: !this.state.addNew,
      });
    }
  };
  handleSidebar = (boolean) => {
    !boolean ? this.props.refreshUploadFile() : "";
    this.setState({ sidebar: boolean });
  };
  render() {
    const {
      currentFolderId,
      addNew,
      sidebar,
      fileProgress,
      listOldFile,
    } = this.state;
    const folder = this.state.fileMap[currentFolderId];

    const folderChain = [];
    let files = [];
    if (folder) {
      let currentFolder = folder;
      while (currentFolder) {
        folderChain.unshift(currentFolder);
        const parentId = currentFolder.parentId;
        currentFolder = parentId ? this.state.fileMap[parentId] : null;
      }
      if (folder.childrenIds) {
        files = folder.childrenIds.map((id) => this.state.fileMap[id]);
      }
    }

    return (
      <div className="data-list list-view">
        <Card>
          <CardBody>
            <Row>
              <Col sm="8"></Col>
              <Col sm="4">
                <FormGroup className="position-relative has-icon-left">
                  <Input
                    type="text"
                    placeholder="Nhấn Enter để thêm mới folder"
                    onChange={(e) => {
                      this.setState({
                        nameFolder: e.target.value,
                      });
                    }}
                    onKeyDown={(e) => this.handleSubmitCreateFolder(e, folder)}
                  />
                  <div className="form-control-position">
                    <FolderPlus size={15} />
                  </div>
                </FormGroup>
              </Col>
              <Col sm="12" style={{ height: 540 }}>
                <FileBrowser
                  files={files}
                  folderChain={folderChain}
                  thumbnailGenerator={this.thumbGenerator}
                  onFileOpen={this.handleFileOpen}
                  onDownloadFiles={null}
                  onDeleteFiles={this.handleDeleteFiles}
                  fillParentContainer={true}
                  view={FileView.SmallThumbs}
                  onUploadClick={this.handleUploadFiles}
                />
              </Col>
            </Row>
          </CardBody>
          {/* <ModalUpload
            ref={(modal) => (this.modal = modal)}
            {...this.props}
            folder={folder}
          /> */}
        </Card>

        <SideBarUpload
          show={sidebar}
          handleSidebar={this.handleSidebar}
          {...this.props}
          folder={folder}
          fileProgress={fileProgress}
          listOldFile={listOldFile}
        />
        <div
          className={classnames("data-list-overlay", {
            show: sidebar,
          })}
          onClick={() => {
            this.handleSidebar(false);
          }}
        />
      </div>
    );
  }
}
const mapStateToProps = (state) => ({
  data: state.media.data,
  fileProgress: state.media.fileProgress,
  listOldFile: state.media.listOldFile,
});

export default connect(mapStateToProps, {
  getAllFile,
  uploadFile,
  deleteFile,
  createFolder,
  uploadMultipeFile,
  setUploadFile,
  refreshUploadFile,
})(MediaLib);
