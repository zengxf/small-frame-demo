package cn.zxf.agent.main;

import java.util.List;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class Jse6ThreadAttachTestMain {

    // 一个运行 Attach API 的线程子类
    static class AttachThread extends Thread {

        private final List<VirtualMachineDescriptor> listBefore;
        private final String                         jar;

        AttachThread( String attachJar, List<VirtualMachineDescriptor> vms ) {
            listBefore = vms; // 记录程序启动时的 VM 集合
            jar = attachJar;
        }

        public void run() {
            VirtualMachine vm = null;
            List<VirtualMachineDescriptor> listAfter = null;
            try {
                int count = 0;
                while ( true ) {
                    listAfter = VirtualMachine.list();
                    for ( VirtualMachineDescriptor vmd : listAfter ) {
                        // 如果 VM 有增加，我们就认为是被监控的 VM 启动了
                        // 这时，我们开始监控这个 VM
                        if ( !listBefore.contains( vmd ) ) {
                            vm = VirtualMachine.attach( vmd );
                            break;
                        }
                    }
                    Thread.sleep( 500 );
                    count++;
                    if ( null != vm || count >= 10 ) {
                        break;
                    }
                }
                vm.loadAgent( jar );
                System.out.println( "loadAgent done ..." );
                vm.detach();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args ) throws InterruptedException {
        String jar = "M:\\project\\zxf_super_demo\\small-frame\\jdk-demo\\instrument-demo\\my-agent-03\\build\\libs\\my-agent-03.jar";
        new AttachThread( jar, VirtualMachine.list() ).start();
    }

}
