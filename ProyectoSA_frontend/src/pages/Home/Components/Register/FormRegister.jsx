import React, { useState } from "react";
import { Card, Button, Form, Row, Col } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';
import './formRegister.css';
import { HttpService } from "../../../../Services/HttpService";
import { showErrorMessage, showSuccessMessage } from "../../../../components/Alerts/SweetAlertComponent";
import { getToken } from "../../../../Services/userHandler";

const FormClientRegister = () => {
    const navigate = useNavigate();
    const [user, setUser] = useState("");
    const [name, setName] = useState("");
    const [lastName, setLastName] = useState("");
    const [password, setPassword] = useState("");
    const [confPassword, setConfPassword] = useState("");
    const [equalPassword, setEqualPassword] = useState(false);
    const [phone, setPhone] = useState("");    

    const handlePhoneChange = (e) => {
        const inputValue = e.target.value;
        // Eliminar caracteres no numéricos
        const numericValue = inputValue.replace(/\D/g, "");
        // Limitar a 8 dígitos
        const formattedPhone = numericValue.slice(0, 8);
        // Agregar guion después del cuarto número
        let formattedWithDash = formattedPhone.replace(/(\d{4})(\d{1,4})/, "$1-$2");
        setPhone(formattedWithDash);
    };

    const handlePassword = (value) => {
        setPassword(value);
        
        validatePassword(value, confPassword);
    }

    const handleConfPassword = (value) => {
        setConfPassword(value);
        
        validatePassword(password, value);
    }

    const validatePassword = (passwd, confPasswd) => {
        
        if(passwd.trim() != "" && confPasswd.trim() != ""){
            if(confPasswd == passwd){
                setEqualPassword(true);
            }else{
                setEqualPassword(false);
            }
        }else{
            setEqualPassword(false);
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if(!equalPassword){
            showErrorMessage("Contraseña Inválida", "Los campos de contraseña deben ser iguales");
            return;
        }else{
            const data = {
                username: user,
                password: password,
                userType: "cliente",
                name: name,
                lastName: lastName,
                phone: phone
            }

            HttpService.post("/api/user/register", data, getToken())   
            .then(() => {
                showSuccessMessage("Usuario Creado", "El usuario ha sido creado correctamente");
                navigate('/');
            })         
            .catch((error) => {
                showErrorMessage("Error", error);
                
            });
        }
    };
    

    return (
        <div className="container mt-5">
        <Card className="ticket-card" >
            <Card.Body>
            <Card.Title className="text-center" >Crear Usuario</Card.Title>
            <Form onSubmit={(e) => handleSubmit(e)}>
                <Form.Group className="mb-3 mt-5" controlId="exampleForm.ControlInput1">
                    <Form.Label>Usuario</Form.Label>
                    <Form.Control  
                        type="text"                        
                        value={user}
                        onChange={(e) => setUser(e.target.value)}
                        required/>    
                            
                </Form.Group>   

                <Row className="mb-3 mt-4">
                    <Form.Group as={Col} controlId="formGridNombre">
                        <Form.Label>Nombre</Form.Label>
                        <Form.Control  
                        type="text" 
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required/>
                    </Form.Group>

                    <Form.Group as={Col} controlId="formGridApellido">
                        <Form.Label>Apellido</Form.Label>
                        <Form.Control 
                        type="text"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)} 
                        required/>
                    </Form.Group>
                </Row>

                <Form.Group className="mb-3 mt-2" controlId="exampleForm.ControlInput1">
                    <Form.Label>Contraseña</Form.Label>
                    <Form.Control  
                        type="password"                        
                        value={password}
                        onChange={(e) => handlePassword(e.target.value)}
                        required/>                                
                </Form.Group>
                <Form.Group className="mb-3 mt-2" controlId="exampleForm.ControlInput1">
                    <Form.Label>Confirmar Contraseña</Form.Label>
                    <Form.Control  
                        type="password"                        
                        value={confPassword}
                        onChange={(e) => handleConfPassword(e.target.value)}
                        required/>     
                    {(((confPassword.trim() !== "")&&(password.trim() !== ""))&&(!equalPassword)) ? 
                        <>
                        <small>
                            Las Contraseñas no son iguales   
                            equalPassword={equalPassword ? <>true</>: <>false</>}                         
                        </small>
                        </> :
                        (((confPassword.trim() !== "")&&(password.trim() !== ""))&&(equalPassword)) ?  
                        <small style={{color:'green'}}>
                                Verificación Correcta
                        </small>
                        : null

                    }                           
                </Form.Group>

                <Form.Group as={Col} controlId="formGridPhone">
                        <Form.Label>Teléfono</Form.Label>
                        <Form.Control
                        type="text"
                        placeholder="0000-0000"
                        value={phone}
                        onChange={handlePhoneChange}
                        required
                        />
                        
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

export default FormClientRegister;
