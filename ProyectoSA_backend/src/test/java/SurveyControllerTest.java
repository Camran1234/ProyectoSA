import com.spring.tiketsys.TiketsysApplication;
import com.spring.tiketsys.config.Encrypter;
import com.spring.tiketsys.controller.survey.SurveyController;
import com.spring.tiketsys.dto.entity.*;
import com.spring.tiketsys.dto.model.SurveyDTO;
import com.spring.tiketsys.repository.SurveyRepository;
import com.spring.tiketsys.repository.TicketPriorityRepository;
import com.spring.tiketsys.repository.TicketTypeRepository;
import com.spring.tiketsys.repository.UserRepository;
import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.service.SurveyService;
import com.spring.tiketsys.service.TicketService;
import com.spring.tiketsys.service.TicketTrackingService;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Import(SurveyController.class)
@WebFluxTest(SurveyController.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        TiketsysApplication.Tests.class,
        Encrypter.class
})
public class SurveyControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    SurveyService surveyService;
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    TicketService ticketService;
    @Autowired
    TicketTrackingService ticketTrackingService;
    @Autowired
    JwtChecker jwtChecker;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketTypeRepository ticketTypeRepository;
    @Autowired
    TicketPriorityRepository ticketPriorityRepository;
    @Before
    public void setUp(){
        reset(surveyService);
        reset(ticketService);
        reset(ticketTrackingService);
        reset(jwtChecker);
    }

    // /api/survey
    @Test
    public void responseSurvey() throws Exception{
        String authorizationHeader = "Bearer your_token_here";
        User user = new User(
                "testUser",
                "admin",
                "test name",
                "test lastname",
                "0000-0000",
                new UserType(),
                false
        );
        TicketType ticketType = new TicketType(1, "tecnico");
        TicketPriority ticketPriority = new TicketPriority(1, "baja");

        // Usar matchers para todos los argumentos
        Ticket ticket = new Ticket(
                "", // nombre
                "", // descripción
                "", // estado
                "", // resumen
                "", // solución
                ticketType, // tipo de ticket
                ticketPriority, // prioridad del ticket
                user // usuario
        );

        when(jwtChecker.getSubject(anyString())).thenReturn("Not null :D");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);
        when(ticketService.getReferenceById(anyInt())).thenReturn(ticket);
        when(ticketTrackingService.findById(anyInt())).thenReturn(new TicketTracking(
                1,
                new State_of_Ticket(1, "nuevo"),
                new Date(),
                new Date(),
                null,
                false,
                ticket)
        );



        Map<String, Object> map = new HashMap<>();
        map.put("ticketNumber", 1);
        map.put("satisfaction", 2);
        map.put("timeService", 3);
        map.put("qualityService", 4);

        webTestClient.post()
                .uri("/api/survey/saveSurvey")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .bodyValue(map)
                .exchange()
                .expectStatus().isOk();

        verify(jwtChecker, times(1)).checkJWT(authorizationHeader);
        verify(surveyService, times(1)).saveSurvey(any(Survey.class));
        verify(ticketTrackingService, times(1)).saveTicket(any(TicketTracking.class));
    }

    @Test
    public void getSurveys() throws Exception {
        String authorizationHeader = "Bearer Token";
        List<Map<String,Object>> surveyDTOS = new ArrayList<>();



        when(jwtChecker.getSubject(anyString())).thenReturn("Not null :D");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);
        when(surveyService.getSurveys()).thenReturn(surveyDTOS);
        when(ticketService.getTicketsUnsolved()).thenReturn(surveyDTOS);
        when(ticketService.getTicketsUnqualified()).thenReturn(surveyDTOS);

        webTestClient.post()
                .uri("/api/survey/getDataReport")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isOk();

        verify(jwtChecker, times(1)).checkJWT(authorizationHeader);
        verify(surveyService, times(1)).getSurveys();


    }


}