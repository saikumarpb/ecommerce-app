import React, { Fragment, useState } from "react";
import "./styles.css";
import { ReactComponent as ShoppingIcon } from "../../assets/icons/shopping.svg";
import { Button, ButtonGroup } from "react-bootstrap";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";

function Header() {
  const [isLoggedin] = useState(false);

  const logoutHandler = () => {
    alert("Feature not implemented");
  };

  const signupHandler = () => {
    alert("Feature not implemented");
  };

  const loginHandler = () => {
    alert("Feature not implemented");
  };

  const UserAuthSection = () => (
    <Fragment>
      {isLoggedin ? (
        <Button
          variant="outline-dark"
          onClick={logoutHandler}
          className="logout-section border-0"
        >
          <span>Logout</span>
        </Button>
      ) : (
        <ButtonGroup aria-label="Basic example">
          <Button
            variant="outline-dark"
            onClick={signupHandler}
            className="border-0"
          >
            <span>Sign Up</span>
          </Button>
          <Button
            variant="outline-dark"
            onClick={loginHandler}
            className="border-0"
          >
            <span>Login</span>
          </Button>
        </ButtonGroup>
      )}
    </Fragment>
  );

  return (
    <Navbar bg="light" expand="lg" fixed="top">
      <Container>
        <Navbar.Brand href="/" className="d-flex gap-2">
          <ShoppingIcon width="30px" height="30px" className="shopping-icon" />
          <span className="title">Eshoppers</span>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse
          id="basic-navbar-nav"
          className="justify-content-between"
        >
          <Nav defaultActiveKey="products">
            <Nav.Item>
              <Nav.Link href="#link" eventKey="products">
                Products
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link href="#link" eventKey="cart">
                Cart
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link href="#link" eventKey="orders">
                Orders
              </Nav.Link>
            </Nav.Item>
          </Nav>

          <Nav className="user-auth-section">
            <UserAuthSection />
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Header;
