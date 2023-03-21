import axios from "axios";
import { Product } from "../types";

// TODO : move to axios setup file
axios.defaults.baseURL = "http://127.0.0.1:8080";

export async function getProducts() {
  return await axios.get<Product[]>("/api/products");
}
