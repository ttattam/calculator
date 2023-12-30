import java.util.Scanner;
public class calculate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите первое число");
        float a = scanner.nextInt();
        System.out.println(a + " первое число");
        System.out.println("введите второе число");
        float b = scanner.nextInt();
        float c = a / b;
        System.out.println("ответ:"+c);

    }
}
