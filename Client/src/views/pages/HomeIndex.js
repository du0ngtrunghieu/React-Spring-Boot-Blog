import React, { Component } from "react";
import { connect } from "react-redux";
import { changeNavbarType } from "../../redux/actions/customizer";
import overlayImg1 from "@/assets/img/pages/card-image-6.jpg";
import {
  Row,
  Col,
  Card,
  CardImg,
  CardImgOverlay,
  CardTitle,
  CardBody,
  Badge,
} from "reactstrap";
import { Link } from "react-router-dom";
import "@/assets/scss/pages/app-ecommerce-shop.scss";
import { Star, X, ShoppingCart } from "react-feather";
class HomeIndex extends Component {
  render() {
    return (
      <Row>
        <Col lg="4" md="6" sm="12">
          <Card className="text-white overlay-img-card">
            <CardImg src={overlayImg1} alt="overlay img" />
            <CardImgOverlay className="overlay-black d-flex flex-column justify-content-between">
              <CardTitle className="text-white">Beautiful Overlay</CardTitle>
              <p>
                Cake sesame snaps cupcake gingerbread danish I love gingerbread.
                Apple pie pie jujubes chupa chups muffin halvah lollipop.
              </p>
            </CardImgOverlay>
          </Card>
        </Col>
        <Col lg="12" md="6" sm="12">
          <div className="ecommerce-application">
            <div className="grid-view wishlist-items">
              {" "}
              <Card className={`ecommerce-card `}>
                <div className="card-content">
                  <div className="item-img text-center">
                    <Link to="/ecommerce/product-detail">
                      <img className="img-fluid" src={overlayImg1} />
                    </Link>
                  </div>
                  <CardBody>
                    <div className="item-wrapper">
                      <div className="item-rating">
                        <Badge color="primary" className="badge-md">
                          <span className="mr-50 align-middle">4</span>
                          <Star size={14} />
                        </Badge>
                      </div>
                      <div className="product-price">
                        <h6 className="item-price">32323 </h6>
                      </div>
                    </div>
                    <div className="item-name">
                      <Link to="/ecommerce/product-detail">
                        <span>fasdf ádf ádf ád fasTiees u đề</span>
                      </Link>
                      <p className="item-company">
                        By <span className="company-name">Tác giả</span>
                      </p>
                    </div>
                    <div className="item-desc">
                      <p className="item-description">fasdf ádf ádf a</p>
                    </div>
                  </CardBody>
                  <div className="item-options text-center">
                    <div className="item-wrapper">
                      <div className="item-rating">
                        <Badge color="primary" className="badge-md">
                          <span className="mr-50 align-middle">4</span>
                          <Star size={14} />
                        </Badge>
                      </div>
                      <div className="product-price">
                        <h6 className="item-price">fasdfas dfasd f</h6>
                      </div>
                    </div>
                    <div className="wishlist">
                      <X size={15} />
                      <span className="align-middle ml-50">Remove</span>
                    </div>
                    <div className="cart">
                      <ShoppingCart size={15} />
                      <span className="align-middle ml-50">Move To Cart</span>
                    </div>
                  </div>
                </div>
              </Card>
            </div>
          </div>
        </Col>
      </Row>
    );
  }
}

export default connect(null, {
  changeNavbarType,
})(HomeIndex);
