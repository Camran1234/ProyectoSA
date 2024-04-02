import React, { useEffect, useState } from "react";
import { Form, Button } from "react-bootstrap";
import './body.css';
import { HttpService } from "../../../../Services/HttpService";
import { showErrorMessage, showSuccessMessage } from "../../../../components/Alerts/SweetAlertComponent";
import { getToken } from "../../../../Services/userHandler";

const Body = ({setTickets}) => {
    const [email, setEmail] = useState("");
    

    const handleSubmit = (e) => {
        e.preventDefault();

        HttpService.getProtected("/api/ticket/getTickets", {email: email}, getToken())
        .then((response) => {
            setTickets(response.tickets);
            //showSuccessMessage("Exito", "Se encontraron tickets con el correo ingresado");
        })
        .catch((error)=>{
            showErrorMessage("Error", "No se encontraron tickets con el correo ingresado");
            
        })
    }

    return (
    <div className="container" style={{marginTop:'15vh'}} >
        <h1 className="text-center" >Buscar Ticket por Correo</h1>
        <Form onSubmit={(e) => handleSubmit(e)}>
            <Form.Group className="mb-3 mt-5" controlId="exampleForm.ControlInput1">
                <Form.Control 
                    size="lg" 
                    type="email" 
                    placeholder="name@gmail.com" 
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <div className="text-center mt-3"> {/* Centra el contenido y agrega un margen superior */}
                    <Button variant="dark" type="submit" className="btn-lg btn-styled" >Buscar</Button> {/* Utiliza variant="dark" para el color negro */}
                </div>
            </Form.Group>   
        </Form>
    </div>
    );
};

export default Body;
