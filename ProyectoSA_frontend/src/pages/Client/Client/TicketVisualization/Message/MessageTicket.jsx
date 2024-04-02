import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import './messageTicket.css';
import ChatComponent from './ChatComponent';

const MessageTicket = ({ ticket, agent }) => {
    return (
        <Container className="mt-5 message-container">
            <Row>
                <Col className="offset-md-0"> {/* Utiliza offset-md-3 para mover el contenedor a la izquierda */}
                    <ChatComponent
                        ticketNumber={ticket.ticketNumber}
                        user={agent? "agent" : "usuario"}
                    />
                </Col>
            </Row>
        </Container>
    );
};

export default MessageTicket;
