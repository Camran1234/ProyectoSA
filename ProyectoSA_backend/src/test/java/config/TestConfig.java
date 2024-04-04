package config;

import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.service.SurveyService;
import com.spring.tiketsys.service.TicketService;
import com.spring.tiketsys.service.TicketTrackingService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Autowired
    SurveyService surveyService;
    @Autowired
    TicketService ticketService;


    @Bean
    public SurveyService surveyServiceMock() {
        return Mockito.mock(SurveyService.class);
    }

    @Bean
    public TicketService ticketServiceMock() { return Mockito.mock(TicketService.class); }




}