package com.viqsystems.Streams;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStream {
/*using the right data structure and then making it work in parallel guarantees the best performance*/
    public long parallelSum(long n) {
        //better use long int double rangeclosed instead of iterate
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }
    public long parallelRangedSum(long N) {
        return LongStream.rangeClosed(1, N)
                .parallel()
                .reduce(0L, Long::sum);
    }
   /*
   * stream.parallel()
    .filter(...
    .sequential
    .map(...)
    .parallel()
    .reduce();
    * But the last call to parallel or sequential wins and affects the pipeline globally. In
      this example, the pipeline will be executed in parallel because that’s the last call in the
      pipeline.
   * */

    /*
    When optimizing performance, you should always follow three
    golden rules: measure, measure, measure. To this purpose we will implement a microbenchmark
    using a library called Java Microbenchmark Harness (JMH). This is a toolkit that helps to create,
    in a simple, annotation-based way, reliable microbenchmarks
    for Java programs and for any other language targeting the Java Virtual Machine (JVM).
    * <dependency>
        <groupId>org.openjdk.jmh</groupId>
        <artifactId>jmh-core</artifactId>
        <version>1.17.4</version>
        </dependency>
        <dependency>
        <groupId>org.openjdk.jmh</groupId>
        <artifactId>jmh-generator-annprocess</artifactId>
        <version>1.17.4</version>
        </dependency>

        The first library is the core JMH implementation while the second contains an
        annotation processor that helps to generate a Java Archive (JAR) file through which you can
        conveniently run your benchmark once you have also added the following plugin to
        your Maven configuration :
        <build>
                <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <executions>
                <execution>
                <phase>package</phase>
                <goals><goal>shade</goal></goals>
                <configuration>
                <finalName>benchmarks</finalName>
                <transformers>
                <transformer implementation="org.apache.maven.plugins.shade.
                resource.ManifestResourceTransformer">
                <mainClass>org.openjdk.jmh.Main</mainClass>
                </transformer>
                </transformers>
                </configuration>
                </execution>
                </executions>
                </plugin>
                </plugins>
                </build>
    * */
}
