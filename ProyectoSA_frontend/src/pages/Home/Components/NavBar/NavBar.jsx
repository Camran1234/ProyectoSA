import React from "react";
import { Nav, NavDropdown } from "react-bootstrap";
import viteLogo from '/vite.svg'
    import './navLink.css'

export const NavBar = () => {
    return (
        <div className="custom-navbar" >
            <Nav className="justify-content-between">
                <Nav.Item>
                    <Nav.Link href="/" eventKey="link-1" style={{}}>
                        
                        <div>
                        <img src={viteLogo} alt="Logo" style={{ width: "5vh", height: "5vh" }} />
                        SISTEMA DE TICKETS
                        </div>
                    </Nav.Link>
                </Nav.Item>
                <Nav variant="underline" style={{marginTop:"1vh"}} >                    
                    <Nav.Item>
                        
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link href="/login" eventKey="link-3">CONSOLA</Nav.Link>
                    </Nav.Item>
                    <NavDropdown title="TICKET" id="nav-dropdown" style={{marginRight:"5vw"}}>
                        <NavDropdown.Item  href="/createTicket" eventKey="4.1">CREAR TICKET</NavDropdown.Item>
                        <NavDropdown.Item href="/" eventKey="4.2">RASTREAR TICKET</NavDropdown.Item>
                        <NavDropdown.Divider />
                        <NavDropdown.Item href="#" eventKey="4.4">FAQ</NavDropdown.Item>
                    </NavDropdown>
                </Nav>
            </Nav>
        </div>
    );
};
