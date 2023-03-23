import { Product } from '../../types';

export interface IProductSlice {
  products: Product[];
  loading: boolean;
  error: string;
}

export interface IAuthSlice {
  token: string;
  email: string;
  isLoggedin: boolean;
  error: string;
}
