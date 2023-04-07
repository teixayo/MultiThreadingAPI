import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntConsumer;

/**
 * @author teixayo
 */

public final class MultiThreadingAPI {

    public static void runAsync(int size, int threads, IntConsumer action) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Callable<Void>> tasks = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            final int index = i;
            tasks.add(() -> {
                action.accept(index);
                return null;
            });
        }
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    public static void runAsync(int size, IntConsumer action) {
        runAsync(size, Runtime.getRuntime().availableProcessors(), action);
    }
}
