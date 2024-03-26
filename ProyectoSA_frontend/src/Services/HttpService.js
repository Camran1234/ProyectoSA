export const HttpService = {};
const API_BACKEND = import.meta.env.VITE_API;

HttpService.get = async (api,data) => {
    try {
        // Construir la URL con los parámetros
        const url = new URL(API_BACKEND+api);
        if (data) {
            // Convertir el objeto data a una cadena de consulta
            const queryString = Object.entries(data).map(([key, value]) => 
            `${encodeURIComponent(key)}=${encodeURIComponent(value)}`).join('&');
            url.search = '?' + queryString;
        }

        // Realizar la petición GET utilizando fetch
        const response = await fetch(url);
        
        if (!response.ok) {
            throw new Error(await response.json());
        }
        
        // Parsear la respuesta como JSON
        const responseData = await response.json();
        return responseData;
    } catch (error) {
        // Manejar errores aquí
        console.error('Error! GET: ', JSON.stringify(error));
        throw error;
    }
}

HttpService.getProtected = async (api, data, token) => {
    try {
        const url = new URL(API_BACKEND+api);
        if (data) {            
            const queryString = Object.entries(data).map(([key, value]) => 
            `${encodeURIComponent(key)}=${encodeURIComponent(value)}`).join('&');
            url.search = '?' + queryString;
        }

        
        const response = await fetch(url, {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`, 
              'Access-Control-Allow-Origin': '*'
            }
          });
        
        if (!response.ok || !response.status === 201) {     
            const responseData = await response.json();                   
            throw new Error(`${responseData.message}`);
        }
        
        
        const text = await response.text(); 
        if (!text.trim()) {
            
            return {};
        }
        
        const responseData = JSON.parse(text);
        return responseData; 
    } catch (error) {
        // Manejar errores aquí
        console.error('Error! GET: ', JSON.stringify(error));
        throw error;
    }
}

HttpService.post = async (api, data) => {
    try {
        const url = API_BACKEND+api;
        const response = await fetch(url, {
            method: 'POST',
            headers: {
            'Content-Type': 'application/json', 
            'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(data)
        });
        
        if (!response.ok || !response.status === 201) {     
            const responseData = await response.json();                   
            throw new Error(`${responseData.message}`);
        }
        
        
        const text = await response.text(); 
        if (!text.trim()) {
            
            return {};
        }
        
        const responseData = JSON.parse(text);
        return responseData; 
    } catch (error) {
        //console.error('Error al realizar la solicitud POST:', JSON.stringify(error));
        throw error; 
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
            const responseData = await response.json();                   
            throw new Error(`${responseData.message}`);
        }
        
        
        const text = await response.text(); 
        if (!text.trim()) {            
            return {};
        }
        
        const responseData = JSON.parse(text);
        return responseData; 
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
          throw new Error(`Error! ${await response.json()}`);
        }        
        return await response.json();
      } catch (error) {
        console.error('Error al realizar la solicitud POST con FormData:', error);
        throw error;
      }
}

HttpService.postProtectedFormData = async(url,data,token) => {

}