package cn.zxf.akka.simple.demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SimpleMain {
    public static void main( String[] args ) throws InterruptedException {
	ActorSystem system = ActorSystem.create( "SimpleMain" );
	ActorRef ref = system.actorOf( Props.create( SimpleActor.class, "test-demo" ) );
	ref.tell( "fuck", ActorRef.noSender() );
	Thread.sleep( 100L );
	system.terminate();
    }
}
