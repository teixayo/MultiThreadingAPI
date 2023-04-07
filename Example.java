public class Example {

    public static void main(String[] args) {
        new Example().run();
    }

    public void run() {
        /*
          This code runs five times to demonstrate the elapsed times for single-threaded and multi-threaded execution.
         */
        for (int i = 0; i < 5; i++) {
            long start = System.currentTimeMillis();
            runSingleThread();
            long end = System.currentTimeMillis();

            System.out.println((end - start) + " MS | SINGLE THREAD");
        }

        for (int i = 0; i < 5; i++) {
            long start = System.currentTimeMillis();
            runMultiThreading();
            long end = System.currentTimeMillis();
            
            System.out.println((end - start) + " MS | MULTI THREADING (" + Runtime.getRuntime().availableProcessors() + " THREAD)");
        }
    }

    public void runSingleThread() {
        Chunk[] chunk = new Chunk[100];
        for (int i = 0; i < chunk.length; i++) {
            chunk[i] = new Chunk();
            chunk[i].setBlocks();
        }
    }

    public void runMultiThreading() {
        Chunk[] chunk = new Chunk[100];

        int threads = Runtime.getRuntime().availableProcessors();

        MultiThreadingAPI.runAsync(chunk.length, threads, integer -> {
            chunk[integer] = new Chunk();
            chunk[integer].setBlocks();
        });
    }
}

class Chunk {
    public final int[][][] blocks = new int[64][64][64];

    public void setBlocks() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                for (int k = 0; k < 64; k++) {
                    blocks[i][j][k] = i << 16 | j << 8 | k;
                }
            }
        }
    }
}
