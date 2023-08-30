package test.jdkapi.io.file;

import java.io.FileReader;
import java.io.StreamTokenizer;

/**
 * 统计文件中的 数字，单词 个数
 * <p>
 * Created by zengxf on 2019-05-30
 */
public class StatisticWordAndNumber {

    public static void main( String[] args ) throws Exception {
        String file = "C:\\Users\\Administrator\\Desktop\\aa\\test\\test.txt";
        FileReader fr = new FileReader( file );
        StreamTokenizer st = new StreamTokenizer( fr );

        st.slashSlashComments( true );
        st.slashStarComments( true );

        int num = 0, str = 0;
        while ( st.nextToken() != StreamTokenizer.TT_EOF ) {
            System.out.println( "token：" + st.toString() );
            if ( st.ttype == StreamTokenizer.TT_NUMBER ) {
                System.out.println( "数：" + st.nval );
                num++;
            }
            if ( st.ttype == StreamTokenizer.TT_WORD ) {
                System.out.println( "单词：" + st.sval );
                str++;
            }
        }
        System.out.println( "num: " + num + ", str: " + str );
    }

}
