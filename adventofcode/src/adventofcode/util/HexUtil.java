package adventofcode.util;

public class HexUtil
{

    public static String toHexString(byte[] b)
    {
        String result = "";
        for (byte b2 : b)
        {
            int b3 = (b2 < 0) ? (b2 & 0x7f) + 0x80 : b2;

            String x = Integer.toHexString(b3);
            result += (x.length() == 1) ? "0" + x : x;
        }
        return result;
    }
}
