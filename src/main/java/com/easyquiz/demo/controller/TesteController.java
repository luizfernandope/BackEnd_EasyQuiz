//faça um ola mundo no endpoint /teste
package com.easyquiz.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/teste")
    public String teste() {
        return "Olá, Mundo!";
    }
}