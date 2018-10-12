import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws Exception{
        System.out.println("Testing Memory!");
        Scanner input = new Scanner(System.in);

        Memory memory = new Memory(16, 16);

        IOgen.ACTION action;
        int a, f, l;
        int fileIndex = 0;

        memory.printMemory();

        while(true){
            System.out.print("Enter Action : 1 - Create, 2 - Read, 3 - Delete : \t");
            a = input.nextInt();
            switch (a){
                case 1:
                    System.out.print("Enter Link Factor (0/1) : \t");
                    l = input.nextInt();
                    System.out.print("Enter number of blocks : \t");
                    int n = input.nextInt();
                    System.out.println("File Index : \t" + fileIndex);
                    fileIndex = fileIndex + 1;
                    memory.createFile(l, n);
                    break;
                case 2:
                    System.out.print("Enter fileIndex : \t");
                    f = input.nextInt();
                    memory.readWriteFile(f);
                    break;
                case 3:
                    System.out.print("Enter fileIndex : \t");
                    f = input.nextInt();
                    memory.deleteFile(f);
                    break;
            }
            memory.printMemory();
        }

    }
}
