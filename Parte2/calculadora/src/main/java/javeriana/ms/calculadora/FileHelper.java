package javeriana.ms.calculadora;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileHelper {

  public static void writeToFile(JSONObject jsonObject, String fileName) throws IOException {
    try {
      File file = new File(fileName);
      if (!file.exists()) {
        file.createNewFile();
      }
      JSONArray jsonArray = readJsonFile(fileName);
      if (jsonArray == null) {
        jsonArray = new JSONArray();
      }
      jsonArray.add(jsonObject);
      try (FileWriter fileWriter = new FileWriter(fileName)) {
        fileWriter.write(jsonArray.toJSONString());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static JSONArray readJsonFile(String fileName) throws IOException {
    JSONParser parser = new JSONParser();
    try (FileReader fileReader = new FileReader(fileName)) {
      Object obj = parser.parse(fileReader);
      JSONArray jsonArray = (JSONArray) obj;
      return jsonArray;
    } catch (IOException | ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}
