package sat;

import java.util.ArrayList;
import java.util.Iterator;

import immutable.EmptyImList;
import immutable.ImList;
import javafx.geometry.Pos;
import sat.env.Bool;
import sat.env.Environment;
import sat.env.Variable;
import sat.formula.*;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {

    //Instantiate output environment
    public static Environment e = new Environment();
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     *
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        Environment environment = new Environment();
        ImList<Clause> clauseImList = formula.getClauses();
        Environment solution = solve(clauseImList,environment);

        return solution;

    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     *
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {
        if(clauses.size() == 0){
            return env;
        }

        int minClauseSize = 9999;
        Clause minClause = null;

        //Finding smallest clause
        for(Clause i : clauses){
            if(i.size() < minClauseSize){
                minClause = i;
                minClauseSize = i.size();
            }
        }

        //Checking if clause is empty - not solvable
        if(minClauseSize == 0){
            return null;
        } else if(minClauseSize == 1){  // Checking if clause is unit clause
            Literal lit = minClause.chooseLiteral();
            Variable var = lit.getVariable();
            Literal checkLit = PosLiteral.make(var);

            if(checkLit.negates(lit)){    //Checking if literal is negative ~
                Environment newEnv = env.putFalse(var);  //var is ~, so set to false
                ImList<Clause> newClauseList = substitute(clauses,lit);
                return solve(newClauseList,newEnv);
            } else{
                Environment newEnv = env.putTrue(var); //var is pos, so set to true
                ImList<Clause> newClauseList = substitute(clauses,lit);
                return solve(newClauseList,newEnv);
            }
        }

        //For cases where min clause size > 1
        Literal lit = minClause.chooseLiteral();
        Variable var = lit.getVariable();
        Environment newEnv = env.putTrue(var);
        Literal posLiteral = PosLiteral.make(var);
        ImList<Clause> newClauses = substitute(clauses, posLiteral); // clauses after setting literal true
        Environment solution = solve(newClauses, newEnv);

        if (solution == null) {
            newEnv = env.putFalse(var); // change branch, try var false
            Literal negLiteral = NegLiteral.make(var);
            newClauses = substitute(clauses, negLiteral);
            return solve(newClauses, newEnv);
        }
        return solution;

    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     *
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substituted = new EmptyImList<Clause>();

    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {


        //Iterative implementation
        Iterator<Clause> iter = clauses.iterator();
        ImList<Clause> subsClauseList = new EmptyImList<Clause>();
        while(iter.hasNext()){
            Clause subsClause = iter.next();
//            System.out.println("Literal: "+l);
            subsClause = subsClause.reduce(l);
            //IT DIES HERE IF SUBSCLAUSE IS NULL
            //EXCEPTION GETS THROWN AND WHOLE LOGIC IS KICKED TO SETTING 13 TO FALSE
            //the below conditional does not like it when subsclause is null
            if(subsClause != null) {
//                if (subsClause.isEmpty()) {
//                    throw new Exception("Empty clause");
//                }
                subsClauseList = subsClauseList.add(subsClause);
            }

        }
        return subsClauseList;

//        Clause first = clauses.first().reduce(l);
//        ImList<Clause> rest = clauses.rest();
//        System.out.println(rest);
//        if(!first.equals(null)) {
//            substituted = substituted.add(first);
//        }
//
//        if(rest.isEmpty()){
//            return substituted;
//        }
//        return substitute(rest,l) ;
//        }

    }}


