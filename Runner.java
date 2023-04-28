package org.example;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture is non-blocking.
 * CompletableFuture object, you can use various non-blocking methods to retrieve the result, such as thenApply(), thenAccept(), or join()
 */
@Slf4j
public class Runner {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        log.info("START! {}", LocalTime.now().getNano());
//        multipleAsyn();
//        multipleFuParallel();
//        completeFeatureHandingEx();

        List numbers = new ArrayList();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Double> future2 = CompletableFuture.supplyAsync(() -> 10 / 2.5d);
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> 20);
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);

        allFutures.exceptionally(ex -> {
            log.info("Exception occurred: {}", ex.getMessage());
            return null; // Default value to return if there's an exception
        }).thenRun(() -> {
            int result1 = future1.join();
            double result2 = future2.join();
            int result3 = future3.join();
            numbers.add(result1);
            numbers.add(result2);
            numbers.add(result3);
            log.info("final result: {}", numbers);
        });
    }

    /**
     * thenAccept() method to specify what to do when the operation is complete
     */
    private static void completeFeatureHandingEx() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 0);

        future.exceptionally(ex -> {
            log.info("{} @ Exception occurred: [{}] | return default 0", LocalTime.now(), ex.getMessage());
            return 0; // Default value to return if there's an exception
        }).thenAccept(result -> log.info("accept result: " + result));

        future.thenAccept(result -> log.info(String.valueOf(result.intValue())));
    }

    /**
     * Handing error combine feature
     * @throws InterruptedException
     */
    private static void multipleFuParallelCombine() throws InterruptedException {
        List<String> list = new ArrayList<>();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Result 1 #" + LocalTime.now().getNano());
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Result 2 #" + LocalTime.now().getNano());
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "Result 3 #" + LocalTime.now().getNano());
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> "Result 4 #" + LocalTime.now().getNano());
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);

        //combine features
        allFutures.thenRun(() -> {
            log.info("INSIDE CONSUME +");
            // All futures completed
            String result1 = future1.join();
            String result2 = future2.join();
            String result3 = future3.join();
            String result4 = future4.join();
            list.add(result1);
            list.add(result2);
            list.add(result3);
            list.add(result4);
            log.info("{}", list);
        });
        log.info("END!");
        Thread.sleep(300);
    }

    /**
     * thenApplyAsync() method to chain two additional operations
     */
    private static void multipleAsyn() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync(result -> result * 2)
                .thenApplyAsync(result -> result + 5);

        future.thenAccept(result -> log.info(String.valueOf(result)));
    }
}
