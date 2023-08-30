package test.new_features.jdk1_8.juc;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestAtomicInteger extends Util {
    public static void main( String[] args ) {
	test_updateAndGet();
	// test_accumulateAndGet();
	// test_getAndUpdate();
	// test_getAndAccumulate();
    }

    // 返回计算前的值
    static void test_getAndAccumulate() {
	AtomicInteger atomic = new AtomicInteger( 0 );
	int get = atomic.getAndAccumulate( 2, ( prev, base ) -> ( prev + 3 ) * base ); // 2 是 base
	log.info( "prev: {}, current: {}", get, atomic.get() );
    }

    // 返回更新前的值
    static void test_getAndUpdate() {
	AtomicInteger atomic = new AtomicInteger( 0 );
	int get = atomic.getAndUpdate( i -> i + 2 );
	log.info( "prev: {}, current: {}", get, atomic.get() );
    }

    // 返回计算后的值
    static void test_accumulateAndGet() {
	AtomicInteger atomic = new AtomicInteger( 0 );
	int get = atomic.accumulateAndGet( 2, ( i, base ) -> ( i + 2 ) * base );
	log.info( "current: {}, current: {}", get, atomic.get() );
    }

    // 返回更新后的值
    static void test_updateAndGet() {
	AtomicInteger atomic = new AtomicInteger( 0 );
	int get = atomic.updateAndGet( i -> i + 2 );
	log.info( "current: {}, current: {}", get, atomic.get() );
    }
}
