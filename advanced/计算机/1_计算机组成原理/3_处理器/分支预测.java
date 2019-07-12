/**
 * 冒险与分支预测：今天下雨了，明天还会下雨吗？
 */
public class BranchPrediction {
    public static void main(String args[]) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j <1000; j ++) {
                for (int k = 0; k < 10000; k++) {
                }
            }
        }
        // 5ms
        System.out.println("Time spent in first loop is " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j <1000; j ++) {
                for (int k = 0; k < 100; k++) {
                }
            }
        }
        // 15ms
        System.out.println("Time spent in second loop is " + (System.currentTimeMillis() - start) + "ms");
    }
}
