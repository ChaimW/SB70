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
         String[][] data = new String[lineCount][17];
         String datum;
         for(int i=0; i<lineCount; i++){
            datum = fileReader.nextLine();
            data[i] = datum.split(",", 17);
         }
         for(int i=1; i<lineCount; i++){
            if(data[i][1].isBlank()){
               break;
            }
            data[i][2] = data[i][2].replace(".","");
            while(data[i][4].length() < 9){
               data[i][4] = "0" + data[i][4];
            }
            while(data[i][5].length() < 25){
               data[i][5] += " ";
            }
            while(data[i][6].length() < 25){
               data[i][6] += " ";
            }
            data[i][7] = dateBuffer(data[i][7]);
            data[i][8] = dateBuffer(data[i][8]);
            data[i][9] = dateBuffer(data[i][9]);
            while(data[i][10].length() < 2){
               data[i][10] = "0" + data[i][10];
            }
            switch(data[i][11].toLowerCase()){
               case "0":
                  break;
               case "bachelor":
               case "bachelors":
                  data[i][11] = "5";
                  break;
               case "associates":
                  data[i][11] = "3";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][11] + " is not a matching degree type, 0 used.\n");
                  logB.write("Degree mismatch: " + data[i][11] + "\n");
                  data[i][11] = "0";
            }
            
            switch(data[i][12].toLowerCase()){
               case "transfer in":
                  data[i][12] = "2";
                  break;
               case "first time":
                  data[i][12] = "1";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][12] + " is not a matching enrollment type, 3 used.\n");
                  logB.write("Enrollment mismatch: " + data[i][12] + "\n");
                  data[i][12] = "3";
            }
            
            switch(data[i][13].toLowerCase()){
               case "m":
               case "male":
                  data[i][13] = "1";
                  break;
               case "f":
               case "female":
                  data[i][13] = "2";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][13] + " is not a matching gender, 3 used.\n");
                  logB.write("Gender mismatch: " + data[i][13] + "\n");
                  data[i][13] = "3";
            }
            
            switch(data[i][14].toLowerCase()){
               case "hispanic":
                  data[i][14] = "01";
                  break;
               case "american indian":
                  data[i][14] = "02";
                  break;
               case "asian":
                  data[i][14] = "03";
                  break;
               case "black":
                  data[i][14] = "04";
                  break;
               case "native hawaiian":
                  data[i][14] = "05";
                  break;
               case "pacific islander":
                  data[i][14] = "05";
                  break;
               case "white":
                  data[i][14] = "06";
                  break;
               case "unknown":
                  data[i][14] = "07";
                  break;
               case "nonresident alien":
                  data[i][14] = "08";
                  break;
               case "two or more":
               case "two or more races":
                  data[i][14] = "09";
                  break;
               default:
                  log.write("[WARNING] Record " + i + ": " + data[i][14] + " is not a matching race, 7 used.\n");
                  logB.write("Race mismatch: " + data[i][14] + "\n");
                  data[i][14] = "7";
            }

            data[i][16] = data[i][16].replace("X","0");
            data[i][16] = data[i][16].replace("x","0");
            while(data[i][16].length() < 6){
               data[i][16] = "0" + data[i][16];
            }
                      
            data[i][15] = dateBuffer(data[i][15]);
            output.write(data[i][0] + " " + data[i][1] + " " + data[i][2] + " " + data[i][3] + " " + data[i][4] + " " + data[i][5] + " " + data[i][6] + " " + data[i][7] + " " + data[i][8] + " " + data[i][9] + " " + data[i][10] + " " + data[i][11] + " " + data[i][12] + " " + data[i][13] + " " + data[i][14] + " " + data[i][15] + " " + data[i][16] + String.format(" %07d\n", i));
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
      date = parts[0] + "/" + parts[1] + "/" + parts[2];
      return date;
   }
   
}   
