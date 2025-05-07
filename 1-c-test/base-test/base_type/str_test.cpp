//
// 字符串测试
// Created by ZXF on 2025/4/21.
//


#include <iostream>
#include <string>


// 定义在 main() 后面，需要在其前面声明下
int test_str();

int main() {
    /**
     * ---------------------------------
     * 使用 C 风格的字符串
     * ---------------------------------
     */

    // 定义一个字符数组，并初始化
    char str1[] = "Hello, World!";
    // 定义一个字符指针，指向一个字符串常量
    const char *str2 = "Hello, C-style string!";

    std::cout << "str1: " << str1 << std::endl;
    std::cout << "str2: " << str2 << std::endl;

    // 修改字符数组中的字符
    str1[7] = 'C';
    std::cout << "Modified str1: " << str1 << std::endl;

    std::cout << "------------------------" << std::endl;

    test_str();

    return 0;
}


int test_str() {
    // 定义一个 std::string 对象，并初始化
    // std::string str1 = "Hello, std::string!";
    std::string str1 = "Hello, std::string! 中文.";
    // 定义一个空的 std::string 对象
    std::string str2;

    // 赋值
    str2 = "Hello, C++ string!";
    std::cout << "str1: " << str1 << std::endl;
    std::cout << "str2: " << str2 << std::endl;

    // 字符串拼接
    str1 += " Welcome to C++ programming.";
    std::cout << "Concatenated str1: " << str1 << std::endl;

    // 字符串长度
    std::cout << "Length of str2: " << str2.length() << std::endl;

    // 访问字符
    std::cout << "First character of str1: " << str1[0] << std::endl;

    // 子字符串
    std::string substr = str1.substr(7, 9);
    std::cout << "Substring of str1: " << substr << std::endl;

    // 使用原始字符串字面量
    std::string str = R"(Line one
Line two with a "quote" and a \backslash\
End of string.)";

    std::cout << str << std::endl;

    return 0;
}
