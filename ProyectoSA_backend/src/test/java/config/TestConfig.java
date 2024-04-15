package config;

import com.spring.tiketsys.repository.*;
import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.security.jwt.JwtProvider;
import com.spring.tiketsys.service.*;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {




    @Bean
    public SurveyService surveyServiceMock() {
        return Mockito.mock(SurveyService.class);
    }

    @Bean
    public SurveyRepository surveyRepositoryMock() { return Mockito.mock(SurveyRepository.class); }

    @Bean
    public TicketService ticketServiceMock() { return Mockito.mock(TicketService.class); }

    @Bean
    public TicketRepository ticketRepositoryMock() { return Mockito.mock(TicketRepository.class); }

    @Bean
    public TicketTrackingService ticketTrackingServiceMock() { return Mockito.mock(TicketTrackingService.class); }

    @Bean
    public TicketTrackingRepository ticketTrackingRepositoryMock() { return Mockito.mock(TicketTrackingRepository.class); }

    @Bean
    public TicketElementService ticketElementServiceMock() { return Mockito.mock(TicketElementService.class); }
    @Bean
    public TicketElementRepository ticketElementRepositoryMock() { return Mockito.mock(TicketElementRepository.class); }

    @Bean
    public JwtChecker jwtCheckerMock() { return Mockito.mock(JwtChecker.class); }

    @Bean
    public JwtProvider jwtProviderMock() { return Mockito.mock(JwtProvider.class); }

    @Bean
    public UserService userServiceMock() { return Mockito.mock(UserService.class); }

    @Bean
    public UserRepository userRepositoryMock() { return Mockito.mock(UserRepository.class); }

    @Bean
    public TicketTypeService ticketTypeServiceMock() { return Mockito.mock(TicketTypeService.class); }

    @Bean
    public TicketTypeRepository ticketTypeRepository() { return Mockito.mock(TicketTypeRepository.class); }
    @Bean
    public TicketTypeRepository ticketTypeRepositoryMock() { return Mockito.mock(TicketTypeRepository.class); }

    @Bean
    public TicketPriorityService ticketPriorityServiceMock() { return Mockito.mock(TicketPriorityService.class); }
    @Bean
    public TicketPriorityRepository ticketPriorityRepositoryMock() { return Mockito.mock(TicketPriorityRepository.class); }

    @Bean
    public State_of_TicketService stateOfTicketServiceMock() { return Mockito.mock(State_of_TicketService.class); }

    @Bean
    public State_of_TicketRepository stateOfTicketRepositoryMock() { return Mockito.mock(State_of_TicketRepository.class); }

    @Bean
    public History_of_CommunicationService historyOfCommunicationServiceMock() { return Mockito.mock(History_of_CommunicationService.class); }

    @Bean
    public History_of_CommunicationRepository historyOfCommunicationRepositoryMock() { return Mockito.mock(History_of_CommunicationRepository.class); }

    @Bean
    public UserTypeService userTypeServiceMock() { return Mockito.mock(UserTypeService.class);}

    @Bean
    public UserTypeRepository userTypeRepositoryMock() { return Mockito.mock(UserTypeRepository.class); }




}