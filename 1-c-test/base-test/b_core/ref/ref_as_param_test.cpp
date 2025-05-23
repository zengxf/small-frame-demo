/*
引用做函数参数
作用： 函数传参时，可以利用引用技术让形参修饰实参
优点： 可以简化指针修改实参

总结：通过引用参数产生的效果和按地址传递是一样的，引用的语法更简洁

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;

// 1.值传递
void mySwap01(int a, int b) {
    int temp = a;
    a = b;
    b = temp;
}

// 2.地址传递
void mySwap02(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

// 3.引用传递
void mySwap03(int &a, int &b) {
    int temp = a;
    a = b;
    b = temp;
}

int main() {
    int a = 20;
    int b = 30;

    mySwap01(a, b);
    cout << "a = " << a << " b = " << b << endl;

    mySwap02(&a, &b);
    cout << "a = " << a << " b = " << b << endl;

    mySwap03(a, b);
    cout << "a = " << a << " b = " << b << endl;

    return 0;
}
