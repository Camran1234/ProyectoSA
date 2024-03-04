import Swal from 'sweetalert2';

// Función para mostrar un mensaje de éxito
export const showSuccessMessage = (title, message) => {
  Swal.fire({
    icon: 'success',
    title: title,
    text: message,
    confirmButtonText: 'OK'
  });
}

// Función para mostrar un mensaje de error
export const showErrorMessage = (title, message) => {
  Swal.fire({
    icon: 'error',
    title: title,
    text: message,
    confirmButtonText: 'OK'
  });
}
