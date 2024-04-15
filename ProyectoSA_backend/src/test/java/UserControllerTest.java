import com.spring.tiketsys.TiketsysApplication;
import com.spring.tiketsys.config.Encrypter;
import com.spring.tiketsys.controller.ticket.TicketTrackingController;
import com.spring.tiketsys.controller.user.UserController;
import com.spring.tiketsys.dto.entity.User;
import com.spring.tiketsys.dto.entity.UserType;
import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.security.jwt.JwtProvider;
import com.spring.tiketsys.service.UserService;
import com.spring.tiketsys.service.UserTypeService;
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
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@Import(UserController.class)
@WebFluxTest(UserController.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        TiketsysApplication.Tests.class,
        Encrypter.class
})
public class UserControllerTest {
    @Autowired
    UserService userService;
    @Autowired
    UserTypeService userTypeService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtChecker jwtChecker;
    @Autowired
    Encrypter encrypter;

    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setUp(){
        reset(userService);
        reset(userTypeService);
        reset(jwtProvider);
        reset(jwtChecker);
    }

    @Test
    public void helloWorld() {
        webTestClient.get()
                .uri("/api/user/")
                .exchange()
                .expectBody(String.class)
                .isEqualTo("HelloWorld");
    }

    @Test
    public void register() throws Exception{
        final String username = "username";
        final String password = "password";
        final String userType = "cliente";
        final String name = "name";
        final String lastName = "lastName";
        final String phone = "0000-0000";

        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        body.put("name", name);
        body.put("lastName", lastName);
        body.put("phone", phone);
        body.put("userType", userType);

        when(userTypeService.getByType(userType)).thenReturn(new UserType(
                1,
                userType
        ));

        webTestClient.post()
                .uri("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void registerUserProtected() throws Exception{
        final String username = "username";
        final String password = "password";
        final String userType = "cliente";
        final String name = "name";
        final String lastName = "lastName";
        final String phone = "0000-0000";
        final String authorizationHeader = "Authorization";

        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        body.put("name", name);
        body.put("lastName", lastName);
        body.put("phone", phone);
        body.put("userType", userType);

        when(jwtChecker.getSubject(anyString())).thenReturn("subject");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);
        when(userTypeService.getByType(userType)).thenReturn(new UserType(
                1,
                userType
        ));

        webTestClient.post()
                .uri("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void getUsers() throws Exception{
        final String authorizationHeader = "Authorization";

        when(jwtChecker.getSubject(anyString())).thenReturn("subject");
        when(jwtChecker.checkSession(anyString())).thenReturn(true);
        when(userService.getAllSecure(anyString())).thenReturn(new ArrayList<>());

        webTestClient.post()
                .uri("/api/user/get-users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void login() throws Exception{
        final String username = "username";
        final String password = "password";
        String encryptedPassword = encrypter.encrypt(password);
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        User user = mock(User.class);
        UserType userType = new UserType(
                1,
                "cliente"
        );
        when(userService.getUser(username)).thenReturn(user);
        when(user.isBlocked()).thenReturn(false);

        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(encryptedPassword);
        when(user.getUserType()).thenReturn(userType);

        webTestClient.post()
                .uri("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void changeBlock() throws Exception{
        final String authorizationHeader = "Authorization";
        final String username = "username";
        final int blocked = 1;
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("blocked", blocked);
        User user = mock( User.class);
        when(jwtChecker.getSubject(anyString())).thenReturn("subject");
        when(userService.getUser(anyString())).thenReturn(user);

        webTestClient.post()
                .uri("/api/user/changeBlock")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authorizationHeader)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("{}");

    }

    @Test
    public void validateJwt(){
        final String authorizationHeader = "Authorization";

        when(jwtChecker.getSubject(authorizationHeader)).thenReturn("subject");

        webTestClient.post()
                .uri("/api/user/validate")
                .header("Authorization", authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getUser() {
        final String authorizationHeader = "Authorization";
        final String subject = "subject";
        final String subjectType = "cliente";

        when(jwtChecker.getSubject(authorizationHeader)).thenReturn(subject);
        when(jwtChecker.getSubjectType(authorizationHeader)).thenReturn(subjectType);

        webTestClient.post()
                .uri("/api/user/get-users")
                .header("Authorization", authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

}