import java.io.*;

public class Main {

  public static void main(String[] args) {

    try {
      /*
      String reading_path = "datasets\\USvideos.csv";
      String writing_path_dimVideo = "cleanCsv\\dim_video\\dim_video_United_States.csv";
      String writing_path_fait = "cleanCsv\\fait\\fait_United_States.csv";
      String countryName = "United States";*/

      /*
      String reading_path = "datasets\\CAvideos.csv";
      String writing_path_dimVideo = "cleanCsv\\dim_video\\dim_video_Canada.csv";
      String writing_path_fait = "cleanCsv\\fait\\fait_Canada.csv";
      String countryName = "Canada";*/

      /*
      String reading_path = "datasets\\DEvideos.csv";
      String writing_path_dimVideo = "cleanCsv\\dim_video\\dim_video_Germany.csv";
      String writing_path_fait = "cleanCsv\\fait\\fait_Germany.csv";
      String countryName = "Germany";*/

      /*
      String reading_path = "datasets\\FRvideos.csv";
      String writing_path_dimVideo = "cleanCsv\\dim_video\\dim_video_France.csv";
      String writing_path_fait = "cleanCsv\\fait\\fait_France.csv";
      String countryName = "France";*/


      String reading_path = "datasets\\GBvideos.csv";
      String writing_path_dimVideo = "cleanCsv\\dim_video\\dim_video_United_Kingdom.csv";
      String writing_path_fait = "cleanCsv\\fait\\fait_United Kingdom.csv";
      String countryName = "United Kingdom";


      BufferedReader source_file = new BufferedReader(new FileReader(reading_path));
      final File target_file_dimVideo = new File(writing_path_dimVideo);
      final File target_file_fait = new File(writing_path_fait);

      target_file_dimVideo.createNewFile();
      target_file_fait.createNewFile();

      final FileWriter writerDimVideo = new FileWriter(target_file_dimVideo);
      final FileWriter writerFait = new FileWriter(target_file_fait);

      try {
        String chaine = source_file.readLine();
        String[] tabChaine = splitCsvLineOnCommas(chaine);

        writerDimVideo.write(tabChaine[0] + ", "+ tabChaine[1] + ", "+tabChaine[2] + ", "+tabChaine[3] + ", "+tabChaine[5] + ", "+tabChaine[12] + ", " + tabChaine[13] + ", " + tabChaine[14] +"\n");
        writerFait.write(tabChaine[0] + ", cle_pays, "+tabChaine[4] + ", "+tabChaine[7] + ", "+tabChaine[8]+ ", "+tabChaine[9]+ ", "+tabChaine[10] +"\n" );


        while ((chaine = source_file.readLine()) != null) {

          //only remains true if the true has an adequate format
          boolean goodRow = true;

          tabChaine = splitCsvLineOnCommas(chaine);

          //check if the row has an adequate number of cells
          if(tabChaine.length == 16){

            //check if the trending_date has an adequate format and transforms it to fit the databases format
            if (tabChaine[1].length() == 8){
              String badformat = tabChaine[1];

              String[] badformatSplit = tabChaine[1].split("\\.");

              String goodformat = "20"+badformatSplit[0]+"-"+badformatSplit[2]+"-"+badformatSplit[1];

              tabChaine[1]= goodformat;
            } else {
              goodRow = false;
            }

            //check if the publish_time has an adequate format and transforms it to fit the databases format
            if (tabChaine[5].length() == 24) {
              String badformat = tabChaine[4];

              String[] badformatSplit = tabChaine[1].split("T");

              tabChaine[5] = badformatSplit[0];

            } else {
              goodRow = false;
            }

            if(!goodRow || !isTrueFalse(tabChaine[14]) || !isTrueFalse(tabChaine[13]) || !isTrueFalse(tabChaine[12]) || !isNumeric(tabChaine[4]) || !isNumeric(tabChaine[7]) || !isNumeric(tabChaine[8]) || !isNumeric(tabChaine[9]) || !isNumeric(tabChaine[10])){
              goodRow = false;
            }

            if (goodRow){
              writerDimVideo.write(tabChaine[0] + ", "+ tabChaine[1] + ", "+tabChaine[2] + ", "+tabChaine[3] + ", "+tabChaine[5] + ", "+tabChaine[12] + ", " + tabChaine[13] + ", " + tabChaine[14] +"\n");
              writerFait.write(tabChaine[0] + ", " + countryName + ", " + tabChaine[4] + ", "+tabChaine[7] + ", "+tabChaine[8]+ ", "+tabChaine[9]+ ", "+tabChaine[10] +"\n" );
            }

          }
        }

      }  finally { //pas oublier de close
        writerDimVideo.close();
        writerFait.close();
        source_file.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  /**
   * Split a string using commas, only if this comma is not surrounded by quotes
   * This is needed to take into account commas that could be in a string
   * @param stringToManage
   * @return
   */
  private static String[] splitCsvLineOnCommas(String stringToManage){

    String otherThanQuote = " [^\"] ";
    String quotedString = String.format(" \" %s* \" ", otherThanQuote);
    String regex = String.format("(?x) "+ // enable comments, ignore white spaces
                    ",                         "+ // match a comma
                    "(?=                       "+ // start positive look ahead
                    "  (?:                     "+ //   start non-capturing group 1
                    "    %s*                   "+ //     match 'otherThanQuote' zero or more times
                    "    %s                    "+ //     match 'quotedString'
                    "  )*                      "+ //   end group 1 and repeat it zero or more times
                    "  %s*                     "+ //   match 'otherThanQuote'
                    "  $                       "+ // match the end of the string
                    ")                         ", // stop positive look ahead
            otherThanQuote, quotedString, otherThanQuote);

    return stringToManage.split(regex, -1);
  }

  /**
   * Check if a string is a number
   * @param stringToCheck
   * @return
   */
  private static boolean isNumeric(String stringToCheck) {

    boolean numeric = true;
    try {
      Double num = Double.parseDouble(stringToCheck);
    } catch (NumberFormatException e) {
      numeric = false;
    }
    return numeric;
  }

  /**
   * Check if a string contains the words "True" or "False"
   * @param stringToCheck
   * @return
   */
  private static boolean isTrueFalse(String stringToCheck) {
    return (stringToCheck.equals("True") || stringToCheck.equals("False"));
  }
}
