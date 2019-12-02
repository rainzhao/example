package com.example.demo.classloader;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

/**
 * java edition of Twitter <b>Snowflake</b>, a network service for generating unique ID numbers at high scale with some
 * simple guarantees.
 *
 * https://github.com/twitter/snowflake
 */
public class SnowflakeGenerator {

    /*
     * bits allocations for timeStamp, datacenterId, workerId and sequence
     */

    private final long unusedBits = 1L;
    /**
     * 'time stamp' here is defined as the number of millisecond that have elapsed since the {@link #epoch} given by
     * users on {@link SnowflakeGenerator} instance initialization
     */
    private final long timestampBits = 41L;
    private final long datacenterIdBits = 5L;
    private final long workerIdBits = 5L;
    private final long sequenceBits = 12L;

    /*
     * max values of timeStamp, workerId, datacenterId and sequence
     */
    private final long maxDatacenterId = ~(-1L << datacenterIdBits); // 2^5-1
    private final long maxWorkerId = ~(-1L << workerIdBits); // 2^5-1
    private final long maxSequence = ~(-1L << sequenceBits); // 2^12-1

    /**
     * left shift bits of timeStamp, workerId and datacenterId
     */
    private final long timestampShift = sequenceBits + datacenterIdBits + workerIdBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long workerIdShift = sequenceBits;

    /*
     * object status variables
     */

    /**
     * reference material of 'time stamp' is '2016-01-01'. its value can't be modified after initialization.
     */
    private final long epoch = 1451606400000L;

    /**
     * data center number the process running on, its value can't be modified after initialization.
     * <p>
     * max: 2^5-1 range: [0,31]
     */
    private final long datacenterId;

    /**
     * machine or process number, its value can't be modified after initialization.
     * <p>
     * max: 2^5-1 range: [0,31]
     *
     */
    private final long workerId;

    /**
     * the unique and incrementing sequence number scoped in only one period/unit (here is ONE millisecond). its value
     * will be increased by 1 in the same specified period and then reset to 0 for next period.
     * <p>
     * max: 2^12-1 range: [0,4095]
     */
    private long sequence = 0L;

    /** the time stamp last snowflake ID generated */
    private long lastTimestamp = -1L;

    /**
     * generate an unique and incrementing id
     *
     * @return id
     */
    private synchronized long getNextId() {
        long currTimestamp = timestampGen();

        if (currTimestamp < lastTimestamp) {
            throw new IllegalStateException(String.format(
                "Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - currTimestamp));
        }

        if (currTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) { // overflow: greater than max sequence
                currTimestamp = waitNextMillis(currTimestamp);
            }

        } else { // reset to 0 for next period/millisecond
            sequence = 0L;
        }

        // track and memo the time stamp last snowflake ID generated
        lastTimestamp = currTimestamp;

        return ((currTimestamp - epoch) << timestampShift) | //
            (datacenterId << datacenterIdShift) | //
            (workerId << workerIdShift) | // new line for nice looking
            sequence;
    }

    // /**
    // * @param datacenterId
    // * data center number the process running on, value range: [0,31]
    // * @param workerId
    // * machine or process number, value range: [0,31]
    // */
    // private SnowflakeGenerator(long datacenterId, long workerId) {
    // if (datacenterId > maxDatacenterId || datacenterId < 0) {
    // throw new IllegalArgumentException(
    // String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
    // }
    // if (workerId > maxWorkerId || workerId < 0) {
    // throw new IllegalArgumentException(
    // String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
    // }
    //
    // this.datacenterId = datacenterId;
    // this.workerId = workerId;
    // }

    private SnowflakeGenerator() {
        this.datacenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
    }

    /**
     * track the amount of calling {@link #waitNextMillis(long)} method
     */
    private final AtomicLong waitCount = new AtomicLong(0);

    /**
     * @return the amount of calling {@link #waitNextMillis(long)} method
     */
    private long getWaitCount() {
        return waitCount.get();
    }

    /**
     * running loop blocking until next millisecond
     *
     * @param currTimestamp
     *            current time stamp
     * @return current time stamp in millisecond
     */
    private long waitNextMillis(long currTimestamp) {
        waitCount.incrementAndGet();
        while (currTimestamp <= lastTimestamp) {
            currTimestamp = timestampGen();
        }
        return currTimestamp;
    }

    /**
     * get current time stamp
     *
     * @return current time stamp in millisecond
     */
    private long timestampGen() {
        return System.currentTimeMillis();
    }

    /**
     * show settings of Snowflake
     */
    @Override
    public String toString() {
        return "Snowflake Settings [timestampBits=" + timestampBits + ", datacenterIdBits=" + datacenterIdBits
            + ", workerIdBits=" + workerIdBits + ", sequenceBits=" + sequenceBits + ", epoch=" + epoch
            + ", datacenterId=" + datacenterId + ", workerId=" + workerId + "]";
    }

    private long getEpoch() {
        return this.epoch;
    }

    /**
     * extract time stamp, datacenterId, workerId and sequence number information from the given id
     *
     * @param id
     *            a snowflake id generated by this object
     * @return an array containing time stamp, datacenterId, workerId and sequence number
     */
    private long[] parseId(long id) {
        long[] arr = new long[5];
        arr[4] = ((id & diode(unusedBits, timestampBits)) >> timestampShift);
        arr[0] = arr[4] + epoch;
        arr[1] = (id & diode(unusedBits + timestampBits, datacenterIdBits)) >> datacenterIdShift;
        arr[2] = (id & diode(unusedBits + timestampBits + datacenterIdBits, workerIdBits)) >> workerIdShift;
        arr[3] = (id & diode(unusedBits + timestampBits + datacenterIdBits + workerIdBits, sequenceBits));
        return arr;
    }

    /**
     * extract and display time stamp, datacenterId, workerId and sequence number information from the given id in
     * humanization format
     *
     * @param id
     *            snowflake id in Long format
     * @return snowflake id in String format
     */
    private String formatId(long id) {
        long[] arr = parseId(id);
        String tmf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(arr[0]));
        return String.format("%s, #%d, @(%d,%d)", tmf, arr[3], arr[1], arr[2]);
    }

    /**
     * a diode is a long value whose left and right margin are ZERO, while middle bits are ONE in binary string layout.
     * it looks like a diode in shape.
     *
     * @param offset
     *            left margin position
     * @param length
     *            offset+length is right margin position
     * @return a long value
     */
    private long diode(long offset, long length) {
        int lb = (int)(64 - offset);
        int rb = (int)(64 - (offset + length));
        return (-1L << lb) ^ (-1L << rb);
    }

    private static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long)mac[mac.length - 1])
                        | (0x0000FF00 & (((long)mac[mac.length - 2]) << 8))) >> 6;
                    id = id % (maxDatacenterId + 1);
                }
            }
        } catch (Exception ignored) {
        }
        return id;
    }

    private static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuilder mpid = new StringBuilder();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (name != null && !"".equals(name)) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    private static final SnowflakeGenerator SNOWFLAKE_GENERATOR = new SnowflakeGenerator();

    public static long nextId() {
        return SNOWFLAKE_GENERATOR.getNextId();
    }
}
