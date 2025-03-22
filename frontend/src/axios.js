// axios cros origin 설정
import axios from "axios";
let config = {
  baseURL: "/api",
  timeout: 600000,  // 10분으로 변경
  withCredentials: true,
};
const axiosInst = axios.create(config);

export default axiosInst;

//baseURL을 통해 /student요청 시 /api/student로 요청이 되기 때문에, 프록시 서버로 요청이 됌.
//프록시 서버는 요청을 받아서 요청을 전달하고, 응답을 받아서 응답을 전달함.
