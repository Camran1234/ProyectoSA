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
                
                <Nav variant="underline" style={{marginTop:"1vh", marginRight: "1vw"}} >                                                            
                    <Nav.Item>
                        <Nav.Link href="/faq" eventKey="link-2">FAQ</Nav.Link>
                    </Nav.Item>
                </Nav>              
            </Nav>
        </div>
    );
};
