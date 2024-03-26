import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { SesionProvider } from './context/sesion'
import 'bootstrap/dist/css/bootstrap.min.css';
import { Home } from './pages/Home/Home';
import { CreateTicket } from './pages/Home/Client/TicketCreation/CreateTicket';
import { Login } from './pages/Home/Client/Login/Login';
import TicketVisualization from './pages/Home/Client/TicketVisualization/TicketVisualization';
import { ProtectedRouteLogin } from './interceptors/ProtectedRouteLogin';
import PrivateRoute from './interceptors/PrivateRoute';
import { HomeAdmin } from './pages/Admin/Home/HomeAdmin';
import { RegisterAdmin } from './pages/Admin/Register/RegisterAdmin';
import { HomeAgent } from './pages/Agent/Home/HomeAgent';
import { TicketControl } from './pages/Agent/TicketControl/TicketControl';
import { AgentControl } from './pages/Agent/AgentControl/AgentControl';


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
          //Para el admin y el agente register-agent
          }
          <Route element={<PrivateRoute redirectTo="/login" allowedRoles={[2]} />}>
            
            <Route path="/agente" element={<HomeAgent />} />
            <Route path="/agente/ticket-control" element={<TicketControl />} />
            <Route path="/agente/ticket-attend" element={<AgentControl />} />
          </Route>

          <Route element={<PrivateRoute redirectTo="/login" allowedRoles={[3]} />}>
            <Route path="/admin" element={<HomeAdmin />} />
            <Route path="/admin/register" element={<RegisterAdmin />} />
          </Route>

          

        </Routes>
      </SesionProvider>
    </BrowserRouter>
  )
}

export default App
