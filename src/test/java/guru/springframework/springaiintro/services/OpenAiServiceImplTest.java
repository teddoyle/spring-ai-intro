package guru.springframework.springaiintro.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenAiServiceImplTest {

    @Autowired
    OpenAIService openAIService;
   @Disabled
    @Test
    void getAnswer() {
        String answer = openAIService.getAnswer("Tell me a dad joke.");
        System.out.println("Got the answer");
        System.out.println(answer);

    }
}