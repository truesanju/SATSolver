package sat.cnfReader;

import immutable.ImList;
import javafx.geometry.Pos;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.NegLiteral;
import sat.formula.PosLiteral;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by Charles on 1/11/2016.
 */
public class ReadFile {

    private String path;

    public ReadFile(String file_path) {
        path = file_path;
    }

    int readLines() throws IOException {
        FileReader file_to_read = new FileReader(path);
        BufferedReader bf = new BufferedReader(file_to_read);
        String aLine;
        int numberOfLines = 0;
        while ((aLine = bf.readLine()) != null) {
            numberOfLines++;
        }
        bf.close();
        return numberOfLines;
    }

    public Formula OpenFile() throws IOException {

        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);
        int numberOfLines = readLines();
        String[] clauseStr;
        Formula formula = new Formula();


        for (int i = 0; i < numberOfLines; i++) {
            String currentLine = textReader.readLine();

            if (!currentLine.equals("")) {
                if (!(currentLine.charAt(0) == ('c')) && !(currentLine.charAt(0) == ('p'))) {
                    clauseStr = currentLine.replace(" 0", "").split("\\s+");
                    Clause clause = new Clause();
                    for(int j=0;j<clauseStr.length;j++){
                        if(clauseStr[j].charAt(0)=='-'){
                            System.out.println("Negative");
                            clause.add(NegLiteral.make(clauseStr[j]));
                        } else{
                            System.out.println("Positive");
                            clause.add(PosLiteral.make(clauseStr[j]));
                            System.out.println(PosLiteral.make(clauseStr[j]));
                            System.out.println(clause.size());
                        }
                        //System.out.println(clause.toString());
                    }
                    formula.addClause(clause);


//                    List<String> stringList = new ArrayList<>(Arrays.asList(clause));
//                    textData.add(stringList);
                }
            }
        }
        textReader.close();
       return formula;

    }
}

//    //parses string array to int array
//    public static int[] parseIntArray(String[] arr) {
//        return Stream.of(arr).mapToInt(Integer::parseInt).toArray();
//    }


