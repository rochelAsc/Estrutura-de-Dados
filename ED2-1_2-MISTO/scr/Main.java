import java.util.*;


public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean LoopFlag = true;


    while (LoopFlag) {
        System.out.println("Escolha um método de armazenamento:\n" +
                "1 - Hash Table\n" +
                "2 - Arvore AVL\n" +
                "3 - Arvore RB\n");
        String StorageMethod = scanner.next();
        switch (StorageMethod) {
            case "1":
                System.out.println("ok.");
            case "2":

            case "3":
                LoopFlag = false;

            default:
                System.out.println("Tente novamente.");

        }
    }


    System.out.println("Chegou ao fim do codigo");
    scanner.close();
}