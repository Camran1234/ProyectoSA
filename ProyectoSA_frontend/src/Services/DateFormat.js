
export function formatDate(dateString) {
    
    try{
        // Parse the date string using Date.parse()
        const parsedDate = new Date(dateString);
        // Format the date using toLocaleDateString() and toLocaleTimeString() methods
        let formattedDate = parsedDate.toLocaleDateString("en-GB", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
        }) + " " + parsedDate.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
        });        
        if (formattedDate === "Invalid Date Invalid Date") {
            formattedDate = dateString;
        }
        return formattedDate;
    }catch(error){
        console.error('Error al formatear la fecha:', error);
        throw error;
    }    
}