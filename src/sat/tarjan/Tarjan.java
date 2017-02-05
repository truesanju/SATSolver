package sat.tarjan;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Tarjan {
    public static void main(String[] args) {

        ArrayList<int[]> test = new ArrayList<int[]>();
        test.add(new int[] {1,2}); // here it means 1 or -2
        test.add(new int[] {2,3});
        test.add(new int[] {3,4});
        test.add(new int[] {-2,-4});
        test.add(new int[] {-1,-3});
//        test.add(new int[] {0,2});
//        test.add(new int[] {0,3});
//        test.add(new int[] {2,1});
//        test.add(new int[] {3,1});
        test(test, 4);
    }

    public static void test(ArrayList<int[]> input, int literals){
        Graph ourgraph = new Graph(literals);
        for (int[] pair: input){
            int i = pair[0];
            int j = pair[1];
//            ourgraph.add_edge(i,j);
            ourgraph.add_edge(node_id_to_idx_of_matrix(-i), node_id_to_idx_of_matrix(j));
            ourgraph.add_edge(node_id_to_idx_of_matrix(-j), node_id_to_idx_of_matrix(i));
        }

        System.out.println("the adjacency matrix is "+"\n" + ourgraph.matrix_to_string(ourgraph.getadjmtx()));


        // find strong connected components
        ourgraph.find_all_strong_connected_component();
        ArrayList<ArrayList<Integer>> SCCs = ourgraph.getSCCs();

        //detect the cycles in SCC
        if(detect_of_cycle(SCCs)){
            System.out.println("FORMULA UNSATISFIABLE");
        }else {
            System.out.println("FORMULA SATISFIABLE");
        }
        System.out.println(SCCs.toString());

        //color the graph
        System.out.println(Arrays.toString(ourgraph.give_output()));
    }

    public static int node_id_to_idx_of_matrix(int i){
        if (i<0){
            i=-i-1;
            return (i<<1)+1;
        }else{
            return (i-1)<<1;
        }
    }

    public static boolean detect_of_cycle(ArrayList<ArrayList<Integer>> input){
        for(ArrayList<Integer> SCC: input){  // scc: strong connected component
            if (SCC.size()!=1){
                Set<Integer> seen = new HashSet<Integer>();
                for (int element : SCC){
                    if (seen.contains(element>>1)) return true;
                    else{
                        seen.add(element>>1);
                    }
                }
            }
        }
        return false;
    }

}
