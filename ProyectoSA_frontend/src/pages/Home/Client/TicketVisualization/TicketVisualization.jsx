import React from 'react';
import { useLocation } from 'react-router-dom';
import { NavBar } from '../../Components/NavBar/NavBar';
import ViewTicket from './Ticket/ViewTicket';
import secondImg from "../../../../assets/second_body_background.jpg";
import MessageTicket from './Message/MessageTicket';
import { Col, Container, Row } from 'react-bootstrap';
const TicketVisualization = () => {
    const {state} = useLocation();
    const ticket= state?.ticket;

    return (
        <div style={{ backgroundImage: `url(${secondImg})`, backgroundSize: 'cover', backgroundRepeat: 'repeat', minHeight: '100vh',
                    maxHeight:'100vh', overflowY: 'hidden' }}>
            <NavBar />
            <Row>
                <Col>
                    <ViewTicket ticket={ticket}/>        
                </Col>
                <Col>
                    <MessageTicket ticket={ticket}/>
                </Col>
            </Row>
        </div>
    )

}

export default TicketVisualization;