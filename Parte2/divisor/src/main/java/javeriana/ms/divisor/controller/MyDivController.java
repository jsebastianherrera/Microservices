package javeriana.ms.divisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyDivController {
  @Autowired
  Environment environment;

  @GetMapping("/dividir")
  public String divisor(@RequestParam int a, @RequestParam int b) {
    String port = environment.getProperty("local.server.port");
    int result = a / b;
    String response = "Resultado: " + result + " -> Ms divisor corriendo en el puerto: " + port;
    return response;

  }
}
