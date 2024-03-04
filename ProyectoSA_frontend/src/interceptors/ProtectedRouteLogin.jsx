import { Navigate, Outlet } from "react-router-dom";
import Cookie from "cookie-universal";




/**
 * {"user_type":{"idUserType":3,"type":"administrador"},
 * {"user_type":{"idUserType":2,"type":"agente"},
 * {"user_type":{"idUserType":1,"type":"cliente"},
 * @param {*} param0 
 * @returns 
 */
export const ProtectedRouteLogin = ({ redirectTo }) => {




    const cookies = Cookie();
    const crr_user = cookies.get("crr_user");
    console.log("Cookie", crr_user);
    if (crr_user === undefined) {
        return <Outlet />;
    } else {
        if (crr_user.user_type.idUserType == 1) {
        redirectTo = "/";
        } else if (crr_user.user_type.idUserType == 2) {
        redirectTo = "/agente";
        }else if (crr_user.user_type.idUserType == 3) {
            redirectTo = "/admin";
        }else{
            redirectTo = "/login";
        }
        console.log("ERR Login Redirecting to", redirectTo);
        return <Navigate to={redirectTo} />;
    }
};