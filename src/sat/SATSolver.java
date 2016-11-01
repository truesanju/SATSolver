package sat;

import java.util.Iterator;

import immutable.ImList;
import sat.env.Bool;
import sat.env.Environment;
import sat.env.Variable;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {




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
        // TODO: implement this.
        Environment e = new Environment();

        //Check for no clauses
        if(formula.getSize()==0){
            Variable v = new Variable(formula.toString());
            e.putTrue(v);
            return e = null;
        }

        //Check for empty clause
        ImList<Clause> clauseList = formula.getClauses();
        Iterator<Clause> iter = formula.iterator();
        while (iter.hasNext()){
            Clause currentClause = iter.next();
            if (currentClause.isEmpty()){
                Variable v = new Variable(currentClause.toString());
                e.putFalse(v);
                return e=null;
            }
        }










        //Backtracking

        //Choose a literal
        //Assign truth value to literal
        //Simplify formula
        //Check satisfiable
        //



        throw new RuntimeException("not yet implemented.");
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
        // TODO: implement this.
        throw new RuntimeException("not yet implemented.");
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
    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {
        // TODO: implement this.
        throw new RuntimeException("not yet implemented.");
    }

}
