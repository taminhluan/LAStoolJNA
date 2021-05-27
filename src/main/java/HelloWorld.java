import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/** Simple example of JNA interface mapping and usage. */
public class HelloWorld {

    // This is the standard, stable way of mapping, which supports extensive
    // customization and mapping of Java to native types.

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
                Native.load("C:\\Users\\luantm\\Workshop\\Personal\\learn_jna\\JavaClient\\target\\math_library",
                        CLibrary.class);
        int add(int a, int b);

        int sub(int a, int b);
    }

    public static void main(String[] args) {
        System.out.println( " 1 + 2 = " + CLibrary.INSTANCE.add(1, 2));
        System.out.println( " 1 - 2 = " + CLibrary.INSTANCE.sub(1, 2));
    }
}