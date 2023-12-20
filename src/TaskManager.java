import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskManager {
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final List<TaskFuture> taskList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Stwórz nowy Task");
            System.out.println("2. Zapytaj o stan Taska");
            System.out.println("3. Anuluj Taska");
            System.out.println("4. Pokaż wszystkie aktualne taski");
            System.out.println("5. Wyjście");
            System.out.print("Wpisz opcję: ");
            int wybor = scanner.nextInt();
            scanner.nextLine();

            switch (wybor) {
                case 1:
                    stworzNowegoTaska();
                    break;
                case 2:
                    zapytajOstatus();
                    break;
                case 3:
                    anulujTaska();
                    break;
                case 4:
                    listTaskow();
                    break;
                case 5:
                    executor.shutdown();
                    return;
                default:
                    System.out.println("Zły wybór.");
                    System.out.println("-----------------------------------");
            }
        }
    }

    private static void stworzNowegoTaska() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj ID taska: ");
        int taskId = scanner.nextInt();
        scanner.nextLine();

        Task task = new Task(taskId);
        Future<String> future = executor.submit(task);
        TaskFuture x = new TaskFuture(future,taskId);
        taskList.add(x);
        System.out.println("Task " + taskId + " stworzony.");
        System.out.println("-----------------------------------");
    }

    private static void zapytajOstatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wpisz ID taska: ");
        int taskId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (TaskFuture future : taskList) {
            if (future.getIdTask().equals(taskId) && future.getFuture().isDone()) {
                try {
                    String result = future.getFuture().get();
                    System.out.println("Task " + taskId + " stan: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Task " + taskId + " bład: " + e.getMessage());
                }
            } else if(future.getIdTask().equals(taskId)){
                System.out.println("Task " + taskId + " nadal się wykonuje");
            }
        }
        System.out.println("-----------------------------------");
    }

    private static void anulujTaska() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wpisz ID taska: ");
        int taskId = scanner.nextInt();
        scanner.nextLine();

        for (TaskFuture future : taskList) {
            if (future.getIdTask().equals(taskId)) {
                future.getFuture().cancel(true);
                System.out.println("Task " + taskId + " anulowany");
            }
        }
        System.out.println("-----------------------------------");
    }

    private static void listTaskow() {
        System.out.println("Wykonywane Taski:");
        for (TaskFuture future : taskList) {
            if (!future.getFuture().isDone()) {
                System.out.println("Task ID: " + future.getIdTask());
            }
        }
        System.out.println("-----------------------------------");
    }
}