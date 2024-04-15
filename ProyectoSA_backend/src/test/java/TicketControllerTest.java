import com.spring.tiketsys.TiketsysApplication;
import com.spring.tiketsys.config.Encrypter;
import com.spring.tiketsys.controller.ticket.TicketController;
import com.spring.tiketsys.dto.entity.*;
import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.service.*;
import config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Import(TicketController.class)
@WebFluxTest(TicketController.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        TiketsysApplication.Tests.class,
        Encrypter.class
})
public class TicketControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketTrackingService ticketTrackingService;
    @Autowired
    private TicketTypeService ticketTypeService;
    @Autowired
    private TicketPriorityService ticketPriorityService;
    @Autowired
    private State_of_TicketService stateOfTicketService;
    @Autowired
    private History_of_CommunicationService historyOfCommunicationService;
    @Autowired
    private TicketElementService ticketElementService;
    @Autowired
    private JwtChecker jwtChecker;

    @Before
    public void setUp(){
        reset(jwtChecker);
        reset(ticketElementService);
        reset(historyOfCommunicationService);
        reset(stateOfTicketService);
        reset(ticketPriorityService);
        reset(ticketTypeService);
        reset(ticketTrackingService);
        reset(ticketService);
        reset(userService);

    }
    @Test
    public void createTicket() throws Exception {
        String authorizationHeader = "Bearer Token";
        TicketType ticketType = new TicketType(1, "tecnico Test");
        TicketPriority ticketPriority = new TicketPriority(1, "alta test");
        State_of_Ticket stateOfTicket = new State_of_Ticket(1, "nuevo test");

        User user = new User();

        Map<String, Object> map = new HashMap<>();
        map.put("ticketType", 1);
        map.put("ticketPriority", 1);
        map.put("files", new HashMap<>());
        map.put("email", "test@gmail.com");
        map.put("name", "test");
        map.put("lastName", "lastTest");
        map.put("phone", "0000-0000");
        map.put("description", "Metro boomin");

        Ticket ticket = new Ticket(
                "test@email.com",
                "test",
                "lastTest",
                "0000-0000",
                "Metro boomin",
                ticketType,
                ticketPriority,
                user
        );


        when(jwtChecker.getSubject(anyString())).thenReturn("Some subject here test");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);

        when(ticketTypeService.findById(anyInt())).thenReturn(ticketType);
        when(ticketPriorityService.findById(anyInt())).thenReturn(ticketPriority);

        when(userService.getUser(anyString())).thenReturn(user);
        when(stateOfTicketService.getReferencedById(anyInt())).thenReturn(stateOfTicket);
        when(ticketService.createTicket(any())).thenReturn(ticket);


        webTestClient.post()
                .uri("/api/ticket/create-ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .bodyValue(map)
                .exchange()
                .expectStatus().isCreated();

        verify(jwtChecker, times(1)).checkJWT(authorizationHeader);
        verify(ticketTypeService, times(1)).findById(1);
        verify(ticketPriorityService, times(1)).findById(1);
        verify(ticketTrackingService, times(1)).createTrack(ticket, stateOfTicket);
    }

    @Test
    public void getAllTicketsOk() throws Exception {
        String authorizationHeader = "Bearer token";
        final String subjectType = "2";
        // Configuración de comportamiento simulado para jwtChecker y ticketService
        when(jwtChecker.initCheck(authorizationHeader)).thenReturn(true);

        when(jwtChecker.getSubjectType(authorizationHeader)).thenReturn(subjectType);

        webTestClient.get()
                .uri("/api/ticket/getTickets-all")
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isOk();

        // Verifica que los métodos en jwtChecker hayan sido llamados correctamente
        verify(jwtChecker, times(1)).initCheck(authorizationHeader);
        verify(jwtChecker, times(1)).getSubjectType(authorizationHeader);

        // Verifica que el método en ticketService haya sido llamado correctamente
        verify(ticketService, times(1)).getUnsolvedTickets();
    }


    @Test
    public void getAllTicketsForbidden() throws Exception{
        String authorizationHeader = "Bearer token";
        final String subjectType = "2";
        // Configuración de comportamiento simulado para jwtChecker y ticketService
        when(jwtChecker.initCheck(authorizationHeader)).thenReturn(false);

        when(jwtChecker.getSubjectType(authorizationHeader)).thenReturn(subjectType);

        webTestClient.get()
                .uri("/api/ticket/getTickets-all")
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isForbidden();

        // Verifica que los métodos en jwtChecker hayan sido llamados correctamente
        verify(jwtChecker, times(1)).initCheck(authorizationHeader);
    }

    @Test
    public void getTicketsOk() throws Exception {
        final String email = "email@test.com";
        final String authorizationHeader = "Bearer Token";
        Map<String,Object> token = new HashMap<>();
        token.put("ticketNumber", "15");
        List<Map<String,Object>> listTokens = new ArrayList<>();
        listTokens.add(token);

        when(jwtChecker.getSubject(anyString())).thenReturn("Some subject");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);

        User user = mock( User.class );
        when(userService.getUser(anyString())).thenReturn(user);
        when(user.getIdUser()).thenReturn(1);
        when(ticketService.getTicketsEmail(anyString(), anyInt())).thenReturn(listTokens);
        when(ticketService.getTicketsElements(anyInt())).thenReturn(new ArrayList<>());

        webTestClient.get()
                .uri("/api/ticket/getTickets?email={email}", email)
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void getTicketsBadRequest() throws Exception {
        final String email = "email@test.com";
        final String authorizationHeader = "Bearer Token";
        Map<String,Object> token = new HashMap<>();
        token.put("ticketNumber", "15");
        List<Map<String,Object>> listTokens = new ArrayList<>();
        listTokens.add(token);

        when(jwtChecker.getSubject(anyString())).thenReturn("Some subject");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);


        when(userService.getUser(anyString())).thenReturn(new User());


        webTestClient.get()
                .uri("/api/ticket/getTickets?email={email}", email)
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void getTicketsSubjectOk() throws Exception{
        final String authorizationHeader = "Bearer Token";
        Map<String,Object> token = new HashMap<>();
        token.put("ticketNumber", "15");
        List<Map<String,Object>> listTokens = new ArrayList<>();
        listTokens.add(token);

        //Para la funcion checkJWT
        when(jwtChecker.getSubject(anyString())).thenReturn("subject not empty");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);

        User user = mock( User.class );
        when(userService.getUser(anyString())).thenReturn(user);
        when(user.getIdUser()).thenReturn(1);
        when(ticketService.getTicketsSubject(anyInt())).thenReturn(listTokens);

        when(ticketService.getTicketsElements(anyInt())).thenReturn(new ArrayList<>());

        webTestClient.get()
                .uri("/api/ticket/getTicketsSubject")
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    public void getTicketsSubjectBadRequest() throws Exception{
        final String authorizationHeader = "Bearer Token";
        Map<String,Object> token = new HashMap<>();
        token.put("ticketNumber", "15");
        List<Map<String,Object>> listTokens = new ArrayList<>();
        listTokens.add(token);

        //Para la funcion checkJWT
        when(jwtChecker.getSubject(anyString())).thenReturn("subject not empty");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);


        when(userService.getUser(anyString())).thenReturn(new User());


        webTestClient.get()
                .uri("/api/ticket/getTicketsSubject")
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void getTicketsWithoutSurveyOK() throws Exception{
        final String authorizationHeader = "Bearer Token";
        Map<String,Object> token = new HashMap<>();
        token.put("ticketNumber",1);
        List<Map<String,Object>> listTokens = new ArrayList<>();
        listTokens.add(token);


        //Para la funcion checkJWT
        when(jwtChecker.getSubject(anyString())).thenReturn("subject not empty");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);

        User user = mock(User.class);
        when(userService.getUser(anyString())).thenReturn(user);
        when(user.getIdUser()).thenReturn(1);
        when(ticketService.getTicketsWithoutSurvey(anyInt())).thenReturn(listTokens);
        when(ticketService.getTicketsElements(anyInt())).thenReturn(new ArrayList<>());

        webTestClient.get()
                .uri("/api/ticket/getTickets-no-survey")
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void getTicketsWithoutSurveyBadRequest() throws Exception{
        final String authorizationHeader = "Bearer Token";
        Map<String,Object> token = new HashMap<>();
        token.put("ticketNumber",1);
        List<Map<String,Object>> listTokens = new ArrayList<>();
        listTokens.add(token);


        //Para la funcion checkJWT
        when(jwtChecker.getSubject(anyString())).thenReturn("subject not empty");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);

        when(userService.getUser(anyString())).thenReturn(new User());


        webTestClient.get()
                .uri("/api/ticket/getTickets-no-survey")
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    public void getTicketsByTicketNumberOk() {
        final int ticketNumber = 1;
        Map<String,Object> token = new HashMap<>();
        token.put("ticketNumber",ticketNumber);
        when(ticketService.getTicketsbyNumber(anyInt())).thenReturn(token);
        when(ticketService.getTicketsElements(anyInt())).thenReturn(new ArrayList<>());

        webTestClient.get()
                .uri("/api/ticket/getTicket-ticketNumber?ticketNumber={ticketNumber}", ticketNumber)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getTicketsByTicketNumberBadRequest() {
        final String authorizationHeader = "Bearer token";
        final int ticketNumber = 1;

        when(ticketService.getTicketsbyNumber(anyInt())).thenReturn(new HashMap<>());


        webTestClient.get()
                .uri("/api/ticket/getTicket-ticketNumber?ticketNumber={ticketNumber}", ticketNumber)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void changeTicketPriorityOk() {
        Map<String,Object> body = new HashMap<>();
        body.put("ticketNumber", 1);
        body.put("priority", 3);

        when(ticketPriorityService.getReferencedById(anyInt())).thenReturn(new TicketPriority(
                3,
                "baja"
        ));

        when(ticketService.findById(anyInt())).thenReturn(new Ticket());
        when(ticketTrackingService.findById(anyInt())).thenReturn(new TicketTracking());
        TicketTracking ticketTracking = mock( TicketTracking.class );
        when(ticketTrackingService.saveTicket(any(TicketTracking.class))).thenReturn(ticketTracking);
        when(ticketTracking.getTicketNumber()).thenReturn(1);


        webTestClient.post()
                .uri("/api/ticket/change-priority")
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void changeTicketPriorityBadRequest() {
        Map<String,Object> body = new HashMap<>();
        body.put("ticketNumber", 1);
        body.put("priority", 3);

        when(ticketPriorityService.getReferencedById(anyInt())).thenReturn(new TicketPriority(
                3,
                "baja"
        ));

        when(ticketService.findById(anyInt())).thenReturn(new Ticket());

        when(ticketTrackingService.findById(anyInt())).thenReturn(null);


        webTestClient.post()
                .uri("/api/ticket/change-priority")
                .bodyValue(body)
                .exchange()
                .expectStatus().isBadRequest();
    }



}