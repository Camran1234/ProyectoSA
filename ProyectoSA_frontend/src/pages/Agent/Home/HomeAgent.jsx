import { useEffect, useState } from "react";

import { NavBar } from "../NavBar/NavBar";
import './home.css';
import fondoAgente from "../../../assets/fondo-agente.jpg";

import { HttpService } from "../../../Services/HttpService";
import { getToken } from "../../../Services/userHandler";
export const HomeAgent = () => {
    const [user, setUser] = useState("");

    useEffect(() => {
        document.title = "Agente";
        
        HttpService.postProtected('/api/user/get-user',{}, getToken())
        .then(response => {
            setUser(response.user);
        })
    }, [])

    return (
        <div style={{ backgroundImage: `url(${fondoAgente})`, backgroundSize: 'cover', backgroundRepeat: 'no-repeat', minHeight: '100vh' }}>
            <NavBar />            
            <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '25vh',
                fontFamily: 'Courier New'

            
            }}>
                <h1 style={{color:'black', fontFamily: 'Courier New, Courier, monospace'}}>Bienvenido {user}</h1>
            </div>
        </div >
    )
}