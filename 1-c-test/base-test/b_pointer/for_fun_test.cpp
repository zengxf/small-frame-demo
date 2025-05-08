/*
指针和函数
作用： 利用指针做函数参数，可以修改实参的值

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;

// 值传递
void swap1(int a, int b) {
    int tmp = a;
    a = b;
    b = tmp;
}

// 地址传递
void swap2(int *p1, int *p2) {
    int tmp = *p1;
    *p1 = *p2;
    *p2 = tmp;
}

int main() {
    int a = 10;
    int b = 20;

    cout << "a = " << a << endl;
    cout << "b = " << b << endl << endl;
    swap1(a, b);

    cout << "a = " << a << endl;
    cout << "b = " << b << endl << endl;
    swap2(&a, &b);

    cout << "a = " << a << endl;
    cout << "b = " << b << endl << endl;
    return 0;
}
