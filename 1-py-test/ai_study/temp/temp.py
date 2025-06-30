powers = [(lambda x, n=n: x ** n) for n in range(1, 6)]
print([p(10) for p in powers])