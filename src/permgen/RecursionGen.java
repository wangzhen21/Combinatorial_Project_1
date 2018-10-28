package permgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecursionGen {
	//
	protected Integer per_len;
	protected List<String> permutation = new ArrayList<String>();
	//初始化序列
		protected void initSize(int per_size) {
			per_len = per_size;
			for (int i = 0; i < per_size; i++) {
				permutation.add(new Integer(1+i).toString());
			}
			Collections.sort(permutation,new Comparator<String>() {
	            @Override
	            public int compare(String o1, String o2) {
	                if(o1.compareTo(o2) > 0){
	                    return 1;
	                }
	                if(o1.compareTo(o2) < 0){
	                    return -1;
	                }
	                if(o1.compareTo(o2) == 0){
	                    return 0;
	                }
	                return 0;
	            }
	        });
		}
	private void swap(int i, int j){
		String a = permutation.get(i);
		permutation.set(i, permutation.get(j));
		permutation.set(j, a);
	}
	private void recursion(int start,int end) throws Exception {
		if (start == end){
			System.out.println(permutation);
			Thread.sleep(100);
			return;
		}
		else{
			for (int i = start; i <= end; i++){
				swap(start, i);
				recursion(start + 1, end);
				swap(start, i);
			}
		}
	}
	public void genPerm(int size) throws Exception {
		long start = System.currentTimeMillis();
		initSize(size);
		recursion(0,size - 1);
		long end = System.currentTimeMillis();
		System.out.println("time: " + (end - start) + "ms");
	}
	public static void main(String[] args)  throws Exception{
		String  a = "2";
		String b = "10";
		int sdfas = a.compareTo(b);
		RecursionGen lop = new RecursionGen();
		lop.genPerm(12);
	}
}
