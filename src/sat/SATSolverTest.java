package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import sat.env.*;
import sat.formula.*;
import sat.SATSolver;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class SATSolverTest {


    public static void main(String[] args) {

        String path =  "C:\\Users\\Charles\\Documents\\GitHub\\SATSolver\\sampleCNF\\largesat.cnf";
        Formula formula;

        try{
            //checks how many lines the file is
            FileReader file_to_read = new FileReader(path);
            BufferedReader bf = new BufferedReader(file_to_read);
            String aLine;
            int numberOfLines = 0;
            while ((aLine = bf.readLine()) != null) {
                numberOfLines++;
            } bf.close();

            FileReader newRead = new FileReader(path);
            BufferedReader textReader = new BufferedReader(newRead);  // start stream for parsing cnf
            String[] clauseStr;
            Clause clause;
            ArrayList<Clause> clauseList = new ArrayList<>();

            for (int i = 0; i < numberOfLines; i++) {
                String currentLine = textReader.readLine();
                ArrayList<Literal> literalList = new ArrayList<>();

                if (!currentLine.equals("")) {
                    if (!(currentLine.charAt(0) == ('c')) && !(currentLine.charAt(0) == ('p'))) {
                        if(currentLine.charAt(0) == ' '){
                            currentLine = currentLine.substring(1);
                        }
                        clauseStr = currentLine.replace(" 0", "").split("\\s+");
                        for(int j=0;j<clauseStr.length;j++){
                            if(clauseStr[j].charAt(0)=='-'){
                                literalList.add(NegLiteral.make(clauseStr[j].substring(1)));
                            } else{
                                literalList.add(PosLiteral.make(clauseStr[j]));
                            }
                        }
                        Literal[] literalArray = new Literal[literalList.size()];
                        literalList.toArray(literalArray);
                        clause = makeCl(literalArray);
                        clauseList.add(clause);
                    }
                }
            }
            Clause[] clauseArray = new Clause[clauseList.size()];
            clauseList.toArray(clauseArray);
            formula = makeFm(clauseArray);
            textReader.close();

            System.out.println("SAT solver starts!!!");
            long started = System.nanoTime();
            Environment e = SATSolver.solve(formula);
            long time = System.nanoTime();
            long timeTaken= time -started;
            System.out.println("Time:" + timeTaken/1000000.0 + "ms");

            if(e==null){
                System.out.println("unsatisfiable");
            } else{
                System.out.println("satisfiable");
            }

            String eString = e.toString();
            eString = eString.replaceAll("->",":");
            eString = eString.replaceAll("\\w*:\\[","");
            eString = eString.replaceAll("\\]","");
            eString = eString.replaceAll(" ","");

            String[] lines = eString.split(",");

//            Path file = new File("C:\\Users\\Charles\\Documents\\GitHub\\SATSolver\\BoolAssignment.txt");
//            Files.write(file, lines, Charset.forName("UTF-8"));

            File fout = new File("BoolAssignment.txt");

            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (int i = 0; i < lines.length; i++) {
                bw.write(lines[i]);
                bw.newLine();
            }

            bw.close();

        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

    
    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }
    
    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }
    
    
    
}