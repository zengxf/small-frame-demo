# Gson 测试

## API 说明
### 返回 null 值
- 通过 `GsonBuilder` 的 `serializeNulls()` 方法设置

### 只返回特定值
- 实体配上 `@Expose` 注解
- 通过 `GsonBuilder` 的 `excludeFieldsWithoutExposeAnnotation()` 方法设置

### 设置字段名
- 实体配上 `@SerializedName` 注解

### 设置版本
- 实体配上 `@Since` 注解
- 实体配上 `@Until` 注解
- 通过 `GsonBuilder` 的 `setVersion()` 方法设置
