package config;

import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.security.jwt.JwtProvider;
import com.spring.tiketsys.service.SurveyService;
import com.spring.tiketsys.service.TicketService;
import com.spring.tiketsys.service.TicketTrackingService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {




    @Bean
    public SurveyService surveyServiceMock() {
        return Mockito.mock(SurveyService.class);
    }

    @Bean
    public TicketService ticketServiceMock() { return Mockito.mock(TicketService.class); }

    @Bean
    public TicketTrackingService ticketTrackingServiceMock() { return Mockito.mock(TicketTrackingService.class); }

    @Bean
    public JwtChecker jwtCheckerMock() { return Mockito.mock(JwtChecker.class); }

    @Bean
    public JwtProvider jwtProviderMock() { return Mockito.mock(JwtProvider.class); }




}