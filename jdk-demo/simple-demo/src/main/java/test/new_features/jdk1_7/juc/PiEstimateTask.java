package test.new_features.jdk1_7.juc;

import java.util.concurrent.RecursiveTask;

public class PiEstimateTask extends RecursiveTask<Double> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final long	      begin;
    private final long	      end;
    private final long	      threshold;	    // 分割任务的临界值

    public PiEstimateTask( long begin, long end, long threshold ) {
	this.begin = begin;
	this.end = end;
	this.threshold = threshold;
    }

    @Override
    protected Double compute() {
	if ( end - begin <= threshold ) {

	    int sign = 1; // 符号，取 1 或者 -1
	    double result = 0.0;
	    for ( long i = begin; i < end; i++ ) {
		result += sign / ( i * 2.0 + 1 );
		sign = -sign;
	    }

	    return result * 4;
	}

	// 分割任务
	long middle = ( begin + end ) / 2;
	PiEstimateTask leftTask = new PiEstimateTask( begin, middle, threshold );
	PiEstimateTask rightTask = new PiEstimateTask( middle, end, threshold );

	leftTask.fork(); // 异步执行 leftTask
	rightTask.fork(); // 异步执行 rightTask

	double leftResult = leftTask.join(); // 阻塞，直到 leftTask 执行完毕返回结果
	double rightResult = rightTask.join(); // 阻塞，直到 rightTask 执行完毕返回结果

	return leftResult + rightResult; // 合并结果
    }

}