package top.hiccup;

import java.util.UUID;

/**
 * 全局唯一ID：
 * <p>
 * 1、UUID：无序，不能保证递增，采用字符存储，查询传输慢
 * <p>
 * 2、snowflake算法：twitter分布式主键ID生成算法，无序且强依赖时钟，多台服务器时钟要同步
 *          1bit+41bit+10bit+12bit=64bit
 *          固定0（为1的话则生成的ID都是负数了）+毫秒数（2^41-1毫秒， z约69年）+机器码（数据中心+机器ID）+流水号
 *
 * 3、MySql自增主键：假设有100台MySql数据库，则自增步长设置为100，可扩展性非常差
 *          如库A的id=100，200，300 库B的id=101，201，301
 * <p>
 * 4、
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class UniqueIdTest {

    public static String test1() {
        return UUID.randomUUID().toString();
    }

    public static long test2() {
        SnowFlakeGenerator generator = new SnowFlakeGenerator.Factory().create(234001L, 1L);
        return generator.nextId();
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}

class SnowFlakeGenerator {

    /**
     * 某个固定的时间
     */
    private final static long START_STAMP = 1547431170362L;
    /**
     * 可分配的位数(10位机器码+12位序列号)
     */
    private final static int REMAIN_BIT_NUM = 22;
    private long idcId;
    private long machineId;
    /**
     * 当前序列号
     */
    private long sequence = 0L;
    /**
     * 上次最新时间戳
     */
    private long lastStamp = -1L;
    /**
     * 时间戳偏移量：一次计算出，避免重复计算
     */
    private int timestampBitLeftOffset;
    /**
     * idc偏移量：一次计算出，避免重复计算
     */
    private int idcBitLeftOffset;
    /**
     * 机器id偏移量：一次计算出，避免重复计算
     */
    private int machineBitLeftOffset;
    /**
     * 最大序列值：一次计算出，避免重复计算
     */
    private int maxSequenceValue;

    private SnowFlakeGenerator(int idcBitNum, int machineBitNum, long idcId, long machineId) {
        int sequenceBitNum = REMAIN_BIT_NUM - idcBitNum - machineBitNum;
        if (idcBitNum <= 0 || machineBitNum <= 0 || sequenceBitNum <= 0) {
            throw new IllegalArgumentException("error bit number");
        }
        this.maxSequenceValue = ~(-1 << sequenceBitNum);
        this.machineBitLeftOffset = sequenceBitNum;
        this.idcBitLeftOffset = idcBitNum + sequenceBitNum;
        this.timestampBitLeftOffset = idcBitNum + machineBitNum + sequenceBitNum;
        this.idcId = idcId;
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long currentStamp = getTimeMill();
        if (currentStamp < lastStamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastStamp - currentStamp));
        }
        //新的毫秒，序列从0开始，否则序列自增
        if (currentStamp == lastStamp) {
            sequence = (sequence + 1) & this.maxSequenceValue;
            if (sequence == 0L) {
                //Twitter源代码中的逻辑是循环，直到下一个毫秒
                lastStamp = tilNextMillis();
//                throw new IllegalStateException("sequence over flow");
            }
        } else {
            sequence = 0L;
        }
        lastStamp = currentStamp;
        return (currentStamp - START_STAMP) << timestampBitLeftOffset | idcId << idcBitLeftOffset | machineId << machineBitLeftOffset | sequence;
    }

    private long getTimeMill() {
        return System.currentTimeMillis();
    }

    private long tilNextMillis() {
        long timestamp = getTimeMill();
        while (timestamp <= lastStamp) {
            timestamp = getTimeMill();
        }
        return timestamp;
    }

    public static class Factory {
        /**
         * twitter默认数据中心占用的位数
         */
        private final static int DEFAULT_IDC_BIT_NUM = 5;
        private final static int DEFAULT_MACHINE_BIT_NUM = 5;
        private int idcBitNum;
        private int machineBitNum;

        public Factory() {
            this.idcBitNum = DEFAULT_IDC_BIT_NUM;
            this.machineBitNum = DEFAULT_MACHINE_BIT_NUM;
        }
        public Factory(int machineBitNum, int idcBitNum) {
            this.idcBitNum = idcBitNum;
            this.machineBitNum = machineBitNum;
        }

        public SnowFlakeGenerator create(long idcId, long machineId) {
            return new SnowFlakeGenerator(this.idcBitNum, this.machineBitNum, idcId, machineId);
        }
    }
}