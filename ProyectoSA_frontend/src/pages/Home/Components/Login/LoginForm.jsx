import React, { useState } from "react";
import { Card, Button, Form } from "react-bootstrap";
import './loginForm.css';
import { HttpService } from "../../../../Services/HttpService";
import { showErrorMessage } from "../../../../components/Alerts/SweetAlertComponent";
import { useNavigate } from "react-router-dom";
import Cookie from 'cookie-universal';
const LoginForm = () => {
    const cookies = Cookie()
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const handleSubmit = (e) => {
        e.preventDefault();
        
        HttpService.post("/api/user/login", {username: username, password: password})
        .then((response)=>{
            //alert(JSON.stringify(response));            
            cookies.set("crr_user", {...response,user:username}, { path: "/" });
            /**
             * {"user_type":{"idUserType":3,"type":"administrador"},
             * "jwt":"here goes token"}
             */
            const userRole = response.user_type.idUserType;
            if(userRole === 1){
                navigate("/");
            }else if(userRole === 2){
                navigate("/agente");
            }else if(userRole === 3){
                navigate("/admin");
            }
        })
        .catch((error) => {
            showErrorMessage("¡Inicio de Sesión Invalido!", error);
        })
    }

    return (
        <div className="container mt-5">
        <Card className="ticket-card">
            <Card.Body>
            <Card.Title className="text-center" >Iniciar Sesión</Card.Title>
            <Form onSubmit={(e) => handleSubmit(e)}>
                <Form.Group className="mb-3 mt-5" controlId="usernameId">
                    <Form.Label>Username</Form.Label>
                    <Form.Control  
                    type="text" 
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required/>                            
                </Form.Group>
                <Form.Group className="mb-3 mt-5" controlId="passwordId">
                    <Form.Label>Password</Form.Label>
                    <Form.Control  
                    type="password" 
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required/>                            
                </Form.Group>


                <div className="text-center mt-3"> {/* Centra el contenido y agrega un margen superior */}
                    <Button variant="dark" type="submit" className="btn-lg btn-styled" >Ingresar</Button> {/* Utiliza variant="dark" para el color negro */}
                </div>
            </Form>
            </Card.Body>
        </Card>
        </div>
    );
};

export default LoginForm;
