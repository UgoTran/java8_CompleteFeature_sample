# java8_CompleteFeature_sample

Project maven:
Demo compate feature, that is java 8 feature
1. Dependencies
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.26</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.3.5</version>
        </dependency>
    </dependencies>
  
2. Knowledge
- CompletableFuture is non-blocking
- CompletableFuture object, you can use various non-blocking methods to retrieve the result, such as thenApply(), thenAccept(), or join()
- Composition: CompletableFuture provides methods such as thenCompose(), thenCombine(), and allOf() that make it easy to compose multiple asynchronous operations and to handle their results in a non-blocking way
- Exception Handling: can handle exceptions in a more declarative way using methods like exceptionally() and handle()
- Completion: can complete it explicitly by calling complete(), completeExceptionally(), or cancel() methods
