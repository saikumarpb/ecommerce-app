import { configureStore } from '@reduxjs/toolkit';
import productSlice from './reducers/product';
import authSlice from './reducers/auth';

const store = configureStore({
  reducer: {
    products: productSlice,
    auth: authSlice,
  },
});

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;

export default store;
