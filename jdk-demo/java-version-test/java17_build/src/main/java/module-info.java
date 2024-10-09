module cn.test {
    requires static lombok; // 需要 static

    requires org.slf4j;
    requires cn.hutool.core;

    exports cn.test;
}