package test.algorithm.endian;

import java.io.*;
import java.nio.ByteOrder;

/**
 * <p>
 * ref https://blog.csdn.net/duyiwuer2009/article/details/7455490
 * <br/>
 * Created by ZXFeng on 2022/3/8.
 */
public class CheckJvmIsBigEndian {

    public static void main(String[] args) throws IOException {
        System.out.println("---------------");
        ByteOrder byteOrder = ByteOrder.nativeOrder();
        System.out.println(byteOrder);
        System.out.println("---------------");
        bytesToInt();
        System.out.println("---------------");
        intToBytes();
        System.out.println("---------------");
    }

    static void bytesToInt() throws IOException {
        byte[] byteAr = new byte[4];
        byteAr[0] = 0x78;
        byteAr[1] = 0x56;
        byteAr[2] = 0x34;
        byteAr[3] = 0x12;
        ByteArrayInputStream bis = new ByteArrayInputStream(byteAr);
        DataInputStream dis = new DataInputStream(bis);
        System.out.println(Integer.toHexString(dis.readInt()));
    }

    static void intToBytes() throws IOException {
        int a = 0x12345678;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(a);
        byte[] b = bos.toByteArray();
        for (int i = 3; i >= 0; i--) {
            System.out.print(Integer.toHexString(b[i]));
        }
        System.out.println();
    }

}
