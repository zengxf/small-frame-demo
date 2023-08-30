package test.err;

public class Api {

    public void test() {

        // StackTraceElement[] stes = new Exception().getStackTrace();
        // for (StackTraceElement trace : stes) {
        // System.out.println(trace.getClassName());
        // }

        assertTrace();

    }

    private void assertTrace() {
        StackTraceElement[] stes = new Exception().getStackTrace();
        String target = stes[2].getClassName();
        if ( Call1.class.getName().equals( target ) ) {
            return;
        }
        throw new RuntimeException( "此Set方法不提供给其他类调用！" );
    }

}
