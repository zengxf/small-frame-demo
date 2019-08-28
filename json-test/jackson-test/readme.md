# Jackson 测试

## API 说明
### 不返回 null 值
- 例：`mapper.setSerializationInclusion( Include.NON_NULL )`

### 美化 JSON
- 例：`mapper.writerWithDefaultPrettyPrinter()`

### 忽略字段返回
- 实体配上 `@JsonIgnore` 注解

### 设置字段名
- 实体配上 `@JsonProperty( "user-name" )` 注解

### 忽略不存在的字段，而不报错
- `new ObjectMapper().configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false )`