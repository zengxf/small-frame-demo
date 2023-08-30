package test.new_features.jdk1_8.stream;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class TestSupplier {

    public static void main(String[] args) throws Throwable {
        Supplier<Stream<String>> streamSupplier = () -> Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> s.startsWith("a"));
        // 每次对 get() 的调用都构造了一个新的数据流
        streamSupplier.get().anyMatch(s -> true); // ok
        streamSupplier.get().noneMatch(s -> true); // ok

        testException();
    }

    static void testException() {
        Stream<String> stream = //
                Stream.of("d2", "a2", "b1", "b3", "c")//
                        .filter(s -> s.startsWith("a"));
        stream.anyMatch(s -> true); // ok
        stream.noneMatch(s -> true); // exception: stream closed
    }

}
