import React, { useState } from 'react';
import { Button, Form, Offcanvas } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '../store';
import { postAsyncSignup } from '../store/actions/auth';
import { SignupRequest } from '../types';

interface ISignupProps {
  show: boolean;
  handleClose: () => void;
}

function Signup({ show, handleClose }: ISignupProps) {
  const initialFormState = {
    name: '',
    email: '',
    password: '',
  };

  const [userDetails, setUserDetails] =
    useState<SignupRequest>(initialFormState);
  const [isFormValidated, setIsFormValidated] = useState(false);

  const dispatch = useDispatch<AppDispatch>();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    const form = e.currentTarget;

    if (form.checkValidity() === false) {
      e.preventDefault();
      e.stopPropagation();
    } else {
      dispatch(postAsyncSignup(userDetails)).then(() => {
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
        <Form.Group className="mb-3" controlId="formName">
          <Form.Label>Name</Form.Label>
          <Form.Control
            name="name"
            type="text"
            onChange={handleFormInputChange}
            placeholder="Enter Full name"
            value={userDetails.name}
            required
          />
          <Form.Control.Feedback type="invalid">
            Name cannot be blank
          </Form.Control.Feedback>
        </Form.Group>

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
            onChange={handleFormInputChange}
            value={userDetails.password}
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
        <Offcanvas.Title>Signup</Offcanvas.Title>
      </Offcanvas.Header>
      <Offcanvas.Body>{renderSignup()}</Offcanvas.Body>
    </Offcanvas>
  );
}

export default Signup;
