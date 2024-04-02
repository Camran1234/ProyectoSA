import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import './viewTicket.css'; // Archivo CSS personalizado
import defaultImage from '../../../../../assets/defaultImage.png';
import { HttpService } from '../../../../../Services/HttpService';
import { getToken } from '../../../../../Services/userHandler';
import { showConfirmationMessage, showErrorMessage } from '../../../../../components/Alerts/SweetAlertComponent';
import { useNavigate } from 'react-router-dom';

const ViewTicket = ({ ticket, agent }) => {
    const navigate = useNavigate();
    const [tickets, setTickets] = useState(ticket?.files);
    const [state, setState] = useState(1);
    const [priority, setPriority] = useState(1);


    useEffect(() => {
        setTickets(ticket?.files);
    }, [ticket]);

    
    const printFiles = tickets.map((file, index) => {
         
        return (        
            <p key={index}><strong>Archivo {index+1}:</strong> {ticket.problemSolved==0 ?
            <>
                <a style={{color:'white'}} href={file}>descargar</a>
            </>
            : null
            }</p>                            
        )
    });

    const changePriority = () => {
        const priorityFunc = () => {
            const data = {
                priority: priority,
                ticketNumber: ticket.ticketNumber
            }
            
            HttpService.postProtected('/api/ticket/change-priority', data, getToken())
            .then(response => {
                const newTicket = {
                    ...ticket, // Copiar el objeto original
                    priority: priority // Sobrescribir el valor del parámetro que deseas cambiar
                  };
                navigate('/seekTicket', { state: { ticket: newTicket } });
                window.location.reload();
            })
            .catch(error => {
                showErrorMessage("Error", error)
            })
        }
        showConfirmationMessage("Cambiar prioridad", "¿Está seguro que desea cambiar la prioridad del ticket?", priorityFunc);
    }

    const changeState = () => {
        const stateFunc = () => {
            const data = {
                state: state,
                ticketNumber: ticket.ticketNumber
            }
            
            HttpService.postProtected('/api/tracking/change-state', data, getToken())
            .then(response => {
                const newTicket = {
                    ...ticket, // Copiar el objeto original
                    state: state // Sobrescribir el valor del parámetro que deseas cambiar
                  };
                navigate('/seekTicket', { state: { ticket: newTicket } });
                window.location.reload();
            })
            .catch(error => {
                showErrorMessage("Error", error)
            })
        }
        showConfirmationMessage("Cambiar estado", "¿Está seguro que desea cambiar el estado del ticket?", stateFunc);
    }
        
    const endTicket = () => {
        const endFunc = () => {
            const data = {
                ticketNumber: ticket.ticketNumber
            }
            
            HttpService.postProtected('/api/tracking/solve', data, getToken())
            .then(response => {                
                navigate('/agente/ticket-attend');
            })
            .catch(error => {
                showErrorMessage("Error", error)
            })
        }
        showConfirmationMessage("Finalizar Ticket", "¿Está seguro que desea finalizar el ticket?", endFunc);
        
    }

    return (
        <Container className="mt-5 custom-container">
            <Row>
                <Col className="offset-md-1"> {/* Utiliza offset-md-3 para mover el contenedor a la izquierda */}
                    <h2>Información del Ticket</h2>
                    <p><strong>Número de ticket:</strong> {ticket.ticketNumber}</p>
                    <p><strong>Correo registrado:</strong> {ticket.email}</p>
                    <p><strong>Estado del ticket:</strong> {ticket.state==1 ?
                        <>
                            Nuevo
                        </>
                        : ticket.state==2 ?
                        <>
                            En curso
                        </>
                        : ticket.state==3 ?
                        <>  
                            Resuelto
                        </>
                        : ticket.state==4 ?
                        <>
                            Cerrado
                        </>
                        : null
                    }</p>
                    <p><strong>Prioridad del ticket:</strong> {ticket.priority==1 ?
                        <>
                            Alta
                        </>
                        : ticket.priority==2 ?
                        <>
                            Media
                        </>
                        : ticket.priority==3 ?
                        <>
                            Baja
                        </>
                        : null
                    }</p>
                    <p><strong>Fecha de creación del ticket:</strong> {ticket.dateOfCreation}</p>
                    <p><strong>Fecha de última actualización del ticket:</strong> {ticket.dateLastUpdate}</p>
                    <p><strong>Agente asignado al ticket:</strong> {ticket.agent.name ?
                        <>
                           
                            {ticket.agent.name} {ticket.agent.lastName}   

                        </>
                        : <>
                            No asignado
                        </>
                    }</p>
                    <p><strong>Problema Resuelto:</strong> {ticket.problemSolved==0 ?
                        <>
                            Sin resolver
                        </>
                        : <div style={{color:'green'}}>
                            Resuelto
                        </div>
                    }</p>
                    {printFiles}
                </Col>
            </Row>
            {agent ?
                <>
                    <Row>
                        <Col>
                            <Form.Group className="align-items-center" >
                                <Form.Label className="label">Cambiar Prioridad</Form.Label>
                                <div className="d-flex align-items-center w-100">
                                    <Button variant="" onClick={() => changePriority()}>
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
                                        value={priority}
                                        onChange={(e) => setPriority(e.target.value)}
                                    required
                                    >                        
                                    <option value="1">Alta</option>                 
                                    <option value="2">Media</option>
                                    <option value="3">Baja</option>
                                    </Form.Select>
                                </div>
                            </Form.Group> 
                        </Col>
                        <Col>
                            <Form.Group className="align-items-center" >
                                <Form.Label className="label">Cambiar Estado</Form.Label>
                                <div className="d-flex align-items-center w-100">
                                    <Button variant="" onClick={() => changeState()}>
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
                                    value={state}
                                    onChange={(e) => setState(e.target.value)}
                                    required
                                    >
                                    <option value="1">Nuevo</option>                 
                                    <option value="2">En curso</option>
                                    <option value="3">Resuelto</option>
                                    <option value="4">Cerrado</option>
                                    </Form.Select>
                                </div>
                            </Form.Group> 
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group className="align-items-center" 
                                style={{
                                    marginLeft: '30%',
                                    marginTop: '5%'

                                }}
                            >
                                <Button variant="danger" 
                                onClick={() => endTicket()}>
                                    Finalizar Ticket
                                </Button>
                            </Form.Group>
                            
                        </Col>
                    </Row>
                </>
            : null }           
        </Container>
    );
};

export default ViewTicket;
