import { Navigate, Outlet, useNavigate } from "react-router-dom";

import Cookie from "cookie-universal";
import { useEffect } from "react";
import { HttpService } from "../Services/HttpService";


/**
 * The allowed Roles are as follows:
 * 1: Cliente
 * 2: Agente
 * 3: Administrador
 * @param {*} param0 
 * @returns 
 */
const PrivateRoute = ({ redirectTo, allowedRoles }) => {
  const navigate = useNavigate();

  useEffect(() => {
    const cookies = Cookie();
    const crr_user = cookies.get("crr_user");
    if(crr_user !== undefined){
        const jwt = crr_user.jwt;
        HttpService.postProtected("/api/user/validate", {}, jwt)
            .catch((error) => {
               //console.log("Error", error);
                cookies.remove("crr_user");
                const navigate = useNavigate();
                navigate(redirectTo);
            });
    }
  }, []);

  const redirect = () => {
    const cookies = Cookie();
    const crr_user = cookies.get("crr_user");

    //console.log("Cookie", crr_user);
    if (crr_user !== undefined) {
      const userRole = crr_user.user_type.idUserType;
      if (allowedRoles.includes(userRole)) {
        return <Outlet />;
      }
    }

    //console.error("ERR User Redirecting to", redirectTo);
    return <Navigate to={redirectTo} />;
  };
  return redirect();
  
};

export default PrivateRoute;