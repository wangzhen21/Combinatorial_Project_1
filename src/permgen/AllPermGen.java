package permgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class AllPermGen {
	private Integer[] radix;
	private int radix_digits;
	
	private static boolean reversable(List<Integer> l, Integer n) {
		int curr = -1;
		for (int j = (l.size()-1); j > -1; j--) {
			if (curr > l.get(j)) {
				curr = l.get(j);
				int nj = l.get(j);
				for (int i = l.size()-1; i > j; i--) {
					if (l.get(i) > nj) {
						l.set(j, l.get(i));
						l.set(i, nj);
						break;
					}
				}
				for (int i = 1; i <= (l.size()-j)/2; i++) {
					int temp = l.get(i+j);
					if (j+i < l.size() && l.size()-i >-1) {
						l.set(j+i, l.get(l.size()-i));
						l.set(l.size()-i, temp);
					}
				}
				return true;
			}
			curr = l.get(j);
		}
		return false;
	}
	private static void printList(List<Integer> l ) {
		for (int i = 0; i < l.size(); i++) {
			//System.out.print(l.get(i));
		}
		//System.out.println();
	}
	
	private static void dictGen(Set<Integer> n) {
		List<Integer> l = new ArrayList<Integer>(n);
		Collections.sort(l);
		//printList(l);
		while(reversable(l, n.size())) {
			//printList(l);
		}
	}
	
	private static void recursiveGen(Set<Integer> n, String p) {
	
		Iterator<Integer> i = n.iterator();
		Set<Integer> n2 = new HashSet<Integer>(n);
		n2.addAll(n);
		while (i.hasNext()) {
			Integer nn = i.next();
			n2.remove(nn);
			recursiveGen(n2, p + nn);
			n2.add(nn);
		}
		return ;
	}
	
	private int[] indexToRadix(int index) {
		int[] radixNum = new int[radix_digits];
		for (int i = 0; i < radix_digits ; i++) {
			radixNum[i] = index / radix[i];
			index = index % radix[i];
		}
		return radixNum;
	}
	
	public void increasingRadixGen(Set<Integer> n) {
		// Generate the radix
		radix_digits = n.size()-1;
		radix = new Integer[radix_digits];
		radix[radix_digits-1] = 1;
		// radix n-1 ... 3 2 1
		for (int i = radix_digits-2; i > -1; i--) {
			radix[i] = radix[i+1] * (radix_digits - i );
		}
		
		// start
		// calculate the max index
		int max = 2;
		for (int i = 1; i < radix_digits; i++) {
			max *= (i+2);
		}
		List<Integer> result = new ArrayList<Integer>(n);
		for (int index = 0; index < max; index++) {
			int[] radix_num = indexToRadix(index);
			List<Integer> available_digits = new ArrayList<Integer>();
			for (int i = 0; i < radix_digits+1; i++) {
				available_digits.add(i);
			}
			
			for (int i = 0; i < radix_digits; i++) {
				int temppos = available_digits.size() -1 - radix_num[i];
				int pos = available_digits.get(temppos);
				result.set(pos , radix_digits + 1 - i);
				available_digits.remove(temppos);
			}
			result.set(available_digits.get(0), 1);
			printList(result);
		}
		
	}
	
	public void decreasingRadixGen(Set<Integer> n) {
		// Generate the radix
		radix_digits = n.size()-1;
		radix = new Integer[radix_digits];
		radix[radix_digits-1] = 1;
		// radix 1 2 3 ... n-1
		for (int i = radix_digits-2; i > -1; i--) {
			radix[i] = radix[i+1] * (i+3);
		}
		
		// start
		// calculate the max index
		int max = 1;
		for (int i = 0; i < radix_digits; i++) {
			max *= (radix_digits + 1 - i);
		}
		List<Integer> result = new ArrayList<Integer>(n);
		for (int index = 0; index < max; index++) {
			int[] radix_num = indexToRadix(index);
			List<Integer> available_digits = new ArrayList<Integer>();
			for (int i = 0; i < radix_digits+1; i++) {
				available_digits.add(i);
			}
			
			for (int i = radix_digits - 1; i > -1; i--) {
				int temppos = available_digits.size() -1 - radix_num[i];
				int pos = available_digits.get(temppos);
				result.set(pos , i+2);
				available_digits.remove(temppos);
			}
			result.set(available_digits.get(0), 1);
			printList(result);
		}
	}

	private static boolean moveable(List<Integer> l, boolean[] direction) {
		// mobile numbers must be more than 1
		int max_mobile = 1;
		int index = -1;
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i) > max_mobile) {
				// left
				if (direction[i] == false && i-1 > -1 && l.get(i-1) < l.get(i)) {
					max_mobile = l.get(i);
					index = i;
				} else if (direction[i] == true && i+1 < l.size() && l.get(i+1) < l.get(i)) {
					max_mobile = l.get(i);
					index = i;
				}
			}
		}
		if (index != -1) {
			if (direction[index] == true) {
				l.set(index, l.get(index+1));
				boolean temp = direction[index];
				direction[index] = direction[index+1];
				l.set(index+1, max_mobile);
				direction[index+1] = temp;
			} else {
				l.set(index, l.get(index-1));
				boolean temp = direction[index];
				direction[index] = direction[index-1];
				l.set(index-1, max_mobile);
				direction[index-1] = temp;
			}
			for (int i = 0; i < l.size(); i++) {
				if (l.get(i) > max_mobile) {
					direction[i] = !direction[i];
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static void sjtGen(Set<Integer> n) {
		// initialize
		List<Integer> numbers = new ArrayList<Integer>(n);
		Collections.sort(numbers);
		// true for right, false for left
		boolean[] direction = new boolean[n.size()];
		for (int i = 0; i < n.size(); i++) {
			direction[i] = false;
		}
		printList(numbers);
		while (moveable(numbers, direction)) {
			printList(numbers);
		}
	}
	
	public static void printMsg(String msg) {
		System.out.println(msg);
	}
	
	public static void main(String[] args) {
		Integer n = 11;
		Set<Integer> nset = new HashSet<Integer>();
		for (int i = 1; i <= n; i++) {
			nset.add(i);
		}
		// Recursive Generation
		printMsg("Initiating Recursive Generation: ");
		long start = System.currentTimeMillis();
		recursiveGen(nset, "");
		long end = System.currentTimeMillis();
		printMsg("Running time is " + (end-start) + "ms");

		// Dictionary-order Generation
		printMsg("Initiating Dictionary-order Generation: ");
		start = System.currentTimeMillis();
		dictGen(nset);
		end = System.currentTimeMillis();
		printMsg("Running time is " + (end-start) + "ms");
		
		// Increasing & Decreasing Radix Generation
		AllPermGen a = new AllPermGen();
		printMsg("Initiating Increasing Radix Generation: ");
		start = System.currentTimeMillis();
		a.increasingRadixGen(nset);
		end = System.currentTimeMillis();
		printMsg("Running time is " + (end-start) + "ms");
		
		printMsg("Initiating Decreasing Radix Generation: ");
		start = System.currentTimeMillis();
		a.decreasingRadixGen(nset);
		end = System.currentTimeMillis();
		printMsg("Running time is " + (end-start) + "ms");
		
		// SJT Generation
		printMsg("Initiating SJT Generation: ");
		start = System.currentTimeMillis();
		sjtGen(nset);
		end = System.currentTimeMillis();
		printMsg("Running time is " + (end-start) + "ms");
	}
}
