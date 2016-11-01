package sat.cnfReader;

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

    public ArrayList OpenFile() throws IOException {

        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);
        int numberOfLines = readLines();
        ArrayList textData = new ArrayList();
        String[] clause;
        ArrayList intData = new ArrayList<Integer>();

        for (int i = 0; i < numberOfLines; i++) {
            String currentLine = textReader.readLine();

            if (currentLine != null) {

                if (!(currentLine.charAt(0) == ('c')) && !(currentLine.charAt(0) == ('p'))) {
                    clause = currentLine.replace(" 0", "").split("\\s+");
                    List<String> stringList = new ArrayList<>(Arrays.asList(clause));
                    for (String s : stringList) intData.add(Integer.valueOf(s));
                    textData.add(stringList);
                }
            }
        }
        textReader.close();
       return textData;

    }
}

//    //parses string array to int array
//    public static int[] parseIntArray(String[] arr) {
//        return Stream.of(arr).mapToInt(Integer::parseInt).toArray();
//    }


