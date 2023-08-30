package test.algorithm.string;

public class FindIndex {
    public static void main( String[] args ) {
        String s = "BBC ABCDAB ABCDABCDABDE";
        String p = "ABCDABD";

        char[] sa = s.toCharArray();
        char[] pa = p.toCharArray();

        System.out.println( "force index: " + force( sa, pa ) );

        System.out.println( "sundayindex: " + sunday( sa, pa ) );

        System.out.println( "fact: " + s.indexOf( p ) );
    }

    /**
     * Sunday 算法
     * 
     * @param sa
     * @param pa
     * @return
     */
    static int sunday( char[] sa, char[] pa ) {

        int i = 0;
        int j = 0;

        while ( i <= ( sa.length - pa.length + j ) ) {
            if ( sa[i] != pa[j] ) {
                if ( i == ( sa.length - pa.length + j ) ) {
                    break;
                }
                int pos = contains( pa, sa[i + pa.length - j] );
                if ( pos == -1 ) {
                    i = i + pa.length + 1 - j;
                    j = 0;
                } else {
                    i = i + pa.length - pos - j;
                    j = 0;
                }
            } else {
                if ( j == ( pa.length - 1 ) ) {
                    return i - j; // break
                } else {
                    i++;
                    j++;
                }
            }
        }

        return -1;
    }

    static int contains( char[] pa, char sc ) {
        for ( int i = pa.length - 1; i >= 0; i-- ) {
            if ( pa[i] == sc ) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 暴力查找
     * 
     * @param sa
     * @param pa
     * @return
     */
    static int force( char[] sa, char[] pa ) {
        int si = 0, pi = 0;
        while ( si < sa.length && pi < pa.length ) {
            if ( sa[si] == pa[pi] ) {
                si++;
                pi++;
            } else {
                si = si - pi + 1;
                pi = 0;
            }
        }

        int i = -1;
        if ( pi == pa.length ) {
            i = si - pi;
        }
        return i;
    }
}
