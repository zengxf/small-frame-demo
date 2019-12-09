package cn.zxf.neuroph.ssc.common.db;

import java.sql.ResultSet;

@FunctionalInterface
public interface PoTransformer< T > {

    T transform( ResultSet res );

}
