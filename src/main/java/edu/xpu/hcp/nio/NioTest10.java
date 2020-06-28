package edu.xpu.hcp.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件锁
 */
public class NioTest10 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt","rw");
        FileChannel channel = randomAccessFile.getChannel();
        FileLock lock = channel.lock(3, 6,true);
        System.out.println(lock.isValid());
        System.out.println(lock.isShared());
        lock.release();
        randomAccessFile.close();
    }
}
