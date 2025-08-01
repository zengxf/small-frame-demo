import logging

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    # filename='%(asctime)s.txt'
    filename='test01.txt'
)
logger = logging.getLogger(__name__)

desc = f'test 111'
logger.info(desc)
logger.error(desc)
