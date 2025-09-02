from pathlib import Path

FILE = Path(__file__).resolve()
ROOT = FILE.parents[0]
# ROOT = "c:/dd"
p1 = ROOT / 'dd.txt'
print(p1)
