export const HttpService = {};
const API_BACKEND = import.meta.env.VITE_API;

HttpService.get = async (api,data) => {
    try {
        // Construir la URL con los parámetros
        const url = new URL(API_BACKEND+api);
        if (data) {
            // Convertir el objeto data a una cadena de consulta
            const queryString = Object.entries(data).map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`).join('&');
            url.search = '?' + queryString;
        }

        // Realizar la petición GET utilizando fetch
        const response = await fetch(url);
        
        if (!response.ok) {
            throw new Error('La petición no fue exitosa.');
        }
        
        // Parsear la respuesta como JSON
        const responseData = await response.json();
        return responseData;
    } catch (error) {
        // Manejar errores aquí
        console.error('Error al hacer la petición GET:', error);
        throw error;
    }
}

HttpService.getProtected = async (url, token) => {

}

HttpService.post = async (api, data) => {
    try {
        const url = API_BACKEND+api;
        const response = await fetch(url, {
            method: 'POST',
            headers: {
            'Content-Type': 'application/json', // Asegúrate de ajustar los encabezados según sea necesario
            'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(data) // Convierte los datos a formato JSON
        });

        console.log(response);
        if (!response.ok || !response.status === 201) {
            throw new Error(`Error! ${response.json()}`);
        }

        // Si la solicitud es exitosa, devuelve los datos de la respuesta
        return response.json(); // Devuelve una promesa que resuelve con los datos de la respuesta en formato JSON
    } catch (error) {
        console.error('Error al realizar la solicitud POST:', error);
        throw error; // Lanza el error para que sea manejado por el llamador del método
    }
}

HttpService.postProtected = async(api,data,token) => {
    try {
        const url = API_BACKEND+api;
        const response = await fetch(url, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`, // Incluye el token de autorización en el encabezado
            'Access-Control-Allow-Origin': '*'
          },
          body: JSON.stringify(data)
        });
  
        if (!response.ok || !response.status === 201) {
            console.log("Error! in status "+response.status);
            throw new Error(`Error! ${response.json()}`);
        }
        console.log("Sucess!");        
        return response.json();
    } catch (error) {
        console.error('Error del servidor:', error);
        throw error;
    }
}

HttpService.postFormData = async(api,formData) => {
    try {
        const url = API_BACKEND+api;
        const response = await fetch(url, {
          method: 'POST',
          headers: {
            'Access-Control-Allow-Origin': '*'
          },
          body: formData
        });
        console.log("Respuesta: ");
        console.log(response);
        if (!response.ok || !response.status === 201) {
          throw new Error(`Error! ${response.json()}`);
        }        
        return response.json();
      } catch (error) {
        console.error('Error al realizar la solicitud POST con FormData:', error);
        throw error;
      }
}

HttpService.postProtectedFormData = async(url,data,token) => {

}