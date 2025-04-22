//
// 基础类型测试
// Created by ZXF on 2025/4/22.
//


#include <iostream>
#include <string>


int main() {
    std::cout << "Hello, World!" << std::endl;

    // 布尔型

    bool flag = true;
    bool isValid = false;

    std::cout << "flag: " << flag << std::endl;
    std::cout << "isValid: " << isValid << std::endl;

    // 浮点型

    float f = 3.14f;
    double d = 2.718281828459045;
    long double ld = 1.7976931348623157e+308L;

    std::cout << "float: " << f << std::endl;
    std::cout << "double: " << d << std::endl;
    std::cout << "long double: " << ld << std::endl;

    // 有符号整型
    short s = 32767; // 短整型，通常 16 位
    int i = 2147483647; // 整型，通常 32 位
    long l = 2147483647L; // 长整型，至少 32 位
    long long ll = 9223372036854775807LL; // 长长整型，至少 64 位

    std::cout << "short: " << s << std::endl;
    std::cout << "int: " << i << std::endl;
    std::cout << "long : " << l << std::endl;
    std::cout << "long long : " << ll << std::endl;

    // 无符号整型
    unsigned short us = 65535;
    unsigned int ui = 4294967295U;
    unsigned long ul = 4294967295UL;
    unsigned long long ull = 18446744073709551615ULL;

    std::cout << "unsigned short: " << us << std::endl;
    std::cout << "unsigned int: " << ui << std::endl;
    std::cout << "unsigned long : " << ul << std::endl;
    std::cout << "unsigned long long : " << ull << std::endl;

    return 0;
}
