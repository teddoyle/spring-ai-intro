package guru.springframework.springaiintro.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.springaiintro.model.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptWithInfo;

    @Value("classpath:templates/get-capital-with-info-in-json.st")
    private Resource getCapitalPromptWithJsonInfo;

    public OpenAiServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        System.out.println("getCapitalWithInfo was called");
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
        return new Answer(chatClient.prompt(prompt).call().content());
    }
    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest capitalRequest) {
        BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser<>(GetCapitalResponse.class);
        String format = parser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", capitalRequest.stateOrCountry(),
                "format", format));
        String jsonResponse = this.chatClient.prompt(prompt).call().content();
        System.out.println(jsonResponse);

        return parser.parse(jsonResponse);
    }

    @Override
    public GetCapitalResponseWithInfo getCapitalWithParsedInfo(GetCapitalRequest getCapitalRequest) {
        BeanOutputParser<GetCapitalResponseWithInfo> parser = new BeanOutputParser<>(GetCapitalResponseWithInfo.class);
        String format = parser.getFormat();
    System.out.println("Format: " + format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));
        String jsonResponse = this.chatClient.prompt(prompt).call().content();
        System.out.println(jsonResponse);

        return parser.parse(jsonResponse);
    }

    @Override
    public Answer getAnswer(Question question) {
        System.out.println("getAnswer was called with a Question record");
        return new Answer(this.chatClient.prompt().user(question.question()).call().content());
    }

    @Override
    public String getAnswer(String question) {
        System.out.println("getAnswer was called with a string");
        return this.chatClient.prompt().user(question).call().content();

    }
}
