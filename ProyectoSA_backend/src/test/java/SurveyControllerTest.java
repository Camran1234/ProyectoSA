import com.spring.tiketsys.controller.survey.SurveyController;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(SurveyController.class)
@WebFluxTest(SurveyController.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        TweetifyApplication.Tests.class,
        Encrypter.class
})
public class SurveyControllerTest {
}
