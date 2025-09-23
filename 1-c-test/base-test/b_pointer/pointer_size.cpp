/*
指针所占内存空间
 Created by ZXF on 2025/5/8
 */

#include<iostream>
using namespace std;
#include <string> // 字符串库

int main() {
    int a = 10;
    int *p;
    p = &a;

    cout << *p << endl;
    cout << sizeof(p) << endl;
    cout << sizeof(char *) << endl;
    cout << sizeof(float *) << endl;
    cout << sizeof(double *) << endl;

    // ------------------------------

    std::string name = "wahahahahahawahahahahahawahahahahahawahahahahaha";

    cout << "name size: " << sizeof(name) << endl;
    std::string *ptr_name = &name;
    cout << "ptr_name size: " << sizeof(ptr_name) << endl;
    cout << "name size: " << sizeof(name) << endl;

    return 0;
}
