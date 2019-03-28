package cn.zxf.test;

import lombok.extern.slf4j.Slf4j;
import net.openhft.affinity.Affinity;
import net.openhft.affinity.AffinityLock;
import net.openhft.affinity.AffinityStrategies;

@Slf4j
public class ThreadTest {
    public static void main( String[] args ) {
	log.info( "cpu: {}, process: {}", Affinity.getCpu(), Affinity.getAffinityImpl().getProcessId() );
	Runnable r = () -> {
	    int threadId = Affinity.getThreadId();
	    System.out.println( threadId );
	};
	for ( int i = 0; i < 10; i++ ) {
	    new Thread( r ).start();
	}
	try ( final AffinityLock al = AffinityLock.acquireLock() ) {
	    System.out.println( "Main locked" );
	    Thread t = new Thread( new Runnable() {
		@Override
		public void run() {
		    try ( AffinityLock al2 = al.acquireLock( AffinityStrategies.SAME_SOCKET, AffinityStrategies.ANY ) ) {
			System.out.println( "Thread-0 locked" );
			log.info( "cpu: {}", Affinity.getCpu() );
		    }
		}
	    } );
	    t.start();
	}
    }
}
