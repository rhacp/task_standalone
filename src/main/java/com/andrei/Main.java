package com.andrei;

import com.andrei.exceptions.BadInputException;
import com.andrei.exceptions.MalformedJSONException;
import com.andrei.models.Input;
import com.andrei.models.Result;
import com.andrei.utils.properties.PropertiesLoader;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Input input = getConvertJsonToInput();

        checkForWrongInput(input);

        Double result = calculation(input);

        String callResult = makeCallToRestEndpoint(input.getOperation_number(), result);

        System.out.println(callResult);
    }

    /**
     * Convert input.json to Input object type.
     * @return Input converted Input object
     * @exception com.andrei.exceptions.MalformedJSONException; if the file is not in a correct JSON format or if not edited properly.
     */
    public static Input getConvertJsonToInput() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();

        try {
            return objectMapper.readValue(new URL("file:src/main/resources/input.json"), Input.class);
        } catch (IOException e) {
            throw new MalformedJSONException("The JSON file is not properly edited. Malformed JSON.");
        }
    }

    /**
     * Check input object.
     * @param input Input object.
     * @exception BadInputException ; if any property is null.
     */
    public static void checkForWrongInput(Input input) {
        if (input.getOperation_number() == null || input.getInput_array() == null || input.getInput_array().isEmpty()) {
            throw new BadInputException("The input file has a null \"operation_numer\" or \"input_array\".");
        }
    }

    /**
     * Does the calculations according to the Input object.
     * @param input Input object.
     */
    public static Double calculation(Input input) {
        Double result = input.getOperation_number();

        for (int index = 0; index < input.getInput_array().size(); index++) {
            switch (input.getInput_array().get(index).getCommand()) {
                case APPEND -> result += input.getInput_array().get(index).getNumber();
                case REDUCE -> result -= input.getInput_array().get(index).getNumber();
                case MULTIPLY -> result *= input.getInput_array().get(index).getNumber();
                case DIVIDE -> result /= input.getInput_array().get(index).getNumber();
                case POWER -> result = Math.pow(result, input.getInput_array().get(index).getNumber());
            }
        }

        return result;
    }

    /**
     * Make the call to the REST endpoint.
     * @param operation_number operation number.
     * @param result result of the calculations.
     */
    public static String makeCallToRestEndpoint(Double operation_number, Double result) {
        Double test = 80.3D;

        Properties properties = PropertiesLoader.loadProperties("application.yaml");
        String callUrl = properties.getProperty("callUrlFirst").substring(1, properties.getProperty("callUrlFirst").length() - 1) + Integer.toString(test.intValue()) + properties.getProperty("callUrlSecond").substring(1, properties.getProperty("callUrlSecond").length() - 1);

        Result resultObject = new Result(result);

        WebClient client = WebClient.create();

        return client.post()
                .uri(callUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(resultObject), Result.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}