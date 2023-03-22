import { createAsyncThunk } from '@reduxjs/toolkit';
import api from '../../api';
import { Product } from '../../types';

export const fetchAsyncProducts = createAsyncThunk(
  'products/fetchAsyncProducts',
  async () => {
    const response = await api.get<Product[]>('/products');
    return response.data;
  }
);
