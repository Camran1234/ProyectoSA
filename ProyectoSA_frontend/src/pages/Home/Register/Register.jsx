import React, { useEffect } from "react";
import { NavBar } from "../Components/NavBar/NavBar";
import FormClientRegister from "../Components/Register/FormRegister";

export const Register = () => {

    useEffect(() => {

        document.title = "Ticket System"
    }, [])

    return(
        <div style={{ backgroundColor: 'whitebone', overflowY: 'hidden'}}>
            <NavBar />
            <FormClientRegister />
        </div>
    )
}