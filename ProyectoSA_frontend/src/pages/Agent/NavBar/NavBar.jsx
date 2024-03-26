import React from "react";
import { Nav, NavDropdown } from "react-bootstrap";
import viteLogo from '/vite.svg'
import './navLink.css'
import { removeSession } from "../../../interceptors/CookieHandler";

export const NavBar = () => {    

    return (
        <div className="custom-navbar" >
            <Nav className="justify-content-between">
                <Nav.Item>
                    <Nav.Link href="/agente" eventKey="link-1" style={{}}>
                        
                        <div>
                        <img src={viteLogo} alt="Logo" style={{ width: "5vh", height: "5vh" }} />
                        SISTEMA DE TICKETS
                        </div>
                    </Nav.Link>
                </Nav.Item>
                <Nav variant="underline" style={{marginTop:"1vh"}} >                                        
                    <NavDropdown title="CONTROL TICKETS" id="nav-dropdown" style={{marginRight:"2vw"}}>
                        <NavDropdown.Item  href="/agente/ticket-control" eventKey="2.1">TICKETS SIN ATENDER</NavDropdown.Item>
                        <NavDropdown.Item href="/agente/ticket-attend" eventKey="4.2">TUS TICKETS</NavDropdown.Item>
                    </NavDropdown>
                    <Nav.Item>
                        <Nav.Link 
                            
                            eventKey="link-3"
                            onClick={() => removeSession()}
                        >CERRAR SESION
                        </Nav.Link>

                    </Nav.Item>
                    
                </Nav>
            </Nav>
        </div>
    );
};
