import oop.ex5.*;

import java.io.*;
import java.util.*;

public class Main5 {
    public static void main(String[] args) {
        try {
            Map<String,String> cli = Util.parseArgs(args);
            String in   = cli.get("--pattern");
            String stepsS = cli.get("--steps");
            String out  = cli.get("--dump-final");
            if (in == null || stepsS == null || out == null) usage("Missing: --pattern --steps --dump-final");

            // TODO

        } catch (Throwable t) {
            fail(t.getMessage() == null ? t.toString() : t.getMessage());
        }
    }

    private static void usage(String msg) {
        System.err.println(msg);
        System.err.println("Usage: java Main5 --pattern <input.pattern> --steps <N> --dump-final <output.pattern> "
                + "[--rule B3/S23] [--neighborhood MOORE8|VONN4|HEX6|FACES6] [--world WORLD_2D|WORLD_HEX|WORLD_3D] [--size N] [--wrap true|false]");
        System.exit(2);
    }
    private static void fail(String msg){ System.err.println("Error: " + msg); System.exit(1); }
}
