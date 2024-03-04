import { Navigate, Outlet } from "react-router-dom";

import Cookie from "cookie-universal";

const PrivateRoute = ({ redirectTo, allowedRoles }) => {
  const cookies = Cookie();
  const crr_user = cookies.get("crr_user");

  console.log("Cookie", crr_user);
  if (crr_user !== undefined) {
    const userRole = crr_user.user_type.idUserType;
    if (allowedRoles.includes(userRole)) {
      return <Outlet />;
    }
  }

  console.error("ERR User Redirecting to", redirectTo);
  return <Navigate to={redirectTo} />;
};

export default PrivateRoute;