基本概念说明

```
inChannel.read( buf )	// 从通道读取数据到缓存	-> 缓存有数据可以读
inChannel.write( buf );	// 从缓存写入到通道	-> 缓存可以被保存
```

Gathering Write
从多个缓存写入到一个通道
Scattering Read
从一个通道读取数据到多个缓存
