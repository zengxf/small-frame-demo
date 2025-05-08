/*
函数默认参数
在C++中，函数的形参列表中的形参是可以有默认值的
语法： 返回值类型 函数名 (参数=默认值) { }

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


// 1.如果某个位置参数有默认值，那么从这个位置往后，从左向右，必须都要有默认值
int func(int a, int b = 1, int c = 10) {
    return a + b;
}

// 2.如果函数声明有默认值，函数实现的时候就不能有默认参数
int func2(int a = 10, int b = 20);

// int func2(int a, int b = 21) { // err: Redefinition of default argument for parameter 'b'
int func2(int a, int b) {
    return a + b;
}

int main() {
    cout << "ret = " << func(20, 20) << endl; // 20 + 20
    cout << "ret = " << func(20) << endl; // 20 + 1 (def)
    cout << "ret = " << func2(100) << endl; // 100 + 20 (def)

    return 0;
}
