package test.jdkapi.regexp;

public class TestErrorRegex {

    public static void main( String[] args ) {
        String badRegex = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+)\\.)+([A-Za-z0-9-~\\\\/])+$";
        String bugUrl = "http://www.fapiao.com/dddp-web/pdf/download?request=6e7JGxxxxx4ILd-kExxxxxxxqJ4-CHLmqVnenXC692m74H38sdfdsazxcUmfcOH2fAfY1Vw__%5EDadIfJgiEf";
        if ( bugUrl.matches( badRegex ) ) {
            System.out.println( "match!!" );
        } else {
            System.out.println( "no match!!" );
        }
    }

}
