import java.util.*;
public class RomanToInt{

	public static void main(String args[]){
		Hashtable<Character,Integer> hash=new Hashtable<Character,Integer>();
		hash.put('I',1);
		hash.put('V',5);
		hash.put('X',10);
		hash.put('L',50);
		hash.put('C',100);
		hash.put('D',500);
		hash.put('M',1000);
		String str="DCCCXLVI";
		int res= convertRomanToInteger(hash,str);
		System.out.println(res);

	}

	// If need we can make the return type as long. 
	public static int convertRomanToInteger(Hashtable<Character,Integer> hash,String str){
		int sum=0;
		int prev=Integer.MAX_VALUE;
		for(int i=0;i<str.length();i++){
			int current=hash.get(str.charAt(i));
			//System.out.println(current);
			if(i==0)
				prev=current;
			else if(prev < current){
				current=current-2*prev;
				prev=current;
			}else{
				prev=current;
			}
			sum+=current;
		}
		return sum;
	}

}