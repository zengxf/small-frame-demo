package cn.zxf.neuroph.ssc.common.data;

import lombok.Builder;

@Builder
public class ParamVo {

    public int yyyy;
    public int mmMin;
    public int mmMax;
    public int ddMin;
    public int ddMax;
    public int periodMin;
    public int periodMax;

    public String toWhere() {
        String where = " WHERE yyyy=" + yyyy;

        if ( mmMin != 0 && mmMax == 0 ) {
            where += " AND mm = " + mmMin;
        } else if ( mmMin != 0 && mmMax != 0 ) {
            where += " AND mm BETWEEN " + mmMin + " AND " + mmMax;
        }

        if ( ddMin != 0 && ddMax == 0 ) {
            where += " AND dd = " + ddMin;
        } else if ( ddMin != 0 && ddMax != 0 ) {
            where += " AND dd BETWEEN " + ddMin + " AND " + ddMax;
        }

        if ( periodMin != 0 && periodMin == 0 ) {
            where += " AND period > " + periodMin;
        } else if ( periodMin != 0 && periodMax != 0 ) {
            where += " AND period BETWEEN " + periodMin + " AND " + periodMax;
        }

        return where;
    }

}
