import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import './viewTicket.css'; // Archivo CSS personalizado

const ViewTicket = ({ ticket }) => {
    return (
        <Container className="mt-5 custom-container">
            <Row>
                <Col className="offset-md-1"> {/* Utiliza offset-md-3 para mover el contenedor a la izquierda */}
                    <h2>Información del Ticket</h2>
                    <p><strong>Número de ticket:</strong> {ticket.ticketNumber}</p>
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
                    <p><strong>Agente asignado al ticket:</strong> {ticket.agent != undefined ?
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
                </Col>
            </Row>
        </Container>
    );
};

export default ViewTicket;
