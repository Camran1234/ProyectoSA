
import { useEffect } from "react";
import mainImg from "../../../../assets/main_body_background.jpg";
import { NavBar } from "../../Components/NavBar/NavBar";
import TicketCreator from "../../Components/TicketCreator/TicketCreator";


export const CreateTicket = () => {

    useEffect(() => {

        document.title = "Ticket System"
    }, [])

    return (
        <div style={{ backgroundImage: `url(${mainImg})`, backgroundSize: 'cover', backgroundRepeat: 'repeat', minHeight: '100vh',
                    maxHeight:'100vh', overflowY: 'hidden' }}>
            <NavBar />
            <TicketCreator />
        </div>
    )

}