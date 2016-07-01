package util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class XXD {

    protected static void printLineNumber(PrintStream stream, int idx) {
        stream.printf("%08x:", idx);
    }

    protected static void printHexCharOrSpace(PrintStream stream, byte[] byteBuffer, int offset) {
        if (offset < byteBuffer.length) {
            stream.printf("%02x", byteBuffer[offset]);
        } else {
            stream.printf("  ");
        }
    }

    protected static void printHexBytes(PrintStream stream, byte[] byteBuffer, int offset ) {
        int bytes = byteBuffer.length;
        for (int j = 0; j < 8; ++j) {
            stream.printf(" ");
            printHexCharOrSpace(stream, byteBuffer, offset+2*j);
            printHexCharOrSpace(stream, byteBuffer, offset+2*j+1);
        }
    }

    protected static void printPrintableChars(PrintStream stream, byte[] byteBuffer, int offset) {
        int bytes = byteBuffer.length;
        for (int j = 0; j < 16; ++j) {
            if (offset + j < bytes) {
                byte b = byteBuffer[offset + j];
                if (Character.isISOControl((char) b)) {
                    stream.printf(".");
                } else {
                    stream.append((char) b);
                }
            } else {
                stream.append(' ');
            }
        }
    }

    public static void print(byte[] byteBuffer) {
        int bytes = byteBuffer.length;
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        try {
            PrintStream stream = new PrintStream(bstream);
            for (int idx = 0; idx < bytes; idx += 16) {
                printLineNumber(stream, idx);
                printHexBytes(stream, byteBuffer, idx);
                stream.printf("  ");
                printPrintableChars(stream, byteBuffer, idx);
                stream.printf("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(bstream.toString());
    }
}
