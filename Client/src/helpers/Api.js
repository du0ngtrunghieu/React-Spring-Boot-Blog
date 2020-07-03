import axios from "axios";

export default axios.create({
  baseURL: `http://127.0.0.1:8080/api/v1`,
  timeout: 1000,
  headers: {
    "Content-Type": "application/json",
  },
});
