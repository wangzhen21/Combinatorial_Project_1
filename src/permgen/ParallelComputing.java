package permgen;


public class ParallelComputing {
	public static void main(String[] args) {
		int size = 12;
		int threadNum = 1;
		for (int i = 0; i < threadNum; i++) {
			DictPermGen tt = new DictPermGen(size,threadNum,i);
			tt.start();
		}
	}
}
