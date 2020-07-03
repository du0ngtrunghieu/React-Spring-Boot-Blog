import React, { useState, useEffect } from "react";
import {
  Card,
  CardHeader,
  CardBody,
  CardTitle,
  Button,
  Spinner,
} from "reactstrap";
import { useDropzone } from "react-dropzone";
import "@/assets/scss/plugins/extensions/dropzone.scss";
import classnames from "classnames";
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

class FormUploadMedia extends React.Component {
  state = {
    files: [],
    isLoading: false,
  };
  handleUpload = () => {
    this.setState({
      isLoading: true,
    });
  };

  render() {
    let { isLoading } = this.state;
    return (
      <Card
        className={classnames("card-reload card-action", {
          refreshing: isLoading,
        })}
      >
        <CardHeader>
          <CardTitle>Vui Lòng Chọn File Upload</CardTitle>
        </CardHeader>
        <CardBody>
          {isLoading ? (
            <Spinner color="primary" className="reload-spinner" />
          ) : (
            ""
          )}
          <ProgrammaticallyDropzone />
        </CardBody>
      </Card>
    );
  }
}

export default FormUploadMedia;
