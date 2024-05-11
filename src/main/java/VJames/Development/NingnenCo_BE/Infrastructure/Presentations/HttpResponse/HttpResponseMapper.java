package VJames.Development.NingnenCo_BE.Infrastructure.Presentations.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseMapper {
    public static ResponseEntity<Map<String,Object>> buildResponse(HttpStatus httpStatus, Object value) {
        Map<String, Object> responseBody = new HashMap<>();
        Field[] fields = value.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (!field.getName().equals("httpStatus")) {
                    responseBody.put(field.getName(), field.get(value));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        responseBody.put("responseCreateAt", Date.from(Instant.now()).toString());
        return ResponseEntity.status(httpStatus.value()).body(responseBody);
    }
}
