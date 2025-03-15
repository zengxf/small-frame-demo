import logging

# 配置日志
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(levelname)s - %(message)s'
)

msg = "test 123"
logging.info(f'(info) log test msg: {msg}')
logging.error(f'(error) log test msg: {msg}')
