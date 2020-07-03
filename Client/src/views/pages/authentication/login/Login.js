import React from "react";
import {
  Button,
  Card,
  CardBody,
  Row,
  Col,
  Form,
  FormGroup,
  Input,
  Label,
  Spinner,
  UncontrolledAlert,
} from "reactstrap";
import { Link } from "react-router-dom";
import { Mail, Lock, Check, Facebook, Twitter, GitHub } from "react-feather";
import Checkbox from "../../../../components/@vuexy/checkbox/CheckboxesVuexy";
import googleSvg from "../../../../assets/img/svg/google.svg";

import loginImg from "../../../../assets/img/pages/login.png";
import "../../../../assets/scss/pages/authentication.scss";
import { connect } from "react-redux";
import { userSignIn } from "../../../../redux/actions/auth/Auth";
import { history } from "../../../../history";

class Login extends React.Component {
  state = {
    email: "",
    password: "",
  };
  onSubmitLogin = (e) => {
    e.preventDefault();
    this.props.userSignIn(this.state);
  };

  render() {
    return (
      <Row className="m-0 justify-content-center">
        <Col
          sm="8"
          xl="7"
          lg="10"
          md="8"
          className="d-flex justify-content-center"
        >
          <Card className="bg-authentication login-card rounded-0 mb-0 w-100">
            <Row className="m-0">
              <Col
                lg="6"
                className="d-lg-block d-none text-center align-self-center px-1 py-0"
              >
                <img src={loginImg} alt="loginImg" />
              </Col>
              <Col lg="6" md="12" className="p-0">
                <Card className="rounded-0 mb-0 px-2">
                  <CardBody>
                    <h4 className="mb-2">Đăng Nhập</h4>
                    <small>
                      {this.props.commonData.error ? (
                        <UncontrolledAlert color="danger">
                          {this.props.commonData.error}
                        </UncontrolledAlert>
                      ) : (
                        ""
                      )}
                    </small>

                    <p className="mb-2"></p>
                    <Form onSubmit={this.onSubmitLogin}>
                      <FormGroup className="form-label-group position-relative has-icon-left">
                        <Input
                          type="email"
                          placeholder="Email"
                          value={this.state.email}
                          onChange={(e) =>
                            this.setState({ email: e.target.value })
                          }
                        />
                        <div className="form-control-position">
                          <Mail size={15} />
                        </div>
                        <Label>Email</Label>
                      </FormGroup>
                      <FormGroup className="form-label-group position-relative has-icon-left">
                        <Input
                          type="password"
                          placeholder="Password"
                          value={this.state.password}
                          onChange={(e) =>
                            this.setState({ password: e.target.value })
                          }
                        />
                        <div className="form-control-position">
                          <Lock size={15} />
                        </div>
                        <Label>Password</Label>
                      </FormGroup>
                      <FormGroup className="d-flex justify-content-between align-items-center">
                        <Checkbox
                          color="primary"
                          icon={<Check className="vx-icon" size={16} />}
                          label="Remember me"
                        />
                        <div className="float-right">
                          <Link to="/pages/forgot-password">
                            Forgot Password?
                          </Link>
                        </div>
                      </FormGroup>
                      <div className="d-flex justify-content-between">
                        <Button.Ripple
                          color="primary"
                          outline
                          onClick={() => {
                            history.push("/pages/register");
                          }}
                        >
                          Register
                        </Button.Ripple>

                        {this.props.commonData.loading ? (
                          <Button.Ripple color="primary" disabled>
                            <Spinner color="danger" size="sm" />
                            <span className="ml-50">Loading...</span>
                          </Button.Ripple>
                        ) : (
                          <Button.Ripple color="primary" type="submit">
                            Login
                          </Button.Ripple>
                        )}
                      </div>
                    </Form>
                  </CardBody>
                  <div className="auth-footer">
                    <div className="divider">
                      <div className="divider-text">OR</div>
                    </div>
                    <div className="footer-btn">
                      <Button.Ripple className="btn-facebook" color="">
                        <Facebook size={14} />
                      </Button.Ripple>
                      <Button.Ripple className="btn-twitter" color="">
                        <Twitter size={14} stroke="white" />
                      </Button.Ripple>
                      <Button.Ripple className="btn-google" color="">
                        <img
                          src={googleSvg}
                          alt="google"
                          height="15"
                          width="15"
                        />
                      </Button.Ripple>
                      <Button.Ripple className="btn-github" color="">
                        <GitHub size={14} stroke="white" />
                      </Button.Ripple>
                    </div>
                  </div>
                </Card>
              </Col>
            </Row>
          </Card>
        </Col>
      </Row>
    );
  }
}
const mapStateToProps = (state) => {
  return {
    auth: state.auth,
    commonData: state.commonData,
  };
};

export default connect(mapStateToProps, {
  userSignIn,
})(Login);