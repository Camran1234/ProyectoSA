import { useEffect, useState } from "react"
import { NavBar } from "../NavBar/NavBar"
import { Col, Row } from "react-bootstrap"
import { IncomingUsers } from "./Components/IncomingUsers/IncomingUsers"
import { HttpService } from "../../../Services/HttpService"
import { getToken } from "../../../Services/userHandler"
import { showErrorMessage } from "../../../components/Alerts/SweetAlertComponent"



export const Controller = () => {
    const [users, setUsers] = useState([]);
    const [requireLoad, setRequireLoad] = useState(true);
    useEffect(() => {

        document.title = "Administrador"
    }, [])

    useEffect(() => {
        if (requireLoad) {
            HttpService.postProtected('/api/user/get-users', {}, getToken())
                .then(response => {
                    setUsers(response);
                })
                .catch(error => {
                    showErrorMessage("Error", "Error al cargar los usuarios");   
                })
            setRequireLoad(false);
        }
    }, [requireLoad])

    return (
        <div className="adminContainer">
            <NavBar />
            <Row>
                <Col>
                    <h2 className="text-center "
                        style={{color:'black', fontFamily: 'Courier New, Courier, monospace',
                                marginTop: '4rem', marginLeft: '5rem'}}
                    >Gestor de Usuarios</h2>
                </Col>
                <Col></Col>
                <Col></Col>
                <Col></Col>                
            </Row>
            <Row>
                <Col className="table_col">
                    <div style={{maxHeight:'60vh', overflowY:'auto', overflowX: 'hidden'}}>
                        <IncomingUsers
                            users={users}
                            requireLoad={setRequireLoad}
                           
                        />
                    </div>
                </Col>
            </Row>
        </div>
    )
}