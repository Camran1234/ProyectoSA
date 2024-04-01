import React, { useState } from "react";
import { Card, Button, Form, Row, Col } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';
import './createTicket.css';
import { HttpService } from "../../../../Services/HttpService";
import { showErrorMessage, showSuccessMessage } from "../../../../components/Alerts/SweetAlertComponent";

const TicketCreator = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const [lastName, setLastName] = useState("");
    const [ticketType, setTicketType] = useState(1);
    const [description, setDescription] = useState(""); 
    const [phone, setPhone] = useState("");
    const [selectedFiles, setSelectedFiles] = useState([]);

    const handleFileChange = (e) => {
        const files = Array.from(e.target.files);
        setSelectedFiles(files);
    };

    const DESCRIPTION_LIMIT = 500;

    const handleDescriptionChange = (e) => {
        const inputValue = e.target.value;
        if (inputValue.length <= DESCRIPTION_LIMIT) {
            setDescription(inputValue);
        }
    };

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

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Archivos");
        let urlsList = [];
        let uploadPromises = []; // Array para almacenar las promesas de carga de archivos
        
        // Subir todos los archivos y guardar las promesas en uploadPromises
        for(let i = 0; i < selectedFiles.length; i++){
            let formData = new FormData();
            formData.append("file", selectedFiles[i]);
            const uploadPromise = HttpService.postFormData("/api/cloud-storage/uploadFile", formData);
            uploadPromises.push(uploadPromise); // Almacena la promesa en el array
        }
        
        // Esperar a que todas las promesas de carga de archivos se resuelvan
        Promise.all(uploadPromises)
            .then((responses) => {
                // Procesar las respuestas de carga de archivos
                responses.forEach((response) => {
                    //alert("Respuesta: " + response.url);
                    urlsList.push(response.url);
                });
    
                // Crear el ticket después de cargar los archivos
                let data = {
                    email: email,
                    name: name,
                    lastName: lastName,
                    phone: phone,
                    ticketType: ticketType,
                    ticketPriority: 3,
                    description: description,
                    files: urlsList 
                };
    
                // Crear el ticket
                return HttpService.post("/api/ticket/create-ticket", data);
            })
            .then((response) => {
                // Manejar la respuesta del ticket creado
                showSuccessMessage("Ticket creado con éxito");
                navigate("/");
            })
            .catch((error) => {
                // Manejar errores en cualquier punto del proceso
                showErrorMessage("Error!", error);
                console.error(error);
            });
    };
    

    return (
        <div className="container mt-5">
        <Card className="ticket-card" >
            <Card.Body>
            <Card.Title className="text-center" >Crear Nuevo Ticket</Card.Title>
            <Form onSubmit={(e) => handleSubmit(e)}>
                <Form.Group className="mb-3 mt-5" controlId="exampleForm.ControlInput1">
                    <Form.Label>Correo</Form.Label>
                    <Form.Control  
                        type="email" 
                        placeholder="name@gmail.com" 
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required/>    
                            
                </Form.Group>   

                <Row className="mb-3 mt-4">
                    <Form.Group as={Col} controlId="formGridNombre">
                        <Form.Label>Nombre</Form.Label>
                        <Form.Control  
                        type="text" 
                        placeholder="Juan..." 
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required/>
                    </Form.Group>

                    <Form.Group as={Col} controlId="formGridApellido">
                        <Form.Label>Apellido</Form.Label>
                        <Form.Control 
                        type="text"
                        placeholder="Pérez..." 
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)} 
                        required/>
                    </Form.Group>
                </Row>

                <Row className="mb-5">
                    <Form.Group as={Col} controlId="formGridPhone">
                        <Form.Label>Teléfono</Form.Label>
                        <Form.Control
                        type="text"
                        placeholder="3445-3243"
                        value={phone}
                        onChange={handlePhoneChange}
                        required
                        />
                        
                    </Form.Group>

                    <Form.Group as={Col} controlId="exampleForm.ControlInput1">
                        <Form.Label>Tipo de Ticket</Form.Label>
                        <Form.Select 
                            value={ticketType}
                            onChange={(e) => setTicketType(e.target.value)}
                            required
                        >                    
                            <option value="1">Tecnico</option>
                            <option value="2">Facturacion</option>
                            <option value="3">Atención al Cliente</option>
                        </Form.Select>
                    </Form.Group>  

                    
                </Row>
                
                <Form.Group as={Col} controlId="formGridDescription">
                    <Form.Label>Descripcion</Form.Label>
                    <Form.Control
                        type="text"
                        as={"textarea"}
                        rows={4}
                        placeholder="Descripcion..."
                        value={description}
                        onChange={handleDescriptionChange}
                        maxLength={DESCRIPTION_LIMIT}
                    />
                    <small>{description.length}/{DESCRIPTION_LIMIT}</small>
                </Form.Group>
                
                <Form.Group controlId="formFileMultiple" className="mb-3 mt-5">
                    <Form.Label>Seleccione uno o varios archivos</Form.Label>
                    <Form.Control type="file" multiple onChange={handleFileChange} />
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

export default TicketCreator;
