# EasyPOI 测试

## 参考
1. https://blog.csdn.net/z28126308/article/details/100079790
2. https://zhuanlan.zhihu.com/p/457848706 
3. https://gitee.com/xiaorenwu_dashije/easypoi_example/blob/master/src/main/java/com/qjc/excel/utils/ExcelUtil.java

## 嵌套表达式解析原理
- `cn.afterturn.easypoi.excel.export.base.BaseExportService #createCells`
- `cn.afterturn.easypoi.excel.export.base.ExportCommonService #getCellValue`
- `cn.afterturn.easypoi.util.PoiPublicUtil #getParamsValue`
```java
public final class PoiPublicUtil {
    public static Object getParamsValue(String params, Object object) throws Exception {
        if (params.indexOf(".") != -1) { // 嵌套表达式-解析
            String[] paramsArr = params.split("\\.");
            return getValueDoWhile(object, paramsArr, 0); // 依次解析数组里的每个表达式
        }
        if (object instanceof Map) {
            return ((Map) object).get(params);
        }
        return PoiReflectorUtil.fromCache(object.getClass()).getValue(object, params);
    }

    public static Object getValueDoWhile(Object object, String[] paramsArr, int index) throws Exception {
        // ... // 省略对象为 null 或是图片的判断
        if (object instanceof Map) {
            object = ((Map) object).get(paramsArr[index]);
        } else {
            object = PoiReflectorUtil.fromCache(object.getClass()).getValue(object, paramsArr[index]);
        }
        // ... // 省略对象为集合判断
        return (index == paramsArr.length - 1) ? (object == null ? "" : object) // 最后一项则返回
                : getValueDoWhile(object, paramsArr, ++index); // 不是最后一项，依次递归解析
    }
}
```