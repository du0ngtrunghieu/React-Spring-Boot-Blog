import React, { Component, useState, useEffect } from "react";
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from "reactstrap";
import { useDropzone } from "react-dropzone";
import "@/assets/scss/plugins/extensions/dropzone.scss";
function ProgrammaticallyDropzone(props) {
  const [files, setFiles] = useState([]);
  const { getRootProps, getInputProps, open } = useDropzone({
    accept: "image/*",
    noClick: true,
    noKeyboard: true,
    onDrop: (acceptedFiles) => {
      setFiles(
        acceptedFiles.map((file) =>
          Object.assign(file, {
            preview: URL.createObjectURL(file),
          })
        )
      );
      props.addFileItem(acceptedFiles);
    },
  });

  const thumbs = files.map((file) => (
    <div className="dz-thumb" key={file.name}>
      <div className="dz-thumb-inner">
        <img src={file.preview} className="dz-img" alt={file.name} />
      </div>
    </div>
  ));

  useEffect(
    () => () => {
      // Make sure to revoke the data uris to avoid memory leaks
      files.forEach((file) => URL.revokeObjectURL(file.preview));
    },
    [files]
  );

  return (
    <section>
      <div {...getRootProps({ className: "dropzone" })}>
        <input {...getInputProps()} />
        <p className="mx-1">
          Kéo hoặc thả một số tệp vào đây hoặc nhấp để chọn tệp
        </p>
      </div>
      <Button.Ripple color="primary" outline className="my-1" onClick={open}>
        Open File Dialog
      </Button.Ripple>
      <aside className="thumb-container">{thumbs}</aside>
    </section>
  );
}

class ModalUpload extends Component {
  state = {
    activeTab: "1",
    modal: false,
    files1: [],
  };
  addFileItem = (fileItem) => {
    this.setState(({ files }) => ({ files: { ...files, fileItem } }));
  };
  handleUpload = (files) => {
    const formData = new FormData();
    formData.append("file", files.fileItem[0]);
    let dataSend = {
      file: files.fileItem[0],
      parent: this.props.folder.id,
      rootDirName: this.props.folder.pathReal.split("/").join("%2F"),
      folderInfo: this.props.folder,
    };
    this.props.uploadFile(dataSend, formData);
  };
  toggleModal = () => {
    this.setState((prevState) => ({
      modal: !prevState.modal,
    }));
  };

  render() {
    return (
      <div>
        <Modal
          isOpen={this.state.modal}
          toggle={this.toggleModal}
          className={this.props.className}
          scrollable={true}
        >
          <ModalHeader toggle={this.toggleModal}>Upload File</ModalHeader>
          <ModalBody>
            <ProgrammaticallyDropzone addFileItem={this.addFileItem} />
          </ModalBody>
          <ModalFooter>
            <Button
              color="primary"
              onClick={() => this.handleUpload(this.state.files)}
            >
              Upload
            </Button>{" "}
            <Button.Ripple color="danger" outline onClick={this.toggleModal}>
              Huỷ
            </Button.Ripple>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}

export default ModalUpload;
