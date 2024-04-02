import React, { useEffect } from "react";
import LoginForm from "../Components/Login/LoginForm";
import { NavBar } from "../Components/NavBar/NavBar";

export const Login = () => {

    useEffect(() => {

        document.title = "Ticket System"
    }, [])

    return(
        <div style={{ backgroundColor: 'whitebone'}}>
            <NavBar />
            <LoginForm />
        </div>
    )
}