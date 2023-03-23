import { postAsyncSignup, postAsyncLogin } from './../actions/auth';
import { createSlice } from '@reduxjs/toolkit';
import { IAuthSlice } from '../types';
import { showErrorToast, showSuccessToast } from '../../utils/toast';

const initialState: IAuthSlice = {
  email: '',
  isLoggedin: false,
  token: '',
  error: '',
};

const authSlice = createSlice({
  name: 'auth',
  initialState: initialState,
  reducers: {
    logout: (state) => {
      state.isLoggedin = false;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(postAsyncSignup.fulfilled, (_, action) => {
      showSuccessToast(
        `User registration successful with email ${action.payload.email}, Please login`,
        'signupSuccess'
      );
    });

    builder.addCase(postAsyncSignup.rejected, (state, action) => {
      const errMsg = 'User registration failed';
      state.error = errMsg;
      showErrorToast(errMsg, 'signupFailed');
    });

    builder.addCase(postAsyncLogin.fulfilled, (state, action) => {
      localStorage.setItem('token', action.payload.token);
      state.isLoggedin = true;
      showSuccessToast(`User logged in successfully`, 'loginSuccess');
    });

    builder.addCase(postAsyncLogin.rejected, (state, action) => {
      const errMsg = 'Login failed';
      state.error = errMsg;
      state.isLoggedin = false;
      showErrorToast(errMsg, 'loginFailed');
    });
  },
});

export const { logout } = authSlice.actions;

export default authSlice.reducer;
