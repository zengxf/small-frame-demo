package cn.zxf.neuroph.ssc.common.data_set;

import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.data.DataSetRow;

import lombok.Data;

@Data
public class DataSetVo {

    public final List<DataSetRow> trainingSet = new ArrayList<>();
    public final List<DataSetRow> testSet     = new ArrayList<>();

}
