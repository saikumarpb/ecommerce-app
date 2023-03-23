import React, { Fragment } from 'react';
import './styles.css';
import { ReactComponent as ShoppingIcon } from '../../assets/icons/shopping.svg';
import { Button, ButtonGroup } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { AppDispatch, RootState } from '../../store';
import { useDispatch, useSelector } from 'react-redux';
import { logout } from '../../store/reducers/auth';

interface HeaderProps {
  signupHandler: () => void;
  loginHandler: () => void;
}
function Header({ signupHandler, loginHandler }: HeaderProps) {
  const isLoggedin = useSelector((store: RootState) => store.auth.isLoggedin);
  const dispatch = useDispatch<AppDispatch>();

  const featureNotImplemented = () => {
    alert('Feature not implemented');
  };

  const logoutHandler = () => {
    localStorage.removeItem('token');
    dispatch(logout());
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
    <Navbar bg="light" expand="lg" sticky="top">
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
            <Nav.Item onClick={featureNotImplemented}>
              <Nav.Link href="#link" eventKey="cart">
                Cart
              </Nav.Link>
            </Nav.Item>
            <Nav.Item onClick={featureNotImplemented}>
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
