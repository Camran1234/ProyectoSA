import Body from "./Components/Body/Body"
import { NavBar } from "./Components/NavBar/NavBar"
import mainImg from "../../assets/main_body_background.jpg";
import { useEffect, useState } from "react";
import { TicketCards } from "./Client/TicketTracking/TicketCards";
import { useLocation } from "react-router-dom";
import { HttpService } from "../../Services/HttpService";
import { getToken } from "../../Services/userHandler";

export const Home = () => {
    const [tickets, setTickets] = useState([]);
    const {state} = useLocation();
    const [specialSearch, setSpecialSearch] = useState(state?.search);

    useEffect(() => {
        if(specialSearch){
            HttpService.getProtected("/api/ticket/getTicketsSubject", {}, getToken())
            .then((response) => {
                setTickets(response.tickets);
                //showSuccessMessage("Exito", "Se encontraron tickets con el correo ingresado");
            })
            .catch((error)=>{
                showErrorMessage("Error", "No se encontraron tickets con el correo ingresado");
                
            })
        }
    }, [specialSearch])

    useEffect(() => {

        document.title = "Ticket System"
    }, [])

    return (
        <div style={{ backgroundImage: `url(${mainImg})`, backgroundSize: 'cover', backgroundRepeat: 'no-repeat', minHeight: '100vh' }}>
            <NavBar />            
            <Body                 
                setTickets={setTickets}
            />
            <TicketCards
                tickets={tickets}

            />
        </div >
    )
}