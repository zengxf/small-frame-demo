__all__ = ["Main"]  # 声明模块对外暴露的类名


class Main:
    def __init__(self):
        self.sign = None

    def test(self):
        print("test 1")
        self.sign = True
        print("test 2")

    def print(self):
        t_sign = "ok"
        print(f"sign is {self.sign}")
        print(f"t_sign: {t_sign}")


if __name__ == '__main__':
    main = Main()
    main.test()
    main.print()
