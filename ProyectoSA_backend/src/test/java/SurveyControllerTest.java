import com.spring.tiketsys.TiketsysApplication;
import com.spring.tiketsys.config.Encrypter;
import com.spring.tiketsys.controller.survey.SurveyController;
import config.TestConfig;
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
        TiketsysApplication.Tests.class,
        Encrypter.class
})
public class SurveyControllerTest {
}
