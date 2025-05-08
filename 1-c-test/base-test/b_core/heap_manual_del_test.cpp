/*
堆区：
由程序员分配释放，若程序员不释放；程序结束时由操作系统回收；
在C++中主要利用 new 在堆区开辟内存。

总结：
1. 堆区数据由程序员管理开辟和释放
2. 堆区数据利用 new 关键字进行开辟内存

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


int *func() {
    int *a = new int(10);
    return a;
}

int main() {
    int *p = func();

    cout << *p << endl;
    cout << *p << endl;

    return 0;
}
