export interface Product {
    id : string,
    name: string,
    price: number,
    image: string,
    categoryId: number
}

export interface SignupResponse {
  name: string;
  email: string;
}

export interface SignupRequest extends SignupResponse {
  password: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}
export interface LoginResponse {
  token: string;
  email: string;
}