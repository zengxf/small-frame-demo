package test.jdkapi.list;

import java.util.ArrayList;
import java.util.List;

public class MergeTest {

    public static void main( String[] args ) {
        List<String> cityNamesList = new ArrayList<>(); // 城市
        List<String> functionNamesList = new ArrayList<>(); // 职能类别
        List<String> industryNamesList = new ArrayList<>(); // 行业类别
        cityNamesList.add( "c1" );
        cityNamesList.add( "c2" );
        functionNamesList.add( "dd" );
        industryNamesList.add( "i1" );
        industryNamesList.add( "i2" );

        System.out.println( cityNamesList );
        System.out.println( functionNamesList );
        System.out.println( industryNamesList );
        System.out.println( "-------------" );
        System.out.println( "-------------" );

        List<RequestBo> list = RequestBo.merge( cityNamesList, functionNamesList, industryNamesList );
        System.out.println( "num: " + list.size() );
        for ( RequestBo requestBo : list ) {
            System.out.println( requestBo );
        }
    }

    private static class RequestBo implements Cloneable {
        public String cityNames;     //
        public String functionNames; //
        public String industryNames; //

        public static List<RequestBo> merge( List<String> cityNamesList, List<String> functionNamesList, List<String> industryNamesList ) {
            List<RequestBo> list = new ArrayList<>();
            List<RequestBo> temp = new ArrayList<>();
            RequestBo one = new RequestBo();

            List<RequestBo> citys = one.mergeCityNames( cityNamesList ); /** !!! */
            list.addAll( citys );

            for ( RequestBo bo : list ) {
                List<RequestBo> functions = bo.mergeFunctionNames( functionNamesList ); /** !!! */
                temp.addAll( functions );
            }
            list.clear();
            list.addAll( temp );

            temp.clear();
            for ( RequestBo bo : list ) {
                List<RequestBo> functions = bo.mergeIndustryNames( industryNamesList ); /** !!! */
                temp.addAll( functions );
            }
            list.clear();
            list.addAll( temp );

            return list;
        }

        private List<RequestBo> mergeIndustryNames( List<String> industryNamesList ) {
            List<RequestBo> list = new ArrayList<>();

            list.add( this );

            if ( isEmpty( industryNamesList ) ) {
                return list;
            }

            int size = industryNamesList.size();
            this.industryNames = industryNamesList.get( 0 ); /** !!! */

            for ( int i = 1; i < size; i++ ) {
                RequestBo bo = this.create();
                bo.industryNames = industryNamesList.get( i ); /** !!! */
                list.add( bo );
            }

            return list;
        }

        private List<RequestBo> mergeFunctionNames( List<String> functionNamesList ) {
            List<RequestBo> list = new ArrayList<>();

            list.add( this );

            if ( isEmpty( functionNamesList ) ) {
                return list;
            }

            int size = functionNamesList.size();
            this.functionNames = functionNamesList.get( 0 ); /** !!! */

            for ( int i = 1; i < size; i++ ) {
                RequestBo bo = this.create();
                bo.functionNames = functionNamesList.get( i ); /** !!! */
                list.add( bo );
            }

            return list;
        }

        private List<RequestBo> mergeCityNames( List<String> cityNamesList ) {
            List<RequestBo> list = new ArrayList<>();

            list.add( this );

            if ( isEmpty( cityNamesList ) ) {
                return list;
            }

            int size = cityNamesList.size();
            this.cityNames = cityNamesList.get( 0 ); /** !!! */

            for ( int i = 1; i < size; i++ ) {
                RequestBo bo = this.create();
                bo.cityNames = cityNamesList.get( i ); /** !!! */
                list.add( bo );
            }

            return list;
        }

        private RequestBo create() {
            RequestBo bo = null;
            try {
                bo = (RequestBo) this.clone();
            } catch ( CloneNotSupportedException e ) {
                e.printStackTrace();
            }
            return bo;
        }

        private boolean isEmpty( List<String> list ) {
            return list == null || list.isEmpty();
        }

        @Override
        public String toString() {
            return "RequestBo [cityNames=" + cityNames + ", functionNames=" + functionNames + ", industryNames=" + industryNames + "]";
        }

    }
}
