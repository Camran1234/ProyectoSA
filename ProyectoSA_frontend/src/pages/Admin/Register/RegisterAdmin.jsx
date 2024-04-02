import { useEffect } from "react"
import { NavBar } from "../NavBar/NavBar"
import FormRegister from "./Components/FormRegister"

export const RegisterAdmin = () => {
    useEffect(() => {

        document.title = "Administrador"
    }, [])

    return (
        <div className="adminContainer">
            <NavBar />
            <FormRegister />
        </div>
    )

}