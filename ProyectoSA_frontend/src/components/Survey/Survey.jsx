import React, { useState } from 'react';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';
import filledStar from '../../assets/filled_star.png';
import emptyStar from '../../assets/empty_star.png';
import { Button } from 'react-bootstrap';
import { HttpService } from '../../Services/HttpService';
import { getToken } from '../../Services/userHandler';
const MySwal = withReactContent(Swal);


const Survey = ({ticket}) => {
    const [ratings, setRatings] = useState({ 
      time: 0,
      quality: 0,
      satisfaction: 0
    });
  
    const handleRatingChange = (category, value) => {
      setRatings(prevState => ({
        ...prevState,
        [category]: value
      }));
    };
  
    const handleSubmit = () => {
        const { time, quality, satisfaction } = ratings;
        // Aquí puedes hacer lo que necesites con los valores de las calificaciones
        console.log("Tiempo de servicio:", time);
        console.log("Calidad del servicio:", quality);
        console.log("Satisfacción con el servicio:", satisfaction);
       
        HttpService.postProtected(`/api/survey/saveSurvey`, {
            ticketNumber: ticket.ticketNumber,
            satisfaction: ratings.satisfaction,
            qualityService: ratings.quality,
            timeService: ratings.time}, getToken())
        .then((response) => {
            console.log(response);
            Swal.fire({
                title: 'Encuesta enviada',
                text: 'Gracias por calificar el servicio',
                icon: 'success',
                confirmButtonText: 'Aceptar',
                allowOutsideClick: false,
            }).then(() => {
                // Cierra el modal de SweetAlert
                window.location.reload();
                Swal.close();                
            });
        }).catch((error) => {
            console.log(error);
            Swal.fire({
                title: 'Error',
                text: 'Error al enviar la encuesta',
                icon: 'error',
                confirmButtonText: 'Aceptar',
                allowOutsideClick: false,
            });
        });        
    };
  
    return (
      <div>
        <div style={{ textAlign: 'center' }}>
          <p style={{ marginBottom: '10px' }}>Tiempo de Servicio</p>
          {[1, 2, 3, 4, 5].map((value, index) => (
            <img
              key={index}
              src={ratings.time >= value ? filledStar : emptyStar}
              alt="Rating"
              onClick={() => handleRatingChange('time', value)}
              style={{ width: '40px', cursor: 'pointer', marginRight: '5px' }}
            />
          ))}
        </div>
        <div style={{ textAlign: 'center', marginTop: '20px' }}>
          <p style={{ marginBottom: '10px' }}>Calidad del Servicio</p>
          {[1, 2, 3, 4, 5].map((value, index) => (
            <img
              key={index}
              src={ratings.quality >= value ? filledStar : emptyStar}
              alt="Rating"
              onClick={() => handleRatingChange('quality', value)}
              style={{ width: '40px', cursor: 'pointer', marginRight: '5px' }}
            />
          ))}
        </div>
        <div style={{ textAlign: 'center', marginTop: '20px' }}>
          <p style={{ marginBottom: '10px' }}>Satisfacción con el Servicio</p>
          {[1, 2, 3, 4, 5].map((value, index) => (
            <img
              key={index}
              src={ratings.satisfaction >= value ? filledStar : emptyStar}
              alt="Rating"
              onClick={() => handleRatingChange('satisfaction', value)}
              style={{ width: '40px', cursor: 'pointer', marginRight: '5px' }}
            />
          ))}
        </div>
        <div style={{ marginTop: '20px', textAlign: 'center' }}>
          <button style={{ marginRight: '10px' }} onClick={handleSubmit}>Calificar</button>          
        </div>
      </div>
    );
  };

export const SurveyModal = ({ticket}) => {    

    const openSwal = () => {
        MySwal.fire({
            title: 'Encuesta de Servicio',
            html: <Survey ticket={ticket} />,
            showCloseButton: true,
            showConfirmButton: false,
            focusConfirm: false,
            allowOutsideClick: false,
        });
    };

    return (
        <Button 
            variant="primary" 
            className='custom-accept-button'
            onClick={() => openSwal()}
        >
            Calificar
        </Button>
    );
};
  
  

