package cl.ucn.disc.hpc;

        import java.io.IOException;
        import java.util.concurrent.*;
        import static cl.ucn.disc.hpc.SecuencialRun.*;
/**
 * Benchmark example.
 */
class SecuencialRun implements Runnable {
    long n;
    int cant = 0;
    SecuencialRun(long n)
    {
        this.n = n;
    }
    @Override
    public void run() {
        int count = 0;
        int i = 1;
        while (count < n) {
            if (isPrime(i)) {
                count++;
            }
            i++;
            cant++;
        }
    }
    /**
     * isPrime Function!
     * @param n to long of the prime numbers
     * @return
     */
    public static boolean isPrime(final long n) {

        // Can't process negative numbers.
        if (n <= 0) {
            throw new IllegalArgumentException("Error in n: Can't process negative numbers");
        }

        // One isn't prime!
        if (n == 1) {
            return false;
        }

        // Two is prime!
        if (n == 2) {
            return true;
        }

        // Even numbers aren't prime!
        if (n % 2 == 0) {
            return false;
        }

        // Testing from 3 to sqrt(n)
        long max = (long) Math.sqrt(n);
        for (long i = 3; i <= max; i += 2) {
            // If module == 0 -> not prime!
            if (n % i == 0) {
                return false;
            }
        }

        return true;

    }
    public int getCant()
    {
        return cant;
    }
}

public class ParallelScript {

    public static float totalPrimes;
    static final int[] countfinal = {0};


    /**
     * The Main.
     */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        int threads = 4;

        long start = System.currentTimeMillis();
        ExecutorService executorMulti = Executors.newFixedThreadPool(threads);  // Creates a //ExecutorService object having a pool of 10 threads.
        System.out.println("Starting");
        SecuencialRun r1 = new SecuencialRun( 500000000);

        executorMulti.execute(r1);


        executorMulti.shutdown();
        System.out.println("Done");

        long end = System.currentTimeMillis();
        System.out.println("------MULTI THREAD WITH "+ threads + " THREADS--------");
        System.out.println("Time of ejecution: " + (end - start) + " ms");
        System.out.println("Result: " + r1.getCant());
        System.out.println("-----------------------------------------");
        System.out.println("Finished all threads");
    }


    //Parallel Instruction
    public static void SecuencialPrimes(int n) {
        int count = 0;
        int i = 2;
        while (count < n) {
            if (isPrime(i)) {
                count++;
            }
            i++;
        }
    }

}
