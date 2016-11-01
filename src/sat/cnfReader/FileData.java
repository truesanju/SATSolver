package sat.cnfReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Charles on 1/11/2016.
 */
public class FileData {
    public static void main(String[] args) throws IOException{

        String file_name =  "C:/Users/Charles/Desktop/largeSat.CNF";

        try{
            ReadFile file = new ReadFile(file_name);
            System.out.println(file.OpenFile());

        } catch(IOException e){
            System.out.println(e.getMessage());

        }

    }
}
