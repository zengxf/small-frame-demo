# 指定 CMake 的最低版本
cmake_minimum_required(VERSION 3.31)

# 定义项目名称为 base-test
project(base-test)

# 设置 C++ 标准为 C++20。
set(CMAKE_CXX_STANDARD 20)

# 将 main.cpp 编译为可执行文件 base-test。
add_executable(base-test main.cpp)
