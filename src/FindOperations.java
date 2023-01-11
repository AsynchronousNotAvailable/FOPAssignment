import java.io.*;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class FindOperations {

        public static void main(String[] args) {

            try {

                // Read file
                BufferedReader fileIn = new BufferedReader(new FileReader("./src/extracted_log.txt"));
                PrintWriter fileOut = new PrintWriter(new FileWriter("./src/job_type.txt"));

                // Variable declarations
                LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
                String line;

                // Read file
                while ((line = fileIn.readLine()) != null) {
                    String keyword = line.split(" ")[1];
                    if (map.containsKey(keyword)) { map.put(keyword, map.get(keyword) + 1); }
                    else { map.put(keyword, 0); }
                }

                // Sort keywords by descending and write to file
                map = sortDescending(map);
                map.forEach((keyword, count) -> fileOut.printf("%s: %d\n", keyword, count));

                // Close file
                fileIn.close();
                fileOut.close();
            }
            catch (FileNotFoundException e) {System.out.println("File Not Found!");}
            catch (IOException e) { System.out.printf("IO Exception: %s\n", e); }
        }

        public static LinkedHashMap<String, Integer> sortDescending(LinkedHashMap<String, Integer> dict) {

            List<Map.Entry<String, Integer>> list = new LinkedList<>(dict.entrySet());
            Collections.sort(list, (map1, map2) -> map2.getValue().compareTo(map1.getValue()));

            LinkedHashMap<String, Integer> newDict = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> map: list) { newDict.put(map.getKey(), map.getValue()); }

            return newDict;
        }
    }

