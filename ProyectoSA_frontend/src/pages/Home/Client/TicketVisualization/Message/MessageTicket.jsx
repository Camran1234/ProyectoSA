import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import './messageTicket.css'; // Archivo CSS personalizado

const MessageTicket = ({ ticket }) => {
    return (
        <Container className="mt-5 message-container">
            <Row>
                <Col className="offset-md-1"> {/* Utiliza offset-md-3 para mover el contenedor a la izquierda */}
                    <h2>Mensajes</h2>
                    
                </Col>
            </Row>
        </Container>
    );
};

export default MessageTicket;
