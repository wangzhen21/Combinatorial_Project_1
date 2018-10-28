package permgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DictPermGen extends Thread{
	protected List<String> permutation = new ArrayList<String>();
	protected Integer per_len;
	private int threadNum;
	private int threadSequence;
	private int size;
	protected boolean last;
	protected List<List<String>> taskForAll = new ArrayList<List<String>>();
	private void genTask(int size) {
		for(int i = 0; i < permutation.size(); i++){
			for(int j = 0; j < permutation.size(); j++){
				List<String> temp = new ArrayList<String>();
				if (i != j) {
					temp.add(permutation.get(i));
					temp.add(permutation.get(j));
					for(int k = 0 ; k < permutation.size(); k++){
						if (k != i && k != j) {
							temp.add(permutation.get(k));
						}
					}
					taskForAll.add(temp);
				}
			}
		}
	}
	public DictPermGen() {
		
	}
	public DictPermGen(int size,int threadNum, int threadSequence) {
		this.size = size;
		initSize(size);
		genTask(size);
		this.threadNum = threadNum;
		this.threadSequence = threadSequence;
	}
	//初始化序列
	protected void initSize(int per_size) {
		per_len = per_size;
		last = false;
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
	public void run() {
		Thread t = Thread.currentThread();
		long start = System.currentTimeMillis();
		for(int i =  threadSequence*(taskForAll.size()/threadNum); i <((threadSequence + 1)*(taskForAll.size()/threadNum)); i ++){ 
			List<String> permutation = new ArrayList<String>();
			List<String> pre = new ArrayList<String>();
			pre.add(taskForAll.get(i).get(0));
			pre.add(taskForAll.get(i).get(1));
			for(int j = 2 ; j < taskForAll.get(i).size(); j ++){
				permutation.add(taskForAll.get(i).get(j));
			}
			genAllPermutation(size - 2,pre, permutation);
		}
		long end = System.currentTimeMillis();
		System.out.println(t.getName() + " time: "+ (end-start) +" ms");	
	}
	public void genAllPermutation(int per_size,List<String> pre,List<String> permutation) {
		long start = System.currentTimeMillis();
		int i, j, k;
		//获得排列的数量
		float num = getFactorial(per_size);
		//System.out.println(permutation);
		while(!end(permutation,per_size)) {
			for (i = per_size-2; i >= 0; i--) {
				if (num < 3) {
					
				}
				String a = permutation.get(i);
				String b = permutation.get(i+1);
				if(a.compareTo(b) < 0){
					String ttt = a;
				}
				if (permutation.get(i).compareTo(permutation.get(i+1)) < 0) {
					break;
				}
			}
			try
			{
			for (j = per_size-1; j > i; j--) {
				if (permutation.get(j).compareTo(permutation.get(i)) > 0) {
					break;
				}
			}
			swap(permutation,i, j);
			}
			catch (Exception e) {
				// TODO: handle exception
				int aaa = 0;
			}
			
			int mid = (per_size+i)/2;
			for (k = i+1; k <= mid; k++) {
				swap(permutation,k, per_size+i-k);
			}
		}
		//System.out.println(permutation);
	}
		public void genAllPermutation(int per_size) {
			long start = System.currentTimeMillis();
			int i, j, k;
			//获得排列的数量
			float num = getFactorial(per_size);
			initSize(per_size);
			printPermutation();
			while(!end(permutation)) {
				for (i = per_len-2; i >= 0; i--) {
					if (num < 3) {
						
					}
					String a = permutation.get(i);
					String b = permutation.get(i+1);
					if(a.compareTo(b) < 0){
						String ttt = a;
					}
					if (permutation.get(i).compareTo(permutation.get(i+1)) < 0) {
						break;
					}
				}
				try
				{
				for (j = per_len-1; j > i; j--) {
					if (permutation.get(j).compareTo(permutation.get(i)) > 0) {
						break;
					}
				}
				swap(i, j);
				}
				catch (Exception e) {
					// TODO: handle exception
					int aaa = 0;
				}
				
				int mid = (per_len+i)/2;
				for (k = i+1; k <= mid; k++) {
					swap(k, per_len+i-k);
				}
				//printPermutation();
				num--;
			}
			printPermutation();
			long end = System.currentTimeMillis();
			System.out.println("dict time: "+ (end-start) +" ms");	
		}
		private boolean end(List<String> permutation,int per_len) {
			for (int i = 0; i < per_len - 1; i++) {
				if (permutation.get(i).compareTo(permutation.get(i+1)) < 0) {
					return false;
				}
			}
			return true;
		}
		private boolean end(List<String> permutation) {
			for (int i = 0; i < per_len - 1; i++) {
				if (permutation.get(i).compareTo(permutation.get(i+1)) < 0) {
					return false;
				}
			}
			return true;
		}
		//输出排列
		protected void printPermutation() {
			for (String s: permutation) {
				System.out.print(s+" ");
			}
			System.out.println();
		}
		private float getFactorial(int n){
			float result = 1;
			while (n >= 1) {
				result *= n;
				n--;
			}
			return result;	
		}
		private void swap(List<String> permutation,int i, int j) {
			String a = permutation.get(i);
			String b = permutation.get(j);
			permutation.set(i, b);
			permutation.set(j, a);
		}
		private void swap(int i, int j) {
			String a = permutation.get(i);
			String b = permutation.get(j);
			permutation.set(i, b);
			permutation.set(j, a);
		}
		public static void main(String[] args) {
			String  a = "2";
			String b = "10";
			int sdfas = a.compareTo(b);
			DictPermGen lop = new DictPermGen();
			lop.genAllPermutation(12);
		}
}