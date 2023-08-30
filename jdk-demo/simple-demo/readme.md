## IDEA 报 GBK 编码错误
- 参考： https://cloud.tencent.com/developer/article/1772652
- IDEA 菜单栏找到：help->Edit Custom VM Options，在打开文件中追加：
  - `-Dfile.encoding=UTF-8`
- 然后重启idea，设置才能生效
- 上面不生效，还得改
  - `IDEA-2022.1.exe.vmoptions` 