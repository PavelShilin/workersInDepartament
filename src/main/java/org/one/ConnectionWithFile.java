package org.one;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConnectionWithFile {

    protected static BufferedReader connectionOpen() throws FileNotFoundException {
        return new BufferedReader(new FileReader("src/main/resources/Base.txt"));
    }
    protected static void connectionClose(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
