import React from 'react';
import { Button, Card } from 'react-bootstrap';
import './ticketCards.css';
import { useNavigate } from 'react-router-dom';
import { SurveyModal } from '../../../../components/Survey/Survey';



export const TicketCards = ({ tickets, survey=false }) => {
    const navigate = useNavigate();

    const handleAction = (ticket) => {        
        navigate('/seekTicket', { state: { ticket: ticket } });
    }

    const ticketCards = tickets.map((ticket, index) => (
        
        <Card key={index} bg="dark" text="white" style={{ width: '20vw', maxHeight:'30vh', margin: '20px', 
                                                padding: '30px', overflowY: 'auto'}}>
            <Card.Body>
                <Card.Title >#{ticket.ticketNumber}</Card.Title>
                <Card.Subtitle className="mb-2">Por {ticket.name} {ticket.lastName}</Card.Subtitle>
                <Card.Subtitle className="mb-2">
                    {ticket.state==1 ?
                        <>
                            Estado Nuevo
                        </>
                        : ticket.state==2 ?
                        <>
                            Estado en curso
                        </>
                        : ticket.state==3 ?
                        <>  
                            Estado resuelto
                        </>
                        : ticket.state==4 ?
                        <>
                            Estado cerrado
                        </>
                        : null
                    }
                </Card.Subtitle>
                <Card.Subtitle className="mb-2">
                    {ticket.priority==1 ?
                        <>
                            Prioridad alta
                        </>
                        : ticket.priority==2 ?
                        <>
                            Prioridad media
                        </>
                        : ticket.priority==3 ?
                        <>  
                            Prioridad baja
                        </>
                        : null
                    }
                </Card.Subtitle>
                <Card.Subtitle className="mb-2">
                    {ticket.ticketType==1 ?
                        <>
                            Problema técnico
                        </>
                        : ticket.ticketType==2 ?
                        <>
                            Problema de Facturación
                        </>
                        : ticket.ticketType==3 ?
                        <>  
                            Atención al cliente
                        </>
                        : null
                    }
                </Card.Subtitle>
                <Card.Subtitle className="mb-3">
                    Atendido por: {JSON.parse(ticket.agent).username}
                </Card.Subtitle>
                <Card.Text>{ticket.description}</Card.Text>
                <Card.Text><small style={{color:'grey'}}>[Creado el {ticket.dateOfCreation}]</small></Card.Text>
                {survey ? 
                    <Card.Text><small style={{color:'grey'}}>[Finalizado el {ticket.dateLastUpdate}]</small></Card.Text>
                    : null
                }
                {survey ?
                    <SurveyModal 
                        ticket={ticket}
                    />
                    : <Button 
                        variant="primary" 
                        className='custom-button'
                        onClick={() => handleAction(ticket)}
                    >
                        Inspeccionar
                    </Button>
                }
            </Card.Body>
        </Card>
    ));

    return (
        <div className='containerTickets'>
            <div className="ticket-cards-container">            
                <div className="ticket-cards-scrollable">
                    {ticketCards}
                </div>
            </div>
        </div>
    );
};
