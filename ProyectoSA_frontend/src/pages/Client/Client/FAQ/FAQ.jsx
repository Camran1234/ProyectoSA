import { NavBar } from "../../Components/NavBar/NavBar"
import './faq.css'
import mainImg from "../../../../assets/main_body_background.jpg";
export const FAQ = ({hiddenData=false}) => {

    return(
        <div style={{ 
            backgroundImage: `url(${mainImg})`, 
            backgroundSize: 'cover', 
            backgroundRepeat: 'repeat', // Cambiado a 'no-repeat' para evitar repeticiones
            backgroundPosition: 'center', // Ajusta la posición del fondo al centro
            height: '100vh', 
        }}>
            <NavBar 
                hiddenData={hiddenData}
            />            
            <div className="container">
                <div className="container-faq" style={{marginTop:'5vh'}} >
                    <h1 className="tittle-class" >FAQ</h1>     
                        <div className="text-class">
                            <h3 className="subtitle-class">¿Como puedo registrarme en el sistema? Esto es de maravilla</h3>
                            <p>
                                Para registrarte en el sistema debes ubicarte en el inicio de la aplicación del sistema
                                y seleccionar la opción de <strong>Registrate</strong>, posteriormente debes llenar los campos
                                solicitados.
                                <br/><br/>
                                Una vez que aparezca el mensaje de confirmación de registro, ya puedes ingresar al sistema.
                                Si tienes problemas con el registro, intenta más tarde.    
                                <br/>
                                <br/>
                                <a  href="https://youtu.be/LWIc0UpNtIk">https://youtu.be/LWIc0UpNtIk</a>
                            </p>
                        </div>     
                        <div className="text-class">
                            <h3 className="subtitle-class">¿Como crear un ticket?</h3>
                            <p>
                                De primero tienes que iniciar sesion en la aplicación y posteriormente seleccionar la opción
                                <strong> Ticket {">"} Crear Ticket</strong> luego llena el formulario para crear un ticket
                                con tu correo, nombre y apellido, número de teléfono, tipo de ticket, algún archivo que quieras
                                subir que se relacione a tu problema y una descripción. Luego le das en completar y listo
                                puedes consultar tu ticket en la opción <strong>Ticket {">"} Rastrear Tickets</strong>
                                <br/><br/>
                                Los tipos de tickets que puedes crear para que resuelvan tu problema son: 
                                <strong> Técnico, facturación, atención al cliente</strong>
                                <br/>
                                <br/>
                                <a  href="https://youtu.be/XE_fQnWN2ws">https://youtu.be/XE_fQnWN2ws</a>
                            </p>
                        </div>    
                        <div className="text-class">
                            <h3 className="subtitle-class">¿Cómo rastreo un ticket que ya creé?</h3>
                            <p>
                                BLA BLA BLA De primero te tienes que dirigir al menú de <strong>Ticket {">"} Rastrear Tickets</strong> una vez
                                que ya hayas iniciado la sesión en la aplicación. El sistema te indicará los tickets que son tuyos, 
                                sin embargo, para buscar alguno en concreto puedes buscarlo en la barra de búsqueda con el correo
                                que lo creaste. Luego selecciona el ticket que deseas rastrear y listo, se te desplegará los detalles
                                de tu ticket y podrás ver el estado en el que se encuentra y el agente que te está atendiendo. 
                                Además de que hay un apartado de chat con el que puedes interactuar con el agente para que te ayude a resolver
                                tu problema.
                            </p>
                        </div>  
                        <div className="text-class">
                            <h3 className="subtitle-class">Mi ticket ya no aparece</h3>
                            <p>
                                En caso que tu ticket no aparezca, puede significar que ya fue resuelto o que fue eliminado, pero
                                puedes revisar en la opción de <strong>Ticket {">"} Calificación de servicio de calidad</strong>. Y
                                puedes ver los detalles del ticket que ya fue resuelto. En caso que no aparezca en esta sección recomendamos
                                que crees un nuevo ticket.                                

                                <br/>
                                <br/>
                                <a  href="https://youtu.be/KoXmQshAAWI">https://youtu.be/KoXmQshAAWI</a>
                            </p>
                        </div>     
                        <div className="text-class">
                            <h3 className="subtitle-class">¿Puedo dar retroalimentación del servicio que se me brindó?</h3>
                            <p>
                                ¡Claro que puedes!, en la opción de <strong>Ticket {">"} Calificación de servicio de calidad</strong>
                                puedes dar una calificación del servicio que se te brindó. De primero selecciona el ticket al que quieres
                                calificar y luego se te muestra un pequeño formulario que te indicará la calificación en términos de 
                                tiempo de servicio, calidad del servicio y satisfacción con el servicio brindado. Luego le das en enviar
                                y listo nuestros administradores podrán ver anónimamente tu calificación y mejorar el servicio.
                            </p>
                        </div>     
                        <div className="text-class">
                            <h3 className="subtitle-class">¡No puedo iniciar sesión!</h3>
                            <p>
                                Si aparece al iniciar sesión aparece el error de <strong>Usuario bloqueado</strong> entonces
                                tienes que esperar a que un administrador te desbloquee o crear otro usuario. Ya que TicketSys 
                                aún no maneja un servicio para comunicar a los clientes con el administrador para desbloquear la cuenta.
                            </p>
                        </div>     
                    </div>
            </div>
        </div >
    )
}