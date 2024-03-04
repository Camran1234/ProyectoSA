import { userInfo } from "../Adapters/userInfo";

const API_BACKEND = import.meta.env.VITE_API;

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