package uam.aleksy.deansoffice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static String serialize(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
