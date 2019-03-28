package cn.zxf.drools.demo;

import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
public class HelloWorldExample {

    public static final void main( final String[] args ) {
        // KieServices is the factory for all KIE services
        KieServices ks = KieServices.Factory.get();

        // From the kie services, a container is created from the classpath
        KieContainer kc = ks.getKieClasspathContainer();

        execute( kc );
    }

    public static void execute( KieContainer kc ) {
        // From the container, a session is created based on
        // its definition and configuration in the META-INF/kmodule.xml file
        KieSession ksession = kc.newKieSession( "HelloWorldKS" );

        // The application can also setup listeners
        ksession.addEventListener( new DebugAgendaEventListener() );
        ksession.addEventListener( new DebugRuleRuntimeEventListener() );

        // The application can insert facts into the session
        final Message message = new Message();
        message.setMessage( "Hello World" );
        message.setStatus( Message.HELLO );
        ksession.insert( message );

        // and fire the rules
        ksession.fireAllRules();

        // and then dispose the session
        ksession.dispose();
    }

    public static class Message {
        public static final int HELLO   = 0;
        public static final int GOODBYE = 1;

        private String          message;

        private int             status;

        public Message() {

        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage( final String message ) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus( final int status ) {
            this.status = status;
        }

        public static Message doSomething( Message message ) {
            return message;
        }

        public boolean isSomething( String msg, List<Object> list ) {
            list.add( this );
            return this.message.equals( msg );
        }
    }

}
