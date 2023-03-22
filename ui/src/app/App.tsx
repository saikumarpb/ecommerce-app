import React from "react";
import { Provider } from 'react-redux';
import Header from "../components/Navbar";
import Products from "../pages/products";
import store from '../store';

function App() {
  return (
    <Provider store={store}>
      <div className="App">
        <Header />
        <Products />
      </div>
    </Provider>
  );
}

export default App;
