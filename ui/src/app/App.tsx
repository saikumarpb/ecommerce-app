import React from "react";
import { Provider } from 'react-redux';
import { ToastContainer } from 'react-toastify';
import Header from '../components/Navbar';
import Products from '../pages/products';
import store from '../store';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
    <Provider store={store}>
      <div className="App">
        <Header />
        <Products />
        <ToastContainer />
      </div>
    </Provider>
  );
}

export default App;
