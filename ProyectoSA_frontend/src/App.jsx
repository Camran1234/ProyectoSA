import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { SesionProvider } from './context/sesion'
import 'bootstrap/dist/css/bootstrap.min.css';
import { Home } from './pages/Home/Home';
import { CreateTicket } from './pages/Home/Client/TicketCreation/CreateTicket';
import { Login } from './pages/Home/Client/Login/Login';
import TicketVisualization from './pages/Home/Client/TicketVisualization/TicketVisualization';
import { ProtectedRouteLogin } from './interceptors/ProtectedRouteLogin';
import PrivateRoute from './interceptors/PrivateRoute';

function App() {

  return (
    <BrowserRouter>
      <SesionProvider>
        <Routes>
          
          <Route index path="/" element={<Home />} />
          <Route path="/createTicket" element={<CreateTicket />} />          
          <Route path="/seekTicket" element={<TicketVisualization />} />

          {
            //Para redirigir del login al correspondiente si ya hay sesion
          }

          <Route element={<ProtectedRouteLogin redirectTo="/" />}>
            <Route path="/login" element={<Login />} />            
          </Route>

          {
          //Para el admin y el agente
          }
          <Route element={<PrivateRoute redirectTo="/login" allowedRoles={[3,2]} />}>
            <Route path="/admin" element={<Home />} />
            <Route path="/agente" element={<Home />} />
          </Route>

          

        </Routes>
      </SesionProvider>
    </BrowserRouter>
  )
}

export default App
