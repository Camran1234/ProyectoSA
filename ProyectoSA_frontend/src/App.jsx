import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { SesionProvider } from './context/sesion'
import 'bootstrap/dist/css/bootstrap.min.css';
import { Home } from './pages/Client/Home';
import { CreateTicket } from './pages/Client/Client/TicketCreation/CreateTicket';
import { Login } from './pages/Home/Login/Login';
import TicketVisualization from './pages/Client/Client/TicketVisualization/TicketVisualization';
import { ProtectedRouteLogin } from './interceptors/ProtectedRouteLogin';
import PrivateRoute from './interceptors/PrivateRoute';
import { HomeAdmin } from './pages/Admin/Home/HomeAdmin';
import { RegisterAdmin } from './pages/Admin/Register/RegisterAdmin';
import { HomeAgent } from './pages/Agent/Home/HomeAgent';
import { TicketControl } from './pages/Agent/TicketControl/TicketControl';
import { AgentControl } from './pages/Agent/AgentControl/AgentControl';
import { Register } from './pages/Home/Register/Register';
import { Controller } from './pages/Admin/Controller/Controller';
import { SearchTicketSurvey } from './pages/Client/Client/SearchTicketSurvey/SearchTicketSurvey';


function App() {

  return (
    <BrowserRouter>
      <SesionProvider>
        <Routes>
          
          {
          //<Route index path="/" element={<Home />} />
          }        
          <Route element={<PrivateRoute redirectTo="/login" allowedRoles={[1,2]} />}>
            <Route path="/seekTicket" element={<TicketVisualization />} />
          </Route>
          {
            //Para redirigir del login al correspondiente si ya hay sesion
          }

          <Route element={<ProtectedRouteLogin redirectTo="/" />}>
            <Route path="/login" element={<Login />} />
            <Route index path="/" element={<Login />} />            
          </Route>
          <Route path="/register" element={<Register />} />

          { 
          //Para el cliente
          }
          <Route element={<PrivateRoute redirectTo="/login" allowedRoles={[1]} />}>
            <Route path="/home" element={<Home />} />
            <Route path="/createTicket" element={<CreateTicket />} />
            <Route path="/find-ticket" element={<Home />} />
            <Route path="/surveyTickets" element={<SearchTicketSurvey />} />
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
            <Route path="admin/controller" element={<Controller />} />
          </Route>

          

        </Routes>
      </SesionProvider>
    </BrowserRouter>
  )
}

export default App
