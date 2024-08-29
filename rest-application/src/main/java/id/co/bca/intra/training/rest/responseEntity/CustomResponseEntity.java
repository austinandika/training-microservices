package id.co.bca.intra.training.rest.responseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomResponseEntity {
    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean success, String message, Object responseObj) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("isSuccess", success);
            map.put("message", message);
            map.put("data", responseObj);

            return new ResponseEntity<>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess", false);
            map.put("message", e.getMessage());
            map.put("data", null);

            return new ResponseEntity<>(map, status);
        }
    }
}
