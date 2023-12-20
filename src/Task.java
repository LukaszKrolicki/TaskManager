import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Task implements Callable<String> {
    private final int id;

    public Task(int id) {
        this.id = id;
    }

    public Integer getTaskId(){
        return id;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(90000);
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("Task " + id + " zakończony");
        System.out.println("--------------------------------");
        return "Task " + id + " zakończony";
    }
}