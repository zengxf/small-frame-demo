package test.script.js;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class FundJs {

    public static void main( String[] args ) throws IOException, ScriptException {
        String url = "http://fund.eastmoney.com/pingzhongdata/001186.js";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName( "javascript" );
        System.out.println( engine.getClass() );
        engine.eval( new InputStreamReader( new URL( url ).openStream(), "UTF-8" ) );
        Object[] arr = (Object[]) engine.get( "Data_netWorthTrend" );
        System.out.println( arr.length );
    }

}
