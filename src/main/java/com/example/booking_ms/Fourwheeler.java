package com.example.booking_ms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Fourwheeler {
    @GetMapping("/Fourwheeler")
    public String getData() {return  "Please book your For Wheeler for New Delhi at 35% discount" ; }
}