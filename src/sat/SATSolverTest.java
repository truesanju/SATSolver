package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import sat.env.*;
import sat.formula.*;
import sat.SATSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();

    public static void main(String[] args) {
        String path =  "C:\\Users\\Charles\\Documents\\GitHub\\SATSolver\\sampleCNF\\s8Sat.cnf";
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
                        clauseStr = currentLine.replace(" 0", "").split("\\s+");
                        for(int j=0;j<clauseStr.length;j++){
                            if(clauseStr[j].charAt(0)=='-'){
                                literalList.add(NegLiteral.make(clauseStr[j]));
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

        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }




	// TODO: have yet to call SATSolver.solve to determine the satisfiability
    
	
    public void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);
/*
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())  
    			|| Bool.TRUE == e.get(b.getVariable())	);
    	
*/    	
    }
    
    
    public void testSATSolver2(){
    	// (~a)
    	Environment e = SATSolver.solve(makeFm(makeCl(na)));
/*
    	assertEquals( Bool.FALSE, e.get(na.getVariable()));
*/    	
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