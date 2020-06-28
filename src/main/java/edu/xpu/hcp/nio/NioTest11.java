package edu.xpu.hcp.nio;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于Buffer的Scattering和Gathering
 */
public class NioTest11 {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);
        int messageLength = 2+3+4;
        ByteBuffer[] byteBuffers = new ByteBuffer[3];

        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);
        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true){
            int bytesRead = 0;
            while (bytesRead < messageLength){
                long r = socketChannel.read(byteBuffers);
                bytesRead += r;
                System.out.println("Read: "+bytesRead);
                Arrays.asList(byteBuffers).stream().map(buffer->"position: "+buffer.position()+",limit:"+buffer.limit())
                        .forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(buffer-> buffer.flip());
            long bytesWritten = 0;
            while (bytesWritten < messageLength){
                long w = socketChannel.write(byteBuffers);
                bytesWritten += w;
            }
            Arrays.asList(byteBuffers).forEach(buffer-> buffer.flip());

            System.out.println("BytesRead:"+bytesRead+",ByteWritten:"+bytesWritten+",MessageLength:"+messageLength);
        }


    }
}
