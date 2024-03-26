import { userInfo } from "../Adapters/userInfo";

const API_BACKEND = import.meta.env.VITE_API;
import Cookie from "cookie-universal";


export function getUser({ data,token }) {
  return fetch(`${API_BACKEND}/api/user/getByUser?user=${data}`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
    },
  })
    .then((res) => res.json())
    .then(data => userInfo([data])[0]) // Pasar como array
    .catch((er) => console.log(er));
}


export function getToken() {
  const cookies = Cookie();
  const crr_user = cookies.get("crr_user");
    if(crr_user !== undefined){
        const jwt = crr_user.jwt;
        return jwt;
    }
    return null;
}