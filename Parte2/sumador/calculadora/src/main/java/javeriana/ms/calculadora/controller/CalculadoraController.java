package javeriana.ms.calculadora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javeriana.ms.calculadora.FileHelper;

@RestController
public class CalculadoraController {

  @Autowired
  @Lazy
  private RestTemplate restTemplate;

  @GetMapping("/calculadora/suma")
  public String calculadoraSuma(@RequestParam int a, @RequestParam int b, @RequestParam String user)
      throws IOException {
    String response = restTemplate.getForObject("http://sumador/suma?a={a}&b={b}", String.class, a, b);
    JSONObject obj = new JSONObject();
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm dd MMM yyyy HH:mm:ss");
    String formattedDateTime = now.format(formatter);
    obj.put("usuario", user);
    obj.put("operacion", "http://sumador/suma?a=" + a + "&b=" + b);
    obj.put("fecha", formattedDateTime);
    FileHelper.writeToFile(obj, "suma.json");
    return response;
  }

  @GetMapping("/calculadora/resta")
  public String calculadoraResta(@RequestParam int a, @RequestParam int b, @RequestParam String user)
      throws IOException {
    String response = restTemplate.getForObject("http://restador/resta?a={a}&b={b}", String.class, a, b);
    JSONObject obj = new JSONObject();
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm dd MMM yyyy HH:mm:ss");
    String formattedDateTime = now.format(formatter);
    obj.put("usuario", user);
    obj.put("operacion", "http://restador/resta?a=" + a + "&b=" + b);
    obj.put("fecha", formattedDateTime);
    FileHelper.writeToFile(obj, "resta.json");
    return response;
  }

  @GetMapping("/calculadora/multiplicar")
  public String calculadoraMultiplicador(@RequestParam int a, @RequestParam int b, @RequestParam String user)
      throws IOException {
    String response = restTemplate.getForObject("http://multiplicador/multiplicar?a={a}&b={b}", String.class, a, b);
    JSONObject obj = new JSONObject();
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm dd MMM yyyy HH:mm:ss");
    String formattedDateTime = now.format(formatter);
    obj.put("usuario", user);
    obj.put("operacion", "http://multiplicador/multiplicar?a=" + a + "&b=" + b);
    obj.put("fecha", formattedDateTime);
    FileHelper.writeToFile(obj, "multiplicar.json");
    return response;
  }

  @GetMapping("/calculadora/dividir")
  public String calculadoraDivisor(@RequestParam int a, @RequestParam int b, @RequestParam String user)
      throws IOException {
    String response = restTemplate.getForObject("http://divisor/dividir?a={a}&b={b}", String.class, a, b);
    JSONObject obj = new JSONObject();
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm dd MMM yyyy HH:mm:ss");
    String formattedDateTime = now.format(formatter);
    obj.put("usuario", user);
    obj.put("operacion", "http://divisor/dividir?a=" + a + "&b=" + b);
    obj.put("fecha", formattedDateTime);
    FileHelper.writeToFile(obj, "dividir.json");
    return response;
  }

  @GetMapping("/calculadora/consulta")
  public JSONArray calculadoraDivisor(@RequestParam String operacion) throws IOException {
    JSONArray rt = new JSONArray();
    if (operacion.equals("suma")) {
      rt = FileHelper.readJsonFile("suma.json");
    } else if (operacion.equals("resta")) {
      rt = FileHelper.readJsonFile("resta.json");
    } else if (operacion.equals("multiplicar")) {
      rt = FileHelper.readJsonFile("multiplicar.json");
    } else if (operacion.equals("dividir")) {
      rt = FileHelper.readJsonFile("dividir.json");
    }
    return rt;
  }

  @LoadBalanced
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
