package com.level.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UTF8Grep {
    // 16 位的 Unicode 代码单元序列和字节序列之间的命名映射关系。
    // 此类定义了用于创建解码器和编码器以及检索与 charset 关联的各种名称的方法。
    // Charset and decoder for ISO-8859-15
    private static Charset charset = Charset.forName("UTF-8");// 测试中文。。
    private static CharsetDecoder decoder = charset.newDecoder();

    // Pattern used to parse lines
    private static final Pattern linePattern = Pattern.compile(".*\r?\n");

    // The input pattern that we're looking for
    private static Pattern pattern;

    // Compile the pattern from the command line
    private static void compile(String pat) {
        try {
            pattern = Pattern.compile(pat);
        } catch (PatternSyntaxException x) {
            System.err.println(x.getMessage());
            System.exit(1);
        }
    }

    // Use the linePattern to break the given CharBuffer into lines, applying
    // the input pattern to each line to see if we have a match
    private static void grep(File f, CharBuffer cb) {
        Matcher lm = linePattern.matcher(cb); // Line matcher
        Matcher pm = null; // Pattern matcher
        int lines = 0;
        while (lm.find()) {
            lines++;
            CharSequence cs = lm.group(); // The current line
            if (pm == null)
                pm = pattern.matcher(cs);
            else
                pm.reset(cs);
            if (pm.find())
                System.out.print(f + ":" + lines + ":" + cs);
            if (lm.end() == cb.limit())
                break;
        }
    }

    // Search for occurrences of the input pattern in the given file
    //
    private static void grep(File f) throws IOException {

        // Open the file and then get a channel from the stream
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();

        // Get the file's size and then map it into memory
        int sz = (int) fc.size();
        MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

        // Decode the file into a char buffer
        CharBuffer cb = decoder.decode(bb);
        // Perform the search
        grep(f, cb);
        // Close the channel and the stream
        fc.close();
    }

    public static void main(String[] args) {
        args = new String[] { "int", "src/main/resources/FrySightWordsListedByFrequency.txt" };
        if (args.length < 2) {
            System.err.println("Usage: java Grep pattern file...");
            return;
        }
        compile(args[0]);
        for (int i = 1; i < args.length; i++) {
            File f = new File(args[i]);
            try {
                grep(f);
            } catch (IOException x) {
                System.err.println(f + ": " + x);
            }
        }
    }

}