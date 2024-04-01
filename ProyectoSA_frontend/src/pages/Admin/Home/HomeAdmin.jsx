import { useEffect, useState } from "react";

import { NavBar } from "../NavBar/NavBar";
import './homeAdmin.css';
import { HttpService } from "../../../Services/HttpService";
import { getToken } from "../../../Services/userHandler";

export const HomeAdmin = () => {
    
    const [user, setUser] = useState("");

    useEffect(() => {
        document.title = "Adminsitrador";
        
        HttpService.postProtected('/api/user/get-user',{}, getToken())
        .then(response => {
            setUser(response.user);
        })
    }, [])

    return (
        <div className="adminContainer">
            <NavBar />            
            <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '25vh',
                fontFamily: 'Courier New, Courier, monospace',                
            
            }}>
                <h1 style={{color:'black'}}>Bienvenido {user}</h1>
            </div>
        </div >
    )
}