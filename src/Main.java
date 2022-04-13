import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        LinkedList<String> obstacles  = new LinkedList<String>();
        String s1 = "Darina";
        String s2 = "Tehila";
        String s3 = "Dana";
        String s4 = "Yosi";
        String s5 = "Dani";
        obstacles.add(s1);
        obstacles.add(s2);
        obstacles.add(s3);
        System.out.println(obstacles);
        obstacles.removeFirst();
        System.out.println(obstacles);
        obstacles.add(s4);
        System.out.println(obstacles);

    }


}
