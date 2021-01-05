package pl.uek.mm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.uek.mm.model.RequestData;
import pl.uek.mm.model.ResponseModel;
import pl.uek.mm.service.AlgorithmService;

@Controller
@RequestMapping
public class RestController {

    @Autowired
    private AlgorithmService algoService;

    @PostMapping(path = "/data")
    @ResponseBody
    public ResponseModel calculate(@RequestBody RequestData request) {
        return algoService.doCalculations(request);
    }

}
