package bitman;

public class BitManipulation {

    public static void main(String[] args) {
        BitManipulation bm = new BitManipulation();

        bm.castByteToInt((byte)-1);
    }



    private void castByteToInt(byte b) {
        System.out.println("byte as binary is " + Integer.toBinaryString(b & 0xFF));

        int i = b;

        System.out.println("byte cast to int is " + Integer.toBinaryString(i));




    }
}
