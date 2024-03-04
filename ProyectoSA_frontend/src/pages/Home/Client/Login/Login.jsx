import React from "react";

import secondImg from "../../../../assets/second_body_background.jpg";

import LoginForm from "../../Components/Login/LoginForm";
import { NavBar } from "../../Components/NavBar/NavBar";

export const Login = () => {

    

    return(
        <div style={{ backgroundImage: `url(${secondImg})`, 
                    backgroundSize: 'cover', backgroundRepeat:'repeat',
                    minHeight: '100vh', maxHeight:'max-content', overflowY: 'hidden' }}>
            <NavBar />
            <LoginForm />
        </div>
    )
}