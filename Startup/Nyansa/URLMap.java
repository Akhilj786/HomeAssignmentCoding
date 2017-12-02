package Interview;
/**
 * Java 1.8
 * Created by AkhilJain on 11/24/17.
 * <p>
 * Time Complexity = O(n)
 * where n = number of input lines in the input file
 * <p>
 * <p>
 * Logic:
 * 1) Iterate over each line and store it in an String array.
 * 2) Apply a split on '|' and convert to string to epoch.
 * 3) Store epoc,url and count and keep updating as we go along.
 * 4) Do like a groupby on epoch(converted to GMT date format) and sorted by count(desc).
 * <p>
 * Future Consideration:
 * 1) Convert it like a streaming service,like given a new line we can spit out the o/p
 *      Logic: Step 1(of the above process) could be broken down by taking only new line and persisted in some db/no-sql.
 * 2) Groupby other format i.e Datetime
 *      Logic: Step2 could be modified for it.
 * 3) Group by url and by date(given a url spit out its counts).
 *      Logic: Step 3,4 could be modified for it.
 */

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class URLMap {
    
    private String convertEpochToDate(long epoch) {
        Date dt = new Date(epoch);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String dtStr = df.format(dt);
        return dtStr;
    }
    
    private long convertEpoch(String epocTime) {
        long epoch = Long.parseLong(epocTime) * 1000;
        epoch = (long) Math.floor(epoch / (24 * 60 * 60 * 1000));
        epoch = epoch * (24 * 60 * 60 * 1000);
        return epoch;
    }
    
    private void printResult(Map<Long, Map<String, Integer>> dateToURLMap) {
        for (Entry<Long, Map<String, Integer>> e : dateToURLMap.entrySet()) {
            System.out.println(convertEpochToDate(e.getKey()) + " GMT");
            Map<String, Integer> urlToCntMap = e.getValue();
            @SuppressWarnings("unchecked")
            Entry<String, Integer>[] countEntries = urlToCntMap.entrySet().toArray(new Entry[urlToCntMap.size()]);
            Arrays.sort(countEntries, new Comparator<Entry<String, Integer>>() {
                @Override
                public int compare(Entry<String, Integer> o1,
                                   Entry<String, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            for (Entry<String, Integer> en : countEntries) {
                System.out.println(en.getKey() + " " + en.getValue());
            }
        }
        
    }
    
    public void createMap(String[] inputLines) {
        Map<Long, Map<String, Integer>> dateToURLMap = new TreeMap<>();
        
        for (String line : inputLines) {
            
            String[] tokens = line.split("\\|");
            long epoch = convertEpoch(tokens[0]);
            Map<String, Integer> urlToCntMap = dateToURLMap.get(epoch);
            if (urlToCntMap == null) {
                urlToCntMap = new HashMap<>();
            }
            Integer cnt = urlToCntMap.get(tokens[1]);
            if (cnt == null) {
                cnt = 0;
            }
            cnt++;
            urlToCntMap.put(tokens[1], cnt);
            dateToURLMap.put(epoch, urlToCntMap);
        }
        
        printResult(dateToURLMap);
        
    }
    
    
    public static void main(String[] args) {
        // Enter the full path of the file
        Scanner in = new Scanner(System.in);
        URLMap obj = null;
        try {
            System.out.println("Please Input Absolute path for file:");
            String filename = in.nextLine();
            File f = new File(filename);
            if (f.exists()) {
                String[] inputLines = null;
                List<String> strList = new ArrayList<>();
                try {
                    FileInputStream istream = new FileInputStream(filename);
                    DataInputStream data_input = new DataInputStream(istream);
                    @SuppressWarnings("resource")
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
                    String str_line;
                    while ((str_line = buffer.readLine()) != null) {
                        str_line = str_line.trim(); // Remove spacing
                        if ((str_line.length() != 0)) {
                            strList.add(str_line); //Empty Line
                        }
                    }
                    inputLines = strList.toArray(new String[strList.size()]);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
                obj = new URLMap();
                obj.createMap(inputLines);
            } else {
                System.out.println("Input file not present");
            }
        } finally {
            in.close();
        }
    }
}
