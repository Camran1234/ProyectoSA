
import Table from 'react-bootstrap/Table';
import './queueTickets.css'
import { Button } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { showConfirmationMessage, showSuccessMessage } from '../../../../../components/Alerts/SweetAlertComponent';
import { HttpService } from '../../../../../Services/HttpService';
import { getToken } from '../../../../../Services/userHandler';
import { useNavigate } from 'react-router-dom';
export const QueueTickets = ({tickets, filterType, filterPriority}) => {
    const [filteredTickets, setFilteredTickets] = useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        setFilteredTickets(tickets);
        
    }, [tickets]);
    
    useEffect(() => {              
        if (filterType !== 0 && filterPriority === 0) {
             
            let newTickets = [];
            for(let i = 0; i < tickets.length; i++){
                let ticket = tickets[i];
                if(ticket.ticketType == filterType){
                    newTickets.push(tickets[i]);
                }
            }
            setFilteredTickets(newTickets);
        }else if(filterType === 0 && filterPriority !== 0) {
            
            let newTickets = [];
            for(let i = 0; i < tickets.length; i++){
                let ticket = tickets[i];
                if(ticket.priority == filterPriority){
                    newTickets.push(tickets[i]);
                }
            }
            setFilteredTickets(newTickets);
        }else if(filterType !== 0 && filterPriority !== 0){
            
            let newTickets = [];
            for(let i = 0; i < tickets.length; i++){
                let ticket = tickets[i];
                if(ticket.ticketType == filterType &&
                    ticket.priority == filterPriority){
                    newTickets.push(tickets[i]);
                }
            }
            
            setFilteredTickets(newTickets);
        }else {
            
            setFilteredTickets(tickets);
        }
        
    }, [filterType, filterPriority, tickets]);
        

    const handleAttendTicket = (ticket) => {
        navigate('/seekTicket', { state: { ticket: ticket } });
    }

    return (
        <Table striped bordered hover responsive="sm" >
          <thead>            
            <tr>
              <th className="p-3 mb-2 bg-dark text-white">#</th>
              <th className="p-3 mb-2 bg-dark text-white">Correo</th>
              <th className="p-3 mb-2 bg-dark text-white">Cliente</th>
              <th className="p-3 mb-2 bg-dark text-white">Teléfono</th>
              <th className="p-3 mb-2 bg-dark text-white">Tipo</th>
              <th className="p-3 mb-2 bg-dark text-white">Prioridad</th>
              <th className="p-3 mb-2 bg-dark text-white">Descripción</th>
              <th className="p-3 mb-2 bg-dark text-white">Acciones</th>
            </tr>
          </thead>
          <tbody >
            {filteredTickets.map(ticket => 
               {
                let type = ticket.ticketType;
                let typeString = "";
                let priority = ticket.priority;
                let priorityString = "";

                if(type === 1){
                    typeString = 'Técnico'
                }else if(type === 2){
                    typeString = 'Facturación'
                }else{
                    typeString = 'Atención al Cliente'
                }

                if(priority === 3){
                    priorityString = 'Baja'
                }else if(priority === 2){
                    priorityString = 'Media'
                }else {
                    priorityString = 'Alta'
                }


                return(
                    <tr key={ticket.ticketNumber}>
                        <td>{ticket.ticketNumber}</td>
                        <td>{ticket.email}</td>
                        <td>{ticket.name} {ticket.lastName}</td>
                        <td>{ticket.phone}</td>
                        <td>{typeString}</td>
                        <td>{priorityString}</td>
                        <td>{ticket.description} [Creado el {ticket.dateOfCreation}]
                        </td>
                        <td>
                            <Button variant='outline-secondary' 
                                onClick={() => handleAttendTicket(ticket)}
                            >
                                Ver
                            </Button>
                        </td>
                    </tr>
                  )
               }
            )}
          </tbody>
        </Table>
      );
    
}
