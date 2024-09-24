package com.boot2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class BootSampleController {

    @RequestMapping(value = "/hello")
    public String boot1Request() {
        return "Hello World";
    }

}
