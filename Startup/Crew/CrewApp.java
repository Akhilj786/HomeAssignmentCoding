import java.util.*;
public class CrewApp{

class Pair<T,Q> {
        public T val1;
        public Q val2;
        
        Pair(T val1, Q val2) {
            this.val1 = val1;
            this.val2 = val2;
        }
        @Override 
        public String toString(){
            return("("+val1+","+val2+")");
        }
  }
  public static void main (String[] args) {
    System.out.println("Hello Java");
    List<Integer> values=new ArrayList<Integer>();
    values.add(2);
    values.add(3);
    values.add(4);
    values.add(5);
    values.add(9);
    values.add(-2);
    values.add(5);
    int target=7;
    List<Pair<Integer, Integer>> pairs=getPairs(target,values);
    for(Pair pair:pairs){
        System.out.println(pair);
    }
  }
  
  public static List<Pair<Integer, Integer>> getPairs(int target, List<Integer> values) {
    CrewApp hw=new CrewApp();
    HashSet<Integer> set=new HashSet<Integer>();
    List<Pair<Integer, Integer>> list=new ArrayList<Pair<Integer, Integer>>();
    for(Integer i:values){
      Integer val=target-i;
      if(set.contains(i)){
        Pair p=hw.new Pair(i,val);
        list.add(p);
      }else{
        
        set.add(val);
        
      }
      
    }
    
    return list;
    
  
  }
}
