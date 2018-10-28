package permgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

public class BackTracking {
	protected Integer per_len;
	protected List<String> permutation = new ArrayList<String>();
	public List<Integer> hasOccupied = new ArrayList<Integer>(); // 0 代表未被占用 1 代表已被占用
	public List<Integer> permutationRecord = new ArrayList<Integer>();
	//初始化序列
	protected void initSize(int per_size) {
		per_len = per_size;
		for (int i = 0; i < per_size; i++) {
			permutation.add(new Integer(1+i).toString());
			permutationRecord.add(1+i);
			hasOccupied.add(0);
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
	public void backTracking(int step) {
		if(step == 1)
		{
			//find the last number
			for(int i = 0 ; i < hasOccupied.size(); i ++ )
			{
				if (hasOccupied.get(i) == 0) {
					permutationRecord.set(step-1, i);
				}
			}
			//System.out.println(permutationRecord);
			return;
		}
		else{
			for(int i = 0 ; i < hasOccupied.size(); i ++){
				if (hasOccupied.get(i) == 0) {
					hasOccupied.set(i,1);
					permutationRecord.set(step-1, i);
					backTracking(step - 1);
					hasOccupied.set(i,0);
				}
			}
		}
	}
	//生成全部排列
	public void getAllGen(int size) {
		long start = System.currentTimeMillis();
		initSize(size);
		backTracking(size);
		long end = System.currentTimeMillis();
		System.out.println("time :" + (end - start) + "ms");
	}
	public static void main(String[] args) {
		String  a = "2";
		String b = "10";
		int sdfas = a.compareTo(b);
		BackTracking lop = new BackTracking();
		lop.getAllGen(13);
	}
}
