
import mainImg from "../../../../assets/main_body_background.jpg";
import { useEffect, useState } from "react";

import { HttpService } from "../../../../Services/HttpService";
import { TicketCards } from "../TicketTracking/TicketCards";
import { getToken } from "../../../../Services/userHandler";
import { NavBar } from "../../Components/NavBar/NavBar";
import { showErrorMessage } from "../../../../components/Alerts/SweetAlertComponent";


export const SearchTicketSurvey = () => {
    const [tickets, setTickets] = useState([]);
    

    useEffect(() => {
        HttpService.getProtected("/api/ticket/getTickets-no-survey", {}, getToken())
        .then((response) => {
            console.log(response.tickets);
            setTickets(response.tickets);            
        })
        .catch((error)=>{
            showErrorMessage("Aviso", "No se encontraron tickets disponibles para calificar");
            
        })
    }, [])

    useEffect(() => {

        document.title = "Ticket System"
    }, [])

    return (
        <div style={{ backgroundImage: `url(${mainImg})`, backgroundSize: 'cover', backgroundRepeat: 'no-repeat', minHeight: '100vh' }}>
            <NavBar />            
            <div className="container" style={{marginTop:'15vh'}} >
                <h1 className="text-center" >Tickets disponibles para calificar</h1>                
            </div>
            <TicketCards
                tickets={tickets}
                survey={true}
            />
        </div >
    )
}