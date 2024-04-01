import Body from "./Components/Body/Body"
import { NavBar } from "./Components/NavBar/NavBar"
import mainImg from "../../assets/main_body_background.jpg";
import { useEffect, useState } from "react";
import { TicketCards } from "./Client/TicketTracking/TicketCards";

export const Home = () => {
    const [tickets, setTickets] = useState([]);

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