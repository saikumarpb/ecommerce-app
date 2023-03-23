import {
  SignupResponse,
  SignupRequest,
  LoginResponse,
  LoginRequest,
} from './../../types/index';
import { createAsyncThunk } from '@reduxjs/toolkit';
import api from '../../api';

export const postAsyncSignup = createAsyncThunk(
  'auth/postAsyncSignup',
  async (signupRequest: SignupRequest) => {
    const response = await api.post<SignupResponse>(
      '/auth/signup',
      signupRequest
    );
    return response.data;
  }
);

export const postAsyncLogin = createAsyncThunk(
  'auth/postAsyncLogin',
  async (loginRequest: LoginRequest) => {
    const response = await api.post<LoginResponse>('/auth/login', loginRequest);
    return response.data;
  }
);
