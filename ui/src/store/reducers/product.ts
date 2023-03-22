import { createSlice } from '@reduxjs/toolkit';
import { IProductSlice } from '../types';
import { fetchAsyncProducts } from '../actions/product';

const initialState: IProductSlice = {
  products: [],
  loading: false,
  error: '',
};

const productSlice = createSlice({
  name: 'products',
  initialState: initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(fetchAsyncProducts.pending, (state) => {
      state.loading = true;
    });

    builder.addCase(fetchAsyncProducts.fulfilled, (state, action) => {
      state.loading = false;
      state.products = action.payload;
    });

    builder.addCase(fetchAsyncProducts.rejected, (state, action) => {
      state.loading = false;
      state.error = action.error.message || 'Prodcts fetch failed';
    });
  },
});

export default productSlice.reducer;
