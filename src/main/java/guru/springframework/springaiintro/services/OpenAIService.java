package guru.springframework.springaiintro.services;

// import java.lang.String;

import guru.springframework.springaiintro.model.*;

public interface OpenAIService {

    // public GetCapitalResponseWithInfo getCapitalWithJsonInfo(GetCapitalRequest getCapitalRequest);
    GetCapitalResponseWithInfo getCapitalWithParsedInfo(GetCapitalRequest getCapitalRequest);
    Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);
    String getAnswer(String question);

    Answer getAnswer(Question question);
}