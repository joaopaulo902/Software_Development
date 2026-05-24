public class Main {

    public static void main(String[] args){
        TextProcessor testFile = new TextProcessor();
        String input = testFile.LoadFile("test.txt");
        TestParser.functionality(input);
    }
}
