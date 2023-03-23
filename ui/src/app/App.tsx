import React, { useState } from 'react';
import { Provider } from 'react-redux';
import { ToastContainer } from 'react-toastify';
import Header from '../components/Navbar';
import Products from '../pages/products';
import store from '../store';
import 'react-toastify/dist/ReactToastify.css';
import Signup from '../pages/signup';
import Login from '../pages/login';

function App() {
  const [showSignup, setShowSignup] = useState(false);
  const [showLogin, setShowLogin] = useState(false);

  const handleSignup = () => setShowSignup((prevState) => !prevState);
  const handleLogin = () => setShowLogin((prevState) => !prevState);

  return (
    <Provider store={store}>
      <div className="App">
        <Header signupHandler={handleSignup} loginHandler={handleLogin} />
        <Products />
        <ToastContainer />
        <Signup show={showSignup} handleClose={handleSignup} />
        <Login show={showLogin} handleClose={handleLogin} />
      </div>
    </Provider>
  );
}

export default App;
