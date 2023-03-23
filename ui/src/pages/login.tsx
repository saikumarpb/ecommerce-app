import React, { useState } from 'react';
import { Button, Form, Offcanvas } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '../store';
import { postAsyncLogin } from '../store/actions/auth';
import { LoginRequest } from '../types';

interface ILoginProps {
  show: boolean;
  handleClose: () => void;
}

function Login({ show, handleClose }: ILoginProps) {
  const initialFormState = {
    email: '',
    password: '',
  };

  const [isFormValidated, setIsFormValidated] = useState(false);
  const [userDetails, setUserDetails] =
    useState<LoginRequest>(initialFormState);

  const dispatch = useDispatch<AppDispatch>();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    const form = e.currentTarget;
    if (form.checkValidity() === false) {
      e.stopPropagation();
      e.preventDefault();
    } else {
      dispatch(postAsyncLogin(userDetails)).then(() => {
        setUserDetails(initialFormState);
        setIsFormValidated(false);
      });
    }
    setIsFormValidated(true);
    e.preventDefault();
  };

  const handleFormInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setUserDetails((prevValues) => {
      return { ...prevValues, [name]: value };
    });
  };

  const renderSignup = () => (
    <div className="p-4">
      <Form noValidate validated={isFormValidated} onSubmit={handleSubmit}>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            name="email"
            type="email"
            value={userDetails.email}
            onChange={handleFormInputChange}
            placeholder="Enter email"
            required
          />
          <Form.Control.Feedback type="invalid">
            Enter valid email address
          </Form.Control.Feedback>
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            name="password"
            type="password"
            value={userDetails.password}
            onChange={handleFormInputChange}
            placeholder="Password"
            required
          />
          <Form.Control.Feedback type="invalid">
            Password cannot be blank
          </Form.Control.Feedback>
        </Form.Group>

        <Button variant="primary" type="submit" className="w-100">
          Submit
        </Button>
      </Form>
    </div>
  );

  return (
    <Offcanvas show={show} onHide={handleClose}>
      <Offcanvas.Header closeButton>
        <Offcanvas.Title>Login</Offcanvas.Title>
      </Offcanvas.Header>
      <Offcanvas.Body>{renderSignup()}</Offcanvas.Body>
    </Offcanvas>
  );
}

export default Login;
