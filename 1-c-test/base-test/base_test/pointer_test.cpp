//
// 指针测试
// Created by ZXF on 2025/4/21.
//


#include <iostream>


/**
指针的声明方式为：
    类型 *指针名;
    其中，类型 是指针所指向的变量的类型，* 表示这是一个指针变量
示例代码：
    int     *ptr;   // ptr 是一个指向 int 类型的指针
    double  *dptr;  // dptr 是一个指向 double 类型的指针
    char    *cptr;  // cptr 是一个指向 char 类型的指针
*/
void _info() {
}

// 指针的初始化
int base_test() {
    /*
    代码解释：
        int *ptr = &var; >>> 将 var 的地址赋值给指针 ptr。
        *ptr >>> 解引用指针，获取 ptr 所指向的变量的值。
    */
    int var = 10;
    int *ptr = &var; // ptr 指向 var 的地址

    std::cout << "Value of var: " << var << std::endl;
    std::cout << "Address of var: " << &var << std::endl;
    std::cout << "Value of ptr: " << ptr << std::endl;
    std::cout << "Value pointed by ptr: " << *ptr << std::endl;

    return 0;
}

/**
解引用（Dereferencing）<br/>
    通过解引用运算符 *，可以访问指针所指向的变量的值。
*/
void Dereferencing_test() {
    int var = 20;
    int *ptr = &var;

    std::cout << "Value of var: " << var << std::endl;
    std::cout << "Value pointed by ptr: " << *ptr << std::endl;

    // 修改 var 的值
    *ptr = 30;
    std::cout << "After modification, value of var: " << var << std::endl;
}

// 指针运算
void pointer_operation() {
    int arr[] = {10, 20, 30, 40, 50};
    int *ptr = arr; // 指向数组的第一个元素

    std::cout << "arr: " << &arr << std::endl;
    std::cout << "arr-ptr: " << ptr << std::endl; // 指针位置

    std::cout << "First element: " << *ptr << std::endl;
    ptr++; // 指针移动到下一个元素
    std::cout << "Second element: " << *ptr << std::endl;

    std::cout << "arr-ptr++: " << ptr << std::endl; // 下一个指针位置 (自动 +4)
}

// 指针与数组
// 在 C++ 中，数组名本身就是指向数组第一个元素的指针。因此，可以通过指针来访问数组元素。
void pointer_array() {
    int arr[] = {5, 10, 15, 20, 25};
    int *ptr = arr; // 等同于 int *ptr = &arr[0];

    for (int i = 0; i < 5; i++) {
        std::cout << "Element " << i << ": " << *(ptr + i) << std::endl;
    }
}

// ----------------------------------------
// 指针与函数

// 函数声明：交换两个整数的值
void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

void swap_test() {
    int x = 5;
    int y = 10;
    std::cout << "Before swap: x = " << x << ", y = " << y << std::endl;
    swap(&x, &y); // 传递变量的地址
    std::cout << "After swap: x = " << x << ", y = " << y << std::endl;
}

// ----------------------------------------

int main() {
    base_test();
    std::cout << std::endl;

    Dereferencing_test();
    std::cout << std::endl;

    pointer_operation();
    std::cout << std::endl;

    pointer_array();
    std::cout << std::endl;

    swap_test();
    std::cout << std::endl;

    return 0;
}
