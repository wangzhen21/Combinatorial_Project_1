package permgen;

import java.util.ArrayList;
import java.util.List;


public class CyclicShift {
	public ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
	public  ArrayList<ArrayList<Integer>> genCyclicShift(int size) {
		if (size == 1) {
			ArrayList<ArrayList<Integer>> genList = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> one = new ArrayList<Integer>();
			one.add(1);
			genList.add(one);
			return genList;
		}
		else
		{
			ArrayList<ArrayList<Integer>> genList = genCyclicShift(size - 1);
			ArrayList<ArrayList<Integer>> returnGenList = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < genList.size(); i ++){
				for(int j = 0 ; j <= size - 1 ; j ++){
					ArrayList<Integer> temp =  genList.get(i);
					ArrayList<Integer> newTemp = new ArrayList<Integer>();
					for(int jj = 0 ; jj < temp.size(); jj++){
						newTemp.add(temp.get(jj));
					}
					if(size != 11)
					{
						newTemp.add(j, size);
						returnGenList.add(newTemp);
					}
				}
			}
			if(size == 11)
				return genList;
			else
				return returnGenList;
		}
	}
	public static void main(String[] args) {
		String  a = "2";
		String b = "10";
		int sdfas = a.compareTo(b);
		CyclicShift lop = new CyclicShift();
		long start = System.currentTimeMillis();
		ArrayList<ArrayList<Integer>> genSequence =  lop.genCyclicShift(11);
		System.out.println("num" + genSequence.size());
		long end = System.currentTimeMillis();
		System.out.println("time" + (end-start) + " ms");
		for(int i = 0; i < genSequence.size(); i++){
			//System.out.println(genSequence.get(i).toString());
		}
	}
}
