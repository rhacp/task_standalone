package com.andrei;

import com.andrei.exceptions.BadInputException;
import com.andrei.exceptions.MalformedJSONException;
import com.andrei.models.Input;
import com.andrei.models.Result;
import com.andrei.utils.properties.PropertiesLoader;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Properties;

@Slf4j
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        Input input = getConvertJsonToInput();

        checkForNullInput(input);

        Double result = calculation(input);

        String callResult = makeCallToRestEndpoint(input.getOperation_number(), result);

        log.info(callResult);
    }

    /**
     * Converts input.json to Input object type.
     * @return Input converted Input object
     * @exception com.andrei.exceptions.MalformedJSONException; if the file is not in a correct JSON format or if not edited properly.
     */
    public static Input getConvertJsonToInput() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();

        try {
            Input input = objectMapper.readValue(new ClassPathResource("input.json").getURL(), Input.class);
            log.info("JSON file mapped to Input object successfully.");

            return input;
        } catch (IOException e) {
            throw new MalformedJSONException("The JSON file is not properly edited. Malformed JSON.");
        }
    }

    /**
     * Checks input object.
     * @param input Input object.
     * @exception BadInputException ; if any property is null.
     */
    public static void checkForNullInput(Input input) {
        if (input.getOperation_number() == null || input.getInput_array() == null || input.getInput_array().isEmpty()) {
            throw new BadInputException("The input file has a null \"operation_number\" or \"input_array\".");
        }

        log.info("Input checked for null values.");
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

        log.info("Calculations done. Result ready.");

        return result;
    }

    /**
     * Makes the call to the REST endpoint from the "application.yaml" file along with the initial "operation_number".
     * @param operation_number operation number.
     * @param result result of the calculations.
     */
    public static String makeCallToRestEndpoint(Double operation_number, Double result) {
        Double test = 80.3D;

        Properties properties = PropertiesLoader.loadProperties("application.yaml");
        String callUrl = properties.getProperty("callUrlFirst").substring(1, properties.getProperty("callUrlFirst").length() - 1) + Integer.toString(test.intValue()) + properties.getProperty("callUrlSecond").substring(1, properties.getProperty("callUrlSecond").length() - 1);

        Result resultObject = new Result(result);

        WebClient client = WebClient.create();

        String callResponse = client.post()
                .uri(callUrl)
                .header("Accept-Language", properties.getProperty("header"))
                .header("Operation-Number", Integer.toString(test.intValue()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(resultObject), Result.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Call to the REST endpoint successfully made.");

        return callResponse;
    }
}