import { useState } from "react";
import { createContext } from "react";
import { getUser } from "../Services/userHandler";

//const TOKEN_DEFAULT = import.meta.env.VITE_TOKEN;

export const sesionContext = createContext();

const defaultUser = {
  idUser: 1,
  username: "admin1234",
  name: "Vincent",
  lastName: "Valentine",
  phone: "00000000",
  userType: "3",
  token: ""
};

//UserType
/*
1 = cliente
2 = agente
3 = administrador
*/
export function SesionProvider({ children }) {
  const [user, setUser] = useState(defaultUser);

  const updateUser = ({ user, userType, token }) => {
    console.log("Sesion",user,userType,token)
    // Implementar el fetch de login
    return getUser({ data: user,token })
      .then((newUser) => setUser({...newUser, rol:userType,token}));
    // Actualizar el estado del usuario
    // Guardar el token también
  };
  //Roles
  
  const isAdmin = () => user.rol == "admin";
  const isAgent = () => user.rol == "agent";

  return (
    <sesionContext.Provider value={{ user, setUser, updateUser, isAdmin, isAgent }}>
      {children}
    </sesionContext.Provider>
  );
}