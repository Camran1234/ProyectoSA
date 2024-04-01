import React, { useState, useEffect } from 'react';
import { w3cwebsocket as W3CWebSocket } from 'websocket';
import { HttpService } from '../../../../../Services/HttpService';
import { Form, InputGroup } from 'react-bootstrap';
import  sentLogo  from '../../../../../assets/sent.png'
import './chatComponent.css'
import { formatDate } from '../../../../../Services/DateFormat';
import { showErrorMessage } from '../../../../../components/Alerts/SweetAlertComponent';
const VITE_WS_API = import.meta.env.VITE_WS_API;
const client = new W3CWebSocket(VITE_WS_API);


const ChatComponent = ({ ticketNumber, user="usuario"}) => {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');

  const handleKeyDown = (event) => {
    //Evento es enter
    if (event.keyCode === 13) {
      sendMessage();
    }
  };

  //Load messages
  useEffect(() => {
    
    HttpService.post(`/api/tracking/trackLogs`, {ticketNumber: ticketNumber})
      .then((response) => {

        //console.log("Mensajes", response);
        var receivedMessages = !response ? [] : response;        
        setMessages(receivedMessages);
      })
      .catch((error) => {
        showErrorMessage("Error", "No se pudieron obtener los mensajes");        
      });
  }, [])

  //Suscribe and receive messages

  useEffect(() => {
    client.onopen = () => {
      console.log('WebSocket Client Connected');
    };
    client.onmessage = (message) => {
      try {
        //console.warn('Mensaje recibido:', message);
        const parsedMessage = JSON.parse(message.data);
        if (parsedMessage.ticketNumber === ticketNumber) {
          //console.log("Mensajes recibidos", messages);
          //console.log("Mensaje recibido", parsedMessage);
          setMessages((messages) => [...messages, parsedMessage]);          
        }
      } catch (error) {
        console.error('Error al analizar el mensaje JSON:', error);
      }
    };
  }, [ticketNumber]);

  const sendMessage = () => {
    const payload = {
      description: message,
      ticketNumber: ticketNumber,
      sent: user,
      date: new Date().toISOString(),
    };
    
    HttpService.post('/api/tracking/saveMessage', payload)
      .then((response) => {
        
        client.send(JSON.stringify(response));
      })
      .catch((error) => {
        showErrorMessage("Error", "No se pudo enviar el mensaje");
        console.error("Error al enviar el mensaje", error);
      });

    
    
    setMessage('');
  };

  const printMessages = messages.map((msg, index) => {
    return (
      <div key={index}>
        {msg.sent === user ? 
          <div className="messageSent">
            
            <div className='textContainer'>
              {msg.description}  
            </div>

            <div className='hourSent'>
              {formatDate(msg.dateTimeContacted)}
            </div>
          </div> : 
          <div className="messageReceived">
            <div className='textContainer'>
              {msg.sent == "" ?
                "[sistema] " : 
                msg.sent === "agente" ?
                "[agente] " :
                `[${msg.sent}] `
              }
              {msg.description}
            </div>
            <div className='hourReceived'>
              {formatDate(msg.dateTimeContacted)}
              
            </div>
          </div>   
        }
                 
      </div>
    )
  });

  return (
    <div>
      <div className='containerMessages'>
        <h2>Chat del Ticket #{ticketNumber}</h2>
        {printMessages}
      </div>

      
      <InputGroup className="mb-3">
        <Form.Control
          id="inputMessage"
          className='inputMessage'
          placeholder="Escribe tu mensaje aquí..."
          aria-label="Recipient's username"
          aria-describedby="basic-addon2"
          value={message}
          onKeyDown={handleKeyDown}
          onChange={(e) => setMessage(e.target.value)}
          rows ={3}
          type = "textarea"
          style={{
            position: 'relative',
            maxHeight: '100px',
            overflowY: 'auto',
            overflowX: 'hidden',
            resize: 'vertical', 
            overflowWrap: 'break-word'
          }}
        />
        <InputGroup.Text  >
          <img 
          onClick={sendMessage}
          className ="logoImg" 
          src={sentLogo} 
          alt="Enviar" 
          style={{
            cursor: 'pointer',
            width: '25px',
            height: '25px'
            
          }}/>
        </InputGroup.Text>
      </InputGroup>
      
    </div>
  );
};

export default ChatComponent;
