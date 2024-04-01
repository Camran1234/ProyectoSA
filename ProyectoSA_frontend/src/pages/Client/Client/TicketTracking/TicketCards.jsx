import React from 'react';
import { Button, Card } from 'react-bootstrap';
import './ticketCards.css';
import { useNavigate } from 'react-router-dom';

export const TicketCards = ({ tickets }) => {
    const navigate = useNavigate();

    const handleAction = (ticket) => {
        navigate('/seekTicket', { state: { ticket: ticket } });
    }

    const ticketCards = tickets.map((ticket, index) => (
        
        <Card key={index} bg="dark" text="white" style={{ width: '20vw', maxHeight:'30vh', margin: '20px', 
                                                padding: '30px', overflowY: 'auto'}}>
            <Card.Body>
                <Card.Title >#{ticket.ticketNumber}</Card.Title>
                <Card.Subtitle className="mb-2">{ticket.name} {ticket.lastName}</Card.Subtitle>
                <Card.Subtitle className="mb-3">
                    {ticket.state==1 ?
                        <>
                            Estado: Nuevo
                        </>
                        : ticket.state==2 ?
                        <>
                            Estado: En curso
                        </>
                        : ticket.state==3 ?
                        <>  
                            Estado: Resuelto
                        </>
                        : ticket.state==4 ?
                        <>
                            Estado: Cerrado
                        </>
                        : null
                    }
                </Card.Subtitle>
                <Card.Text>{ticket.description}</Card.Text>
                <Button variant="primary" className='custom-button' 
                    onClick={() => handleAction(ticket)}
                >Inspeccionar</Button>
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
