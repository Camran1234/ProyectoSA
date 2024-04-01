import React from "react";
import { Nav, NavDropdown } from "react-bootstrap";
import viteLogo from '/vite.svg'
    import './navLink.css'
import { removeSession } from "../../../../interceptors/CookieHandler";

export const NavBar = () => {
    return (
        <div className="custom-navbar" >
            <Nav className="justify-content-between">
                <Nav.Item>
                    <Nav.Link href="/home" eventKey="link-1" style={{}}>
                        
                        <div>
                        <img src={viteLogo} alt="Logo" style={{ width: "5vh", height: "5vh" }} />
                        SISTEMA DE TICKETS
                        </div>
                    </Nav.Link>
                </Nav.Item>
                <Nav variant="underline" style={{marginTop:"1vh"}} >                                        
                    <NavDropdown title="TICKET" id="nav-dropdown" >
                        <NavDropdown.Item  href="/createTicket" eventKey="2.1">CREAR TICKET</NavDropdown.Item>
                        <NavDropdown.Item href="/find-ticket" eventKey="2.2">RASTREAR TICKET</NavDropdown.Item>
                        <NavDropdown.Divider />
                        <NavDropdown.Item href="#" eventKey="2.3">FAQ</NavDropdown.Item>
                    </NavDropdown>
                    <Nav.Item>
                        <Nav.Link onClick={() => removeSession()} eventKey="link-3">CERRAR SESIÓN</Nav.Link>
                    </Nav.Item>
                </Nav>
            </Nav>
        </div>
    );
};
