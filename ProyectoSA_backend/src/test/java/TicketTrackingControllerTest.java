import com.spring.tiketsys.TiketsysApplication;
import com.spring.tiketsys.config.Encrypter;
import com.spring.tiketsys.controller.ticket.TicketTrackingController;
import com.spring.tiketsys.dto.entity.State_of_Ticket;
import com.spring.tiketsys.dto.entity.TicketTracking;
import com.spring.tiketsys.dto.entity.User;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Import(TicketTrackingController.class)
@WebFluxTest(TicketTrackingControllerTest.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        TiketsysApplication.Tests.class,
        Encrypter.class
})
public class TicketTrackingControllerTest {

    @Autowired
    private TicketTrackingService ticketTrackingService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private State_of_TicketService stateOfTicketService;
    @Autowired
    private History_of_CommunicationService historyOfCommunicationService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtChecker jwtChecker;
    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        reset(ticketTrackingService);
        reset(ticketService);
        reset(stateOfTicketService);
        reset(historyOfCommunicationService);
        reset(userService);
        reset(jwtChecker);
    }
    @Test
    public void changeTicketStateOk() throws Exception {
        final String authorizationHeader = "Bearer token";
        final int state = 2;
        final int ticketNumber = 1;
        Map<String, Object> body = new HashMap<>();
        body.put("state", state);
        body.put("ticketNumber", ticketNumber);
        TicketTracking ticketTracking = mock( TicketTracking.class );

        when(jwtChecker.initCheck(anyString())).thenReturn(true);
        when(stateOfTicketService.getReferencedById(state)).thenReturn(new State_of_Ticket(
                state,
                "en curso"
        ));
        when(ticketTrackingService.findById(ticketNumber)).thenReturn(ticketTracking);
        when(ticketTracking.getTicketNumber()).thenReturn(ticketNumber);

        webTestClient.post()
                .uri("/api/tracking/change-state")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk();

        verify(jwtChecker, times(1)).initCheck(authorizationHeader);

    }

    @Test
    public void changeTicketStateBadRequest() throws Exception {
        final String authorizationHeader = "Bearer token";
        final int state = 2;
        final int ticketNumber = 1;
        Map<String, Object> body = new HashMap<>();
        body.put("state", state);
        body.put("ticketNumber", ticketNumber);


        when(jwtChecker.initCheck(anyString())).thenReturn(true);
        when(stateOfTicketService.getReferencedById(state)).thenReturn(new State_of_Ticket(
                state,
                "en curso"
        ));


        webTestClient.post()
                .uri("/api/tracking/change-state")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .bodyValue(body)
                .exchange()
                .expectStatus().isBadRequest();

        verify(jwtChecker, times(1)).initCheck(authorizationHeader);

    }

    @Test
    public void solve() throws Exception{
        final String authorizationHeader = "Bearer token";
        final int ticketNumber = 1;
        Map<String, Object> body = new HashMap<>();
        body.put("ticketNumber", ticketNumber);
        TicketTracking ticketTracking = mock( TicketTracking.class );

        when(jwtChecker.initCheck(anyString())).thenReturn(true);
        when(ticketTrackingService.findById(ticketNumber)).thenReturn(ticketTracking);
        when(stateOfTicketService.getByState("cerrado")).thenReturn(new State_of_Ticket(
                4,
                "cerrado"
        ));
        when(ticketTrackingService.saveTicket(ticketTracking)).thenReturn(ticketTracking);
        when(ticketTracking.getTicketNumber()).thenReturn(ticketNumber);

        webTestClient.post()
                .uri("/api/tracking/solve")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk();

        verify(jwtChecker, times(1)).initCheck(authorizationHeader);
    }

    @Test
    public void getLogs() {
        final int ticketNumber = 1;
        Map<String, Object> body = new HashMap<>();
        body.put("ticketNumber", ticketNumber);

        List<Map<String,Object>> logs = new ArrayList<>();
        when(historyOfCommunicationService.getLogs(ticketNumber)).thenReturn(logs);

        webTestClient.post()
                .uri("/api/tracking/trackLogs")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk();
        verify(historyOfCommunicationService, times(1)).getLogs(ticketNumber);
    }

    @Test
    public void assignAgent() throws Exception{
        final String authorizationHeader = "Bearer token";
        final String api = "/api/tracking/assign-agent";
        final String subject = "subject";
        final int ticketNumber = 1;
        final int state = 2;

        Map<String,Object> body = new HashMap<>();
        body.put("ticketNumber", ticketNumber);

        User user = mock( User.class );
        TicketTracking ticketTracking = mock( TicketTracking.class );
        State_of_Ticket stateOfTicket = new State_of_Ticket(
                state,
                "en curso"
        );


        when(jwtChecker.initCheck(anyString())).thenReturn(true);
        when(jwtChecker.getSubject(authorizationHeader)).thenReturn(subject);
        when(userService.getUser(subject)).thenReturn(user);
        when(ticketTrackingService.findById(ticketNumber)).thenReturn(ticketTracking);
        when(stateOfTicketService.getReferencedById(state)).thenReturn(stateOfTicket);
        when(ticketTrackingService.findById(ticketNumber)).thenReturn(ticketTracking);
        when(ticketTrackingService.saveTicket(ticketTracking)).thenReturn(ticketTracking);
        when(ticketTracking.getTicketNumber()).thenReturn(ticketNumber);
        when(ticketTracking.getAgent()).thenReturn(user);
        when(user.getName()).thenReturn("name");
        when(user.getLastName()).thenReturn("lastName");
        when(user.getUsername()).thenReturn("username");

        webTestClient.post()
                .uri(api)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated();

        verify(userService, times(1)).getUser(subject);
        verify(historyOfCommunicationService, times(1)).saveLog(eq(ticketTracking), anyString());
    }

    @Test
    public void getAgentTickets() throws Exception{
        final String authorizationHeader = "Authroization";
        final String subject = "subject";

        final int ticketNumber = 1;
        User user = mock( User.class );
        List<Map<String,Object>> tokens = new ArrayList<>();
        Map<String, Object> token = new HashMap<>();
        token.put("ticketNumber", ticketNumber);
        tokens.add(token);



        when(jwtChecker.initCheck(authorizationHeader)).thenReturn(true);
        when(jwtChecker.getSubject(authorizationHeader)).thenReturn(subject);
        when(jwtChecker.getSubjectType(authorizationHeader)).thenReturn("2");
        when(userService.getUser(subject)).thenReturn(user);
        when(user.getIdUser()).thenReturn(1);
        when(ticketTrackingService.getAgentTickets(anyInt())).thenReturn(tokens);
        when(ticketService.getTicketsElements(ticketNumber)).thenReturn(new ArrayList<>());

        webTestClient.get()
                .uri("/api/tracking/get-agent-all")
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isOk();

        verify(userService, times(1)).getUser(subject);
        verify(ticketTrackingService, times(1)).getAgentTickets(1);

    }

    @Test
    public void visualize() {
        final int ticketNumber = 1;
        Map<String, Object> body = new HashMap<>();
        body.put("ticketNumber", ticketNumber);

        TicketTracking ticketTracking = mock(TicketTracking.class);
        when(ticketTrackingService.findById(ticketNumber)).thenReturn(ticketTracking);

        webTestClient.post()
                .uri("/api/tracking/visualize")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void viewTicket() throws Exception{
        final String authorizationHeader = "Authorization";
        final String subject = "subject";
        final int ticketNumber = 1;

        TicketTracking ticketTracking = mock( TicketTracking.class );
        Map<String,Object> body = new HashMap<>();
        body.put("ticketNumber", ticketNumber);


        when(jwtChecker.getSubject(authorizationHeader)).thenReturn(subject);
        when(jwtChecker.checkSession(anyString())).thenReturn(true);
        when(ticketTrackingService.findById(anyInt())).thenReturn(ticketTracking);
        when(historyOfCommunicationService.getLogs(anyInt())).thenReturn(new ArrayList<>());

        webTestClient.post()
                .uri("/api/tracking/visualizeTicket")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk();

    }
}