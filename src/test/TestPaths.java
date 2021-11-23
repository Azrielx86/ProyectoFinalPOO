package test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPaths {
    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(path.toString(), "data", "foo.txt");
        System.out.println(filePath.toString());
    }
}
