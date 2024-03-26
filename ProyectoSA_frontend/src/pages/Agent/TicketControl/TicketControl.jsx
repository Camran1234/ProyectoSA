import { useEffect, useState } from "react";
import fondoAgente from "../../../assets/fondo-agente.jpg"
import defaultImage from "../../../assets/defaultImage.png"
import { NavBar } from "../NavBar/NavBar";
import { Button, Col, Form, Row } from "react-bootstrap";
import { IncomingTickets } from "./Components/IncomingTickets/IncomingTickets";
import { HttpService } from "../../../Services/HttpService";
import { getToken } from "../../../Services/userHandler";
import { showErrorMessage } from "../../../components/Alerts/SweetAlertComponent";
import './ticketControl.css';
export const TicketControl = () => {
    const [tickets, setTickets] = useState([]);    
    const [filterTicketType, setFilterTicketType] = useState(0);
    const [filterTicketPriority, setFilterTicketPriority] = useState(0);

    const defaultTicketType = () => {
        setFilterTicketType(0);
    }

    const defaultTicketPriority = () => {
        setFilterTicketPriority(0);
    }

    useEffect(() => {
        document.title = "Agente";
       HttpService.getProtected('/api/ticket/getTickets-all', {}, getToken() )
         .then(response => {
              setTickets(response);
         })
         .catch(error => {
             showErrorMessage("Error", error)
         })

    }, [])

    

    return (
        <div style={{ backgroundImage: `url(${fondoAgente})`, backgroundSize: 'cover', backgroundRepeat: 'no-repeat', minHeight: '100vh' }}>
            <NavBar />  
            <Row>
                <Col>
                    <h2 className="text-center "
                        style={{color:'black', fontFamily: 'Courier New, Courier, monospace',
                                marginTop: '6rem', marginLeft: '5rem'}}
                    >Tickets sin atender</h2>
                </Col>
                <Col>
                    {/*Manejar otro filtro */}
                </Col> 
                <Col>                                                        
                    <Form.Group className="m-5  align-items-center" >
                        <Form.Label className="label">Filtro por Tipo</Form.Label>
                        <div className="d-flex align-items-center w-100">
                            <Button variant="" onClick={() => defaultTicketType()}>
                            <img src={defaultImage} alt="Default" 
                                style={{
                                marginRight: '5px',
                                width: '2.5rem',
                                height: '2.5rem',
                                objectFit: 'cover'
                                }} 
                            />
                            </Button>
                            <Form.Select 
                            value={filterTicketType}
                            onChange={(e) => setFilterTicketType(e.target.value)}
                            required
                            >
                            <option value="0">Default</option>                    
                            <option value="1">Técnico</option>                 
                            <option value="2">Facturación</option>
                            <option value="3">Atención al Cliente</option>
                            </Form.Select>
                        </div>
                    </Form.Group>                    
                </Col>
                <Col>
                    <Form.Group className="m-5  align-items-center" >
                        <Form.Label className="label">Filtro por Prioridad</Form.Label>
                        <div className="d-flex align-items-center w-100">
                            <Button variant="" onClick={() => defaultTicketPriority()}>
                            <img src={defaultImage} alt="Default" 
                                style={{
                                marginRight: '5px',
                                width: '2.5rem',
                                height: '2.5rem',
                                objectFit: 'cover'
                                }} 
                            />
                            </Button>
                            <Form.Select 
                            value={filterTicketPriority}
                            onChange={(e) => setFilterTicketPriority(e.target.value)}
                            required
                            >
                            <option value="0">Default</option>                    
                            <option value="1">Alta</option>                 
                            <option value="2">Media</option>
                            <option value="3">Baja</option>
                            </Form.Select>
                        </div>
                    </Form.Group>     
                </Col>                               
            </Row>          
            <Row>
                <Col className="table_col">
                    <div style={{maxHeight:'60vh', overflowY:'auto'}}>
                        <IncomingTickets
                            tickets={tickets}
                            filterPriority={filterTicketPriority}
                            filterType={filterTicketType}
                        />
                    </div>
                </Col>
                
            </Row>
            
        </div >
    )
}