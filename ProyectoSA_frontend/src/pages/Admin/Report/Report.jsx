import { useEffect, useState } from "react";

import { NavBar } from "../NavBar/NavBar";
import './report.css';
import { HttpService } from "../../../Services/HttpService";
import { Col, Row } from "react-bootstrap";
import { IncomingUsers } from "../Controller/Components/IncomingUsers/IncomingUsers";
import { ReportView } from "./Components/ReportView";
import { getToken } from "../../../Services/userHandler";

export const Report = () => {
    const [headers,setHeaders] = useState([]);
    const [data,setData] = useState([]);

    useEffect(() => {
        document.title = "Adminsitrador";
        HttpService.postProtected('/api/survey/getDataReport',{}, getToken())
        .then(response => {
            let {unsolvedTickets, unqualifiedTickets, surveys} = response;
            let headers = ["# Ticket","Satisfaccion", "Calidad de Servicio", "Tiempo de Servicio" ];
            setHeaders(headers);
            setData(surveys);
        })

    }, [])

    return (
        <div className="adminContainer">
            <NavBar />
            <Row>
                <Col>                    
                </Col>
                <Col></Col>
                <Col></Col>
                <Col></Col>                
            </Row>
            <Row>
                <Col className="table_col">
                    <div style={{maxHeight:'60vh', overflowY:'auto', overflowX: 'hidden'}}>
                        <ReportView
                            headers={headers}
                           data={data}
                           title={"Informe de Encuestas"}
                        />
                    </div>
                </Col>
            </Row>
        </div>
    )
}