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
                    <Nav.Link href="/admin" eventKey="link-1" style={{}}>
                        
                        <div>
                        <img src={viteLogo} alt="Logo" style={{ width: "5vh", height: "5vh" }} />
                        SISTEMA DE TICKETS
                        </div>
                    </Nav.Link>
                </Nav.Item>
                <Nav variant="underline" style={{marginTop:"1vh"}} >                    
                    <Nav.Item>
                        <Nav.Link  href="/admin/register" eventKey="link-2">REGISTRO</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link  href="/admin/controller" eventKey="link-3">GESTOR DE CLIENTES</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link  href="/admin/reports" eventKey="link-4">INFORMES</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link 
                            href="/login" 
                            eventKey="link-5"
                            onClick={() => removeSession()}
                        >CERRAR SESION
                        </Nav.Link>

                    </Nav.Item>
                    
                </Nav>
            </Nav>
        </div>
    );
};
