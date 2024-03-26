import Cookie from "cookie-universal";

export const getUserType = () => {
    const cookies = Cookie();
    const crr_user = cookies.get("crr_user");
    if (crr_user === undefined) {
        return "cliente";
    } else {
        return crr_user.user_type.type;
    }
}

export const removeSession = () => {
    const cookies = Cookie();
    cookies.remove("crr_user");
    window.location.href = "/login";
}