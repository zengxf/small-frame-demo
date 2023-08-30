package test.jvm.reload_class.netCL;

public class NetClassLoaderSimple {

    private NetClassLoaderSimple instance;

    public void setNetClassLoaderSimple( Object obj ) {
	this.instance = (NetClassLoaderSimple) obj;
	System.out.println( "------> instance: " + instance );
    }

}
