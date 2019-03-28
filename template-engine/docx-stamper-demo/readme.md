## 注
- 循环时，要先选中，再创建备注

### 换行要显示设置
```
DocxStamperConfiguration config = new DocxStamperConfiguration();
config.setLineBreakPlaceholder( "\n" );
DocxStamper<RecommendDto> stamper = new DocxStamper<>( config );
```