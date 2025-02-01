package com.example.booking_ms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class vacation {
    @GetMapping("/vacation")
    public String getData() {return  "Please book your vacation in New Delhi 10% discount" ; }
}