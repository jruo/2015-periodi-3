package com.hiilimonoksidi.tiralabra;

import java.util.StringTokenizer;
import org.junit.Test;

public class MainTest {

    @Test
    public void main() {
        Main.main(tokenize("-i test-images/circles.png -o test-images/circles/ -s 0,0 -g 199,199"));
        Main.main(tokenize("-i test-images/lines.png -o test-images/lines/ -s 0,0 -g 299,299"));
        Main.main(tokenize("-i test-images/maze.png -o test-images/maze/ -s 2,0 -g 269,271"));
        Main.main(tokenize("-i D:/janne/desktop/big-maze.png -s 3,0 -g 1801,1799 -t 20"));
    }
    
    private String[] tokenize(String string) {
        StringTokenizer tokenizer = new StringTokenizer(string, " ");
        String[] strings = new String[tokenizer.countTokens()];
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            strings[i++] = tokenizer.nextToken();
        }
        return strings;
    }
}
