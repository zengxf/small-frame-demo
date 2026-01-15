# UV 测试


## 安装
### 方法一：PowerShell 自动安装
- 管理员身份打开 PowerShell
```bash
# cmd 中执行
powershell -c "irm https://astral.sh/uv/install.ps1 | iex"

# 或直接下面的命令
irm https://astral.sh/uv/install.ps1 | iex
```

### 方法二：手动下载安装
- ref: https://github.com/astral-sh/uv/releases
- 进入: https://github.com/astral-sh/uv/releases/tag/0.9.25
- 点击 `uv-x86_64-pc-windows-msvc.zip`
  - 最终: https://github.com/astral-sh/uv/releases/download/0.9.25/uv-x86_64-pc-windows-msvc.zip
- 解压并添加到 path

### 环境变量
- **设置国内镜像源**
- 变量名：`UV_INDEX_URL`
- 变量值：`https://pypi.tuna.tsinghua.edu.cn/simple`
```bash
# 临时设置
# PowerShell
$env:UV_INDEX_URL = "https://pypi.tuna.tsinghua.edu.cn/simple"
# cmd
set UV_INDEX_URL=https://pypi.tuna.tsinghua.edu.cn/simple

# 永久设置
setx UV_INDEX_URL "https://pypi.tuna.tsinghua.edu.cn/simple"
```


## 测试
```bash
# 1. 查看 uv 版本
uv -V
uv --version

# 2. 查看 uv 帮助信息
uv -h
uv --help

# 3. 尝试创建一个虚拟环境（测试核心功能）
uv venv my-env
uv venv --python 3.11 py311 -v  # 指定 py 版本

# 缓存相关
uv cache dir    # 查看缓存目录
uv cache size   # 查看缓存大小
uv cache prune  # 清理所有未使用的缓存条目
uv cache clean  # 彻底清理缓存
```


## 使用
```bash
# 1. 创建虚拟环境（在当前项目目录下）
uv venv

# 2. 安装包（速度远快于 pip）
uv pip install pandas
uv pip install requests -v
# 2.10 指定镜像安装
uv pip install requests --index-url https://pypi.tuna.tsinghua.edu.cn/simple -v
# 2.20 删除依赖
uv pip uninstall requests

# 3. 将当前项目依赖保存到 requirements.txt
uv pip freeze > requirements.txt

# 3.1 查看安装的依赖
uv pip list     # 表格形式
uv pip freeze   # 等号形式 ("=")

# 4. 从 requirements.txt 安装依赖
uv pip install -r requirements.txt

# 5. 运行 Python 脚本（uv 会自动激活虚拟环境）
uv run python test.py
```


## 项目
```bash
# 创建虚拟环境（在当前项目目录下）
# uv venv  # 此步可以不用 (添加依赖时自动创建)

# 初始化项目
# 会创建 pyproject.toml 文件
uv init

# 添加依赖
uv add requests
uv add pandas

# 删除依赖
uv remove requests

# 查看依赖
uv tree
uv tree --depth 2   # 只显示依赖树的前 N 层
uv pip list         # 手动安装但未声明在 pyproject.toml 中的包，这是当前环境的真实快照

# 重新生成锁文件
uv lock

# 根据 pyproject.toml 和 uv.lock 恢复依赖环境 (即恢复 .venv 目录)
uv sync
uv sync --locked    # 没有 uv.lock 文件（第一次恢复）

# 导出
uv export --help    # 查看导出帮助
uv export           # 默认带 hash，带每个注释，带注释头。但不输出到文件
uv export --no-hashes --no-annotate -o requirements.txt
uv export --no-hashes --no-annotate --no-header -o requirements.txt

# 运行
uv run python           # 启动交互式 Python
uv run python main.py   # 运行名为 main.py 的脚本
uv run mycmd            # 运行自定义命令

# 构建
uv build
```


## 项目共享环境
```bash
# 可以集中目录设置 (如 D:/Data/py-env)
cd /d D:/Data/py-env

# 指定版本
uv venv --python 3.11 py311 -v

# PyCharm 多项目共用
#   设置 (File -> Settings/Preferences) -> Python 解释器，
#   然后选择 "Add Interpreter" -> "Existing Environment" (或 "Add Local Interpreter" -> "uv" 类型)，
#   指定你已有的 uv 环境的路径，将其添加为现有环境
```