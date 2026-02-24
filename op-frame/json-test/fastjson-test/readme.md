# FastJson 测试

## API 说明
### 返回 null 值
- 如：`JSON.toJSONString( info, SerializerFeature.WriteMapNullValue );`

### 设置字段名
- 实体配上 `@JSONField( name = "user-name" )` 注解
