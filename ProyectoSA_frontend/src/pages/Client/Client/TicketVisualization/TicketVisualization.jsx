import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

import ViewTicket from './Ticket/ViewTicket';
import secondImg from "../../../../assets/second_body_background.jpg";
import fondoAgente from "../../../../assets/fondo-agente.jpg"
import MessageTicket from './Message/MessageTicket';
import { Col, Row } from 'react-bootstrap';
import { getToken } from '../../../../Services/userHandler';
import { HttpService } from '../../../../Services/HttpService';
import { NavBar as NavBarAgent } from '../../../Agent/NavBar/NavBar';
import { NavBar } from '../../Components/NavBar/NavBar';
import { getUserType } from '../../../../interceptors/CookieHandler';
const TicketVisualization = () => {
    const {state} = useLocation();
    const [ticket, setTicket] = useState(state?.ticket);
    const [agent, setAgent] = useState(false);

    useEffect(() => {
        document.title = "Ticket System";
        // Verificar si hay un ticket en el estado y si el estado es una cadena JSON
        if (ticket && typeof ticket.agent === 'string') {
            // Actualizar el estado de ticket con agent asignado
            setTicket(prevTicket => ({
                ...prevTicket,
                agent: JSON.parse(ticket.agent)
            }));
        }

        if(getToken() && getUserType() === 'agente'){            
            setAgent(true);            
        }
    }, [ticket]);

    return (
        <div style={{ backgroundImage: agent? `url(${fondoAgente})` : `url(${secondImg})`, backgroundSize: 'cover', backgroundRepeat: 'repeat', minHeight: '100vh',
                    maxHeight:'100vh', overflow: 'hidden' }}>
            {agent? <NavBarAgent /> : <NavBar />}
            <Row>
                <Col>
                    <ViewTicket ticket={ticket} agent={agent}/>        
                </Col>
                <Col>
                    <MessageTicket ticket={ticket} agent={agent}/>
                </Col>
            </Row>
        </div>
    )

}

export default TicketVisualization;