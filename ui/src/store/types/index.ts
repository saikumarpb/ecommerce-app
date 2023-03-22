import { Product } from '../../types';

export interface IProductSlice {
  products: Product[];
  loading: boolean;
  error: string;
}
