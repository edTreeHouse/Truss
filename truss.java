/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truss;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author edwardclark
 */
public class truss {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        StringBuilder outgoingCSV = new StringBuilder();

        try {
            int kntr = 0, fieldCounter = 0;
            long fooDuration = 0, barDuration = 0;
            String line = "", field = "";
            
            // retrieve csv from system in
            System.out.print("Enter csv and press Enter:");
            
            // read system.in
            Scanner input = new Scanner(new InputStreamReader(System.in, "UTF-8"));
            
            // skip first line
            input.nextLine();
            
            // walk each line
            while (input.hasNextLine()) {
                kntr++;
                
                line = input.nextLine();
                
                // check for last line
                if (line.length() < 1) {
                    System.out.println(outgoingCSV.toString());
                    System.exit(0);
                }
                
                // split tokens at commas
                StringTokenizer s = new StringTokenizer(line,",");   
                
                //System.out.println("line "+kntr+": "+line);        

                fieldCounter=0;
                            
                while (s.hasMoreTokens()) {

                    fieldCounter++;
                    field = s.nextToken();
                    
                    if (fieldCounter == 1) {  // parse timestamp field
                        field.replace(",", "");
                        
                        //System.out.println("Parsing Timestamp: "+field);
                                                
                        try {
                            String dateFormat = "L/d/yy h:mm:ss a"; 
                            String dateInString = field;

                            ZoneId newYorkTimeZone = ZoneId.of("America/New_York");
                            ZoneId pacificTimeZone = ZoneId.of("America/Los_Angeles");

                            LocalDateTime ldt = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern(dateFormat));

                            ZonedDateTime pacificZonedDateTime = ldt.atZone(pacificTimeZone);
                            ZonedDateTime nyDateTime = pacificZonedDateTime.withZoneSameInstant(newYorkTimeZone);

                            LocalDateTime est = nyDateTime.toLocalDateTime();
                            DateTimeFormatter estFormatter = DateTimeFormatter.ofPattern(dateFormat);
                            String text = est.format(estFormatter);
                            //System.out.println("Time Zone converted DateTime: "+text);
                            
                            outgoingCSV.append(text+",");
                                                    
                        } catch (Exception etn) {
                            etn.printStackTrace();
                        }
                    } else if (fieldCounter == 2) { // parse address line 1 
                        //System.out.println("Parsing Address line 1: "+field);
                        outgoingCSV.append(field+",");
                    } else if (fieldCounter == 3) { // parse address line 2 
                        //System.out.println("Parsing Address line 2: "+field);
                        outgoingCSV.append(field+",");
                    } else if (fieldCounter == 4) { // parse address line 3  
                        //System.out.println("Parsing Address line 3: "+field);
                        outgoingCSV.append(field+",");
                    } else if (fieldCounter == 5) { // parse zip 
                        //System.out.println("Parsing zip: "+field);
                        StringBuffer padding = new StringBuffer();
                        if (field.length() < 5) {
                            // pad    
                            int padLength = 5 - field.length();
                            while (padding.length() < padLength) {
                                padding.append("0");                                
                            }  
                            field = padding + field;
                            //System.out.println("Padded Zip: "+field);
                        }
                        outgoingCSV.append(field+",");
                    } else if (fieldCounter == 6) { // parse name
                        //System.out.println("Parsing name: "+field);
                        field = field.toUpperCase();                       
                    }               
                }
            }
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
    } 
}
