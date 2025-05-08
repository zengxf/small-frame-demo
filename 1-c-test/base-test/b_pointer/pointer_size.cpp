/*
指针所占内存空间
 Created by ZXF on 2025/5/8
 */

#include<iostream>
using namespace std;

int main() {
    int a = 10;
    int *p;
    p = &a;

    cout << *p << endl;
    cout << sizeof(p) << endl;
    cout << sizeof(char *) << endl;
    cout << sizeof(float *) << endl;
    cout << sizeof(double *) << endl;

    return 0;
}
