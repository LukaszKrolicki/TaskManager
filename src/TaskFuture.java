import java.util.concurrent.Future;

public class TaskFuture {
    private Future<String> future;
    private Integer idTask;
    public TaskFuture(Future<String> future, Integer idTask){
        this.future=future;
        this.idTask=idTask;
    }

    public Future<String> getFuture() {
        return future;
    }

    public Integer getIdTask() {
        return idTask;
    }
}
