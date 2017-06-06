package thiagodnf.jmetal.analysis.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Response {

	public static ResponseEntity success(String message) {
		return new ResponseEntity("{\"type\":\"success\",\"message\":\"" + message + "\"}", HttpStatus.OK);
	}
}
