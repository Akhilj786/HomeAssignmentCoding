import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class StringChain{
    static class StringCompare implements Comparator<String> {
		public int compare(String o1, String o2) {
			if (o1.length() > o2.length())
				return 1;
			else if (o1.length() < o2.length())
				return -1;
			else
				return 0;

		}
	}

	static int longest_chain(String[] w) {
		Arrays.sort(w, new StringCompare());
		int n = w.length;
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		for (int i = 0; i < n; i++) {
			hmap.put(w[i], 1);
		}
		for (int i = 0; i < n; i++) {
			String word = w[i];
			for (int j = 0; j < word.length(); j++) {
				String temp = word.substring(0, j) + word.substring(j + 1);
				if (hmap.containsKey(temp)) {
					hmap.put(w[i], hmap.get(temp) + 1);
				}
			}
		}

		int max = 0;
		for (Map.Entry<String, Integer> entry : hmap.entrySet()) {
			int value = entry.getValue();
			max = Math.max(max, value);
		}

		return max;
	}

	public static void main(String args[]){
		String[] w = {"bda", "a", "b", "ba", "bca", "bdca"};
		int res = longest_chain(w);
		System.out.println(res);
	}

}