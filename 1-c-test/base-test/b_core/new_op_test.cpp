/*
用new操作符在堆区开辟数据；
堆区开辟的数据，由程序员手动开辟，手动释放，释放利用操作符 delete。
利用new创建的数据，会返回该数据对应类型的指针。

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;

// 示例-1：基本语法
int *func() {
    int *a = new int(10);
    return a;
}

// 示例-2：开辟数组
void test_arr() {
    int *arr = new int[10];

    for (int i = 0; i < 10; i++) {
        arr[i] = i + 100;
        cout << arr[i] << endl;
    }

    // 释放数组，delete 后需要加 []
    delete[] arr;
}

int main() {
    int *p = func();
    cout << *p << endl;
    cout << *p << endl;

    // 利用 delete 释放堆区数据
    delete p;
    // cout << *p << endl; // 虽然没报错，但结果值已不对

    test_arr();

    return 0;
}
