import java.lang.*;
import java.util.*;


public class Aligner
{
    static boolean right = false;
    static boolean center = false;
    static boolean justify = false;
    static int w = 60;

    public static void main(String[] args)
    {        
        boolean nextW = false;
        
        for(int i = 0; i < args.length; i++) {
            String arg = args[i];
            
            if(arg.equals("--right")) {
                right = true;
            }
            else if (arg.equals("--center")) {
                center = true;
            }
            else if (arg.equals("--justify")) {
                justify = true;
            }
            else if(arg.equals("-w")) {
                nextW = true;
            }
            else if(nextW) {
                nextW = false;
                w = Integer.valueOf(arg);
            }
        }
        
        int length = 0; //delka vsech slov bez mezer
        LinkedList<String> words = new LinkedList<>(); //jednotliva slova v arrayi
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            String word = sc.next();
            
            if(word.length() == w) { //slovo je stejne dlouhe jako radek
                //vytisknu stara nevytistena slova
                printLine(words, length);
                words.clear();
                length = 0;
                
                //vytisknu to stejne dlouhe slovo
                System.out.println(word);
            } else if(word.length() > w) { // slovo je delsi nez radek
                //vytisknu stara nevytistena slova
                printLine(words, length);
                words.clear();
                length = 0;
                
                //rozseknu slovo a vytisknu vsechny cele casti
                String subWord;
                int lines = (int) Math.floor(word.length() / (float) w);
                for(int i = 0; i < lines; i++) {
                    System.out.println(word.substring(i*w, i*w + w));
                }
                
                //ulozim zbytek
                word = word.substring(lines*w);
                words.add(word);
                length += word.length();
            } else if(length + words.size() + word.length() > w) { //radek s pridavanym slovem by byl delsi nez ma byt
                //vytisknu radek
                printLine(words, length);
                words.clear();
                length = 0;
                
                //zalozim novy radek
                words.add(word);
                length += word.length();
            } else {
                //pridam slovo do radku
                words.add(word);
                length += word.length();
            }
        }
        
        //vytisknu posledni radek
        printLine(words, length);
    }
    
    private static void printLine(LinkedList<String> words, int length) {
        if(length == 0 || words.size() == 0) return;
        
        if(justify) {
            printLineJustify(words, length);
            return;
        }
        
        int lengthWithSpaces = length + words.size() - 1;
        int space = w - lengthWithSpaces;
        
        if(center) {
            float spaceCenter = space / 2f;
            printSpace((int) Math.round(spaceCenter));
        } else if(right) {
            printSpace(space);
        }
        
        for(int i = 0; i < words.size(); i++) {
            System.out.print(words.get(i));
            System.out.print(" ");
        }
        
        System.out.println();
    }
    
    private static void printLineJustify(LinkedList<String> words, int length) {
        if(words.size() == 1) {
            System.out.println(words.get(0));
            return;
        }
        
        int wordsCount = words.size();
        float space = (w - length) / (float) (wordsCount - 1);
        
        String word;
        int lengthWordsLeft = length;
        int printed = 0;
        
        //tiskneme vsechna slova krome poslednich dvou
        for(int i = 0; i < wordsCount - 2; i++) {
            word = words.get(i);
            System.out.print(word);
            lengthWordsLeft -= word.length();
            
            int thisSpace = (int) Math.ceil(space);
            printed += word.length() + thisSpace;
            printSpace(thisSpace);
            
            space = (w - printed - lengthWordsLeft) / (float) (wordsCount - i - 1);
        }
        
        //tiskneme posledni dve slova
        word = words.get(wordsCount - 2);
        System.out.print(word);
        printed += word.length();
       
        word = words.get(wordsCount - 1);
        printed += word.length();
        
        printSpace(w - printed);
        
        System.out.print(word);
        System.out.println();
    }
    
    private static void printSpace(int space) {
        for(int i = 0; i < space; i++) {
            System.out.print(" ");
        }
    }
}
