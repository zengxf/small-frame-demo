package test.new_features.jdk1_4.nio;

import java.nio.CharBuffer;

public class TestBuffer {
    public static void main( String[] args ) {
        CharBuffer buf = CharBuffer.allocate( 1024 );
        buf.put( 'c' ); // 鍐欐暟鎹?
        // inChannel.read(buf); // read into buffer

        buf.flip(); // 浠庡啓妯″紡鍒囨崲鍒拌妯″紡

        char c = buf.get(); // 璇绘暟鎹?
        // inChannel.write(buf); // read from buffer into channel.
        System.out.println( c );

        buf.rewind(); // 灏唒osition缃负0锛屽彲浠ラ噸澶嶈鍙朾uffer涓殑鏁版嵁
        System.out.println( buf.get() );

        {
            System.out.println( "-==-=-===--=-=-=" );
            buf.clear(); // 閲嶇疆position涓?0锛宭imit涓篶apacity锛屼竴浜涙暟鎹病鏈夎鍙栧畬锛岃皟鐢╟lear灏变細瀵艰嚧杩欓儴鍒嗘暟鎹鈥滈仐蹇樷??
            buf.put( 'a' );
            buf.put( 'b' );
            buf.flip();
            System.out.println( buf.get() );

            System.out.println( "-==-=-===--=-=-=" );
            buf.compact(); // 淇濈暀鏈鏁版嵁
            buf.put( 'c' );
            buf.flip();
            System.out.println( buf.get() );
            System.out.println( buf.get() );
        }

        {
            buf.clear();
            // 閫氳繃mark鏂规硶鍙互鏍囪褰撳墠鐨刾osition锛岄?氳繃reset鏉ユ仮澶峬ark鐨勪綅缃?
            buf.put( 'a' );
            buf.put( 'b' );
            buf.put( 'c' );

            buf.flip();
            System.out.println( "-==-=-===--=-=-=" );
            System.out.println( buf.get() );
            buf.mark();
            System.out.println( buf.get() );
            System.out.println( buf.get() );
            buf.reset();
            System.out.println( "----- 111" );
            System.out.println( buf.get() );
            System.out.println( buf.get() );
        }

        {
            // equals() 闇?婊¤冻锛? 鈥? 绫诲瀷鐩稿悓 鈥? buffer涓墿浣欏瓧鑺傛暟鐩稿悓 鈥? 鎵?鏈夊墿浣欏瓧鑺傜浉绛?
            // compareTo() 涔熸槸姣旇緝buffer涓殑鍓╀綑鍏冪礌
        }
    }
}
