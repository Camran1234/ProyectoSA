
import Table from 'react-bootstrap/Table';
import './incomingUsers.css'
import { Button, Col, Row } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { showConfirmationMessage, showSuccessMessage } from '../../../../../components/Alerts/SweetAlertComponent';
import { HttpService } from '../../../../../Services/HttpService';
import { getToken } from '../../../../../Services/userHandler';
export const IncomingUsers = ({users, requireLoad}) => {
    const changeBlock = (user, blockState) => {
        const attendTicket = () => {
            HttpService.postProtected('/api/user/changeBlock', {username: user.username, blocked: blockState? 1: 0}, getToken())
            .then(response => {                
                requireLoad(true);
            })
            .catch(error => {
                showErrorMessage("Error", error);
            })
        }

        showConfirmationMessage(`${blockState? 'Bloqueando' : 'Desbloqueando'} a ${user.username}`
            , `¿Estás seguro de ${blockState ? 'bloquear' : 'desbloquear'} al usuario ${user.username}?`,
            attendTicket);
    }

    const deleteUser = (user) => {
        const attendTicket = () => {
            HttpService.deleteProtected('/api/user/delete-user', {username: user.username}, getToken())
            .then(() => {
                showSuccessMessage("Éxito", "Usuario eliminado correctamente");                
                requireLoad(true);
            })
            .catch(() => {
                showErrorMessage("No se pudo eliminar al usuario, intente más tardes");
            })
        }

        showConfirmationMessage(`Eliminar a ${user.username}`
            , `¿Estás seguro de eliminar al usuario ${user.username}?`,
            attendTicket);
    }

    return (
        <Table striped bordered hover responsive="lg" >
          <thead>            
            <tr>           
              <th className="p-3 mb-2 bg-dark text-white">ID</th>
              <th className="p-3 mb-2 bg-dark text-white">Usuario</th>
              <th className="p-3 mb-2 bg-dark text-white">Nombre</th>
              <th className="p-3 mb-2 bg-dark text-white">Tipo</th>
              <th className="p-3 mb-2 bg-dark text-white">Teléfono</th>
              <th className="p-3 mb-2 bg-dark text-white">¿Está bloqueado?</th>
              <th className="p-3 mb-2 bg-dark text-white">Acciones</th>
            </tr>
          </thead>
          <tbody >
            {users.map(user => 
               {             
                
                let typeString;
                if(user.userType === 1){
                    typeString = 'cliente'
                }else if(user.userType === 2){
                    typeString = 'agente'
                }else{
                    typeString = 'administrador'
                }


                return(
                    <tr key={user.idUser}>
                        <td>{user.idUser}</td>
                        <td>{user.username}</td>
                        <td>{user.name} {user.lastName}</td>
                        <td>{typeString}</td>
                        <td>{user.phone}</td>
                        <td>{user.blocked ? 'Sí' : 'No'}
                        </td>
                        <td>
                            <Row>
                                <Col>
                                    <Button 
                                        variant={user.blocked ? 'outline-success' : 'outline-warning'} 
                                        onClick={() => changeBlock(user, !user.blocked)}
                                    >
                                        {user.blocked ? <>Desbloquear</> : <>Bloquear</>}
                                    </Button>                                     
                                </Col>    
                                <Col>
                                    <Button
                                        variant='outline-danger'
                                        onClick={() => deleteUser(user)}
                                    >
                                        Eliminar
                                    </Button>
                                </Col>
                            </Row>                      
                        </td>
                    </tr>
                  )
               }
            )}
          </tbody>
        </Table>
      );
    
}
