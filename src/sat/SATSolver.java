package sat;

import java.util.Iterator;

import immutable.EmptyImList;
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

        Environment e = new Environment();

        //Check for no clauses
        if(formula.getSize()==0){
            Variable v = new Variable(formula.toString());
            e.putTrue(v);
            return e;
        }

        //Check for empty clause
        ImList<Clause> clauseList = formula.getClauses(); //returns ImList<Clause>
        Iterator<Clause> iter = formula.iterator();
        Clause smallestClause = new Clause();
        while (iter.hasNext()) {
            Clause currentClause = iter.next();
            if (currentClause.size() == 1) {
                currentClause = smallestClause;
            }
            if (currentClause.isEmpty()) {
                return e = null;
            }}

            if (smallestClause.isUnit()){
                    Variable v = new Variable(smallestClause.toString());
                    e.putTrue(v);
                    clauseList.remove(smallestClause);
                    Formula f = new Formula(substitute(clauseList,smallestClause.chooseLiteral()));
                    return solve(f);
                } else {
                    Literal lit = smallestClause.chooseLiteral();
                    Variable v = new Variable(lit.toString());
                    try {
                        e.putTrue(v);
                        Formula f = new Formula(substitute(clauseList, lit));
                        solve(f);
                        return e;
                    } catch (Exception ex)
                    {
                        try {
                            e.putFalse(v);
                            Literal nlit = lit.getNegation();
                            Formula f = new Formula(substitute(clauseList, nlit));
                            solve(f);
                            return e;
                        } catch (Exception x){
                            return e=null;


                }}

            }


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
        //Iterative implementation
//        Iterator<Clause> iter = clauses.iterator();
//        ImList<Clause> subsClauseList = new EmptyImList<Clause>();
//        while(iter.hasNext()){
//            Clause subsClause = iter.next();
//            subsClause.reduce(l);
//            subsClauseList.add(subsClause);
//        }
//        return subsClauseList;
        Clause first = clauses.first().reduce(l);
        ImList<Clause> rest = clauses.rest();
        ImList<Clause> substituted = new EmptyImList<Clause>();
        substituted.add(first);
        if(rest.size()==0){
            return substituted;
        }
        return substitute(rest,l);
        }

    }


