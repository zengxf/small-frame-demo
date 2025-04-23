//
// 函数测试
//

#include <iostream>
#include <vector>
#include <algorithm>
#include <string>

int test_int() {
    std::cout << "test_int()!" << std::endl << std::endl;
    return 0;
}

int test_int2() {
    std::cout << "test_int2()!" << std::endl << std::endl;
    // return 10; // 不返回，编译不会报错，但运行时会报错
    return 10;
}

// 值传递（Pass by Value）
void Pass_by_Value_increment(int a) {
    a++;
    std::cout << "Inside function: " << a << std::endl;
}

// 引用传递（Pass by Reference）
void Pass_by_Reference_increment(int &a) {
    a++;
    std::cout << "Inside function: " << a << std::endl;
}

// 指针传递（Pass by Pointer）
void Pass_by_Pointer_increment(int *a) {
    (*a)++;
    std::cout << "Inside function: " << *a << std::endl;
}

// 内联函数：计算平方
inline int square(int x) {
    return x * x;
}

// 函数定义：带有默认参数
void greet(std::string name, std::string message = "Hello") {
    std::cout << message << ", " << name << "!" << std::endl << std::endl;
}

// Lambda 函数（匿名函数）
void test_Lambda() {
    std::vector<int> numbers = {1, 2, 3, 4, 5};
    int sum = 0;

    // 使用 Lambda 函数计算总和
    /*
     * 代码解释：
     *   [&] 捕获外部变量 sum 的引用。
     *   (int x)：参数列表，接收一个整数 x。
    */
    std::for_each(numbers.begin(), numbers.end(), [&](int x) {
        sum += x; // 函数体，将 x 加到 sum 上。
    });

    std::cout << "Sum: " << sum << std::endl << std::endl;
}

int main() {
    test_int();
    test_int2();

    int a = 5;
    Pass_by_Value_increment(a);
    std::cout << "Outside function a: " << a << std::endl << std::endl;

    int b = 5;
    Pass_by_Reference_increment(b);
    std::cout << "Outside function b: " << b << std::endl << std::endl;

    int c = 5;
    Pass_by_Pointer_increment(&c);
    std::cout << "Outside function c: " << c << std::endl << std::endl;

    int num = 5;
    std::cout << "Square of " << num << " is " << square(num) << std::endl << std::endl;

    greet("Alice"); // 使用默认参数
    greet("Bob", "Hi"); // 使用自定义参数

    test_Lambda();

    return 0;
}
