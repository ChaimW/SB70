import java.util.*;
import java.io.*;

public class SB70 {
   public static void main(String[] args) {
      try {
         File source = new File("source.csv");
         Scanner fileReader = new Scanner(source);
         FileWriter output = new FileWriter("unitary_file.txt");
         FileWriter log = new FileWriter("SB70.log");
         FileWriter logB = new FileWriter("SB70_anon.log");
         
         int lineCount = 0;
         while(fileReader.hasNextLine()){
            lineCount++;
            fileReader.nextLine();
         }
         
         fileReader = new Scanner(source);
         String[][] data = new String[lineCount][25];
         String datum;
         for(int i=0; i<lineCount; i++){
            datum = fileReader.nextLine();
            data[i] = datum.split(",", 25 );
         }
         for(int i=1; i<lineCount; i++){
            if(data[i][1].isBlank()){
               break;
            }
            
            while(data[i][0].length() < 8){
               data[i][0] = "0" + data[i][0];
            }
            
            data[i][2] = data[i][2].replace("-","");  //ssn
            while(data[i][2].length() < 9){
               data[i][2] = "0" + data[i][2];
            }
            
            while(data[i][3].length() < 25){
               data[i][3] += " ";
            }
            while(data[i][4].length() < 25){
               data[i][4] += " ";
            }
            
            data[i][5] = dateBuffer(data[i][5]);
            data[i][6] = dateBuffer(data[i][6]);
            data[i][7] = dateBuffer(data[i][7]);
            
            switch(data[i][8].toLowerCase()){
               case "0":
                  break;
               case "ba":
               case "bs":
               case "bachelor":
               case "bachelors":
                  data[i][8] = "5";
                  break;
               case "aa":
               case "as":
               case "associates":
                  data[i][8] = "3";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][11] + " is not a matching degree type, 0 used.\n");
                  logB.write("Degree mismatch: " + data[i][11] + "\n");
                  data[i][8] = "0";
            }
            
            switch(data[i][9].toLowerCase()){
               case "1":
               case "a":
               case "cal a":
                  data[i][9] = "1";
                  break;
               case "2":
               case "b":
               case "cal b":
                  data[i][9] = "2";
                  break;
               case "3":
               case "c":
               case "cal c":
                  data[i][9] = "3";
                  break;
               case "0":
               case "none":
               case "n/a":
               case "na":
                  data[i][9] = "0";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][9] + " is not a matching Cal Grant type, 0 used.\n");
                  logB.write("cal grant mismatch: " + data[i][9] + "\n");
                  data[i][9] = "0";
            }
            
            switch(data[i][17].toLowerCase()){
               case "1":
               case "y":
                  data[i][17] = "1";
                  break;
               case "2":
               case "n":
                  data[i][17] = "2";
                  break;
               case "0":
               case "none":
               case "n/a":
               case "na":
                  data[i][17] = "0";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][17] + " is not a matching pell type, 0 used.\n");
                  logB.write("pell mismatch: " + data[i][17] + "\n");
                  data[i][17] = "0";
            }
            
            switch(data[i][15].toLowerCase()){
               case "m":
               case "male":
                  data[i][15] = "1";
                  break;
               case "f":
               case "female":
                  data[i][15] = "2";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][15] + " is not a matching gender, 4 used.\n");
                  logB.write("Gender mismatch: " + data[i][15] + "\n");
                  data[i][15] = "4";
            }
            
            switch(data[i][16].toLowerCase()){
               case "american indian":
               case "american indian/native alaskan":
                  data[i][16] = "1";
                  break;
               case "asian":
                  data[i][16] = "2";
                  break;
               case "black":
               case "black or african american":
               case "african am./black/non-hispanic":
                  data[i][16] = "3";
                  break;
               case "hispanic":
               case "hispanic of any race":
                  data[i][16] = "4";
                  break;
               case "native hawaiian":
               case "pacific islander":
               case "native hawaiian/pacific islander":
               case "native hawaiian or other pacific islander":
                  data[i][16] = "5";
                  break;
               case "white":
               case "white/caucasian/non-hispanic":
                  data[i][16] = "6";
                  break;
               case "two or more":
               case "two or more races":
                  data[i][16] = "7";
                  break;
               case "unknown":
               case "race and ethnicity unknown":
               case "other":
               case "nonresident alien":
               case "non-resident alien":
                  data[i][16] = "8";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][16] + " is not a matching race, 8 used.\n");
                  logB.write("Race mismatch: " + data[i][16] + "\n");
                  data[i][16] = "8";
            }
            
            switch(data[i][20].toLowerCase()){
               case "transfer in":
               case "tr":
                  data[i][20] = "1";
                  break;
               case "first time":
               case "ft":
                  data[i][20] = "2";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][20] + " is not a matching enrollment type, 0 used.\n");
                  logB.write("Enrollment mismatch: " + data[i][20] + "\n");
                  data[i][20] = "0";
            }
            
            switch(data[i][22].toLowerCase()){
               case "1":
               case "y":
                  data[i][22] = "1";
                  break;
               case "2":
               case "n":
                  data[i][22] = "2";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][22] + " is not a matching persitence, 2  used.\n");
                  logB.write("persist mismatch: " + data[i][22] + "\n");
                  data[i][22] = "2";
            }
            
            switch(data[i][23].toLowerCase()){
               case "1":
               case "y":
                  data[i][23] = "1";
                  break;
               case "2":
               case "n":
                  data[i][23] = "2";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][23] + " is not a matching persitence, 2  used.\n");
                  logB.write("persist mismatch: " + data[i][23] + "\n");
                  data[i][23] = "2";
            }
            
            switch(data[i][24].toLowerCase()){
               case "1":
               case "y":
                  data[i][24] = "1";
                  break;
               case "2":
               case "n":
                  data[i][24] = "2";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][24] + " is not a matching persitence, 2  used.\n");
                  logB.write("persist mismatch: " + data[i][24] + "\n");
                  data[i][24] = "2";
            }

                      
            output.write(data[i][0] + " " + data[i][1] + " " + data[i][2] + " " + data[i][3] + " " + data[i][4] + " " + data[i][5] + " " + data[i][6] + " " + data[i][7] + " " + data[i][8] + " " + data[i][9] + " " + data[i][10] + " " + data[i][11] + " " + data[i][12] + " " + data[i][13] + " " + data[i][14] + " " + data[i][15] + " " + data[i][16] + " " + data[i][17] + " " + data[i][18] + " " + data[i][19] + " " + data[i][20] + " " + data[i][21] + " " + data[i][22] + " " + data[i][23] + " " + data[i][24] + String.format(" %07d\n", i));
         }
         
         output.close();
         log.close();
         logB.close();
      } // end try block
      
      catch (FileNotFoundException e){
         System.out.println("Source file not found");
      }
      
      catch (IOException e){
         System.out.println("Output error");
      }
      
   }   // end main method
   
   private static String dateBuffer(String date){
      String[] parts = date.split("/");
      if(parts[0].length() < 2){
         parts[0] = "0" + parts[0];
      }
      if(parts[1].length() < 2){
         parts[1] = "0" + parts[1];
      }
      if(parts[2].length() < 4){
         if (parts[0] == "00"){
            parts[2] = "00" + parts[2];
         }
         else if(Integer.parseInt(parts[2])>50){
            parts[2] = "19" + parts[2];
         }
         else{
            parts[2] = "20" + parts[2];
         }
      }
      date = parts[0] + "/" + parts[1] + "/" + parts[2];
      return date;
   }
   
}   
