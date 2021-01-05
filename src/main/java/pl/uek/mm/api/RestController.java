package pl.uek.mm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.uek.mm.model.ApiError;
import pl.uek.mm.model.RequestData;
import pl.uek.mm.model.ResponseModel;
import pl.uek.mm.service.AlgorithmService;

@Controller
@RequestMapping
public class RestController {

    @Autowired
    private AlgorithmService algoService;

    @PostMapping(path = "/data")
    @CrossOrigin
    @ResponseBody
    public ResponseModel calculate(@RequestBody RequestData request) throws Exception {
        return algoService.doCalculations(request);
    }

	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleException(Exception e) {
		ApiError error = new ApiError(
				HttpStatus.INTERNAL_SERVER_ERROR,
				e.getMessage());
		return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
