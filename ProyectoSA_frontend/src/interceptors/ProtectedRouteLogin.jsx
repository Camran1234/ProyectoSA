import { Navigate, Outlet } from "react-router-dom";
import Cookie from "cookie-universal";
import { useEffect } from "react";
import { HttpService } from "../Services/HttpService";
import { useNavigate } from "react-router-dom";



/**
 * {"user_type":{"idUserType":3,"type":"administrador"},
 * {"user_type":{"idUserType":2,"type":"agente"},
 * {"user_type":{"idUserType":1,"type":"cliente"},
 * @param {*} param0 
 * @returns 
 */
export const ProtectedRouteLogin = ({ redirectTo }) => {
    
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
    }
    return redirect();

    
};