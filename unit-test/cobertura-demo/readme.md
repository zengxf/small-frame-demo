测试覆盖率：
	cobertura 不支持 lambda 语法
	mvn cobertura:help			# 查看帮助
	mvn cobertura:check			# 校验测试用例的覆盖率，如果没有达到要求，则执行失败。绑定 test
	mvn cobertura:check-integration-test			# 校验集成测试。绑定 verify
	mvn cobertura:cobertura		# 生成报告
	mvn cobertura:cobertura-integration-test		# 集成测试-生成报告
	mvn cobertura:clean			# 清理文件
	mvn cobertura:dump-datafile	# 命令行输出
	mvn cobertura:instrument	# 生成一套 class 文件

参考：
	http://www.cnblogs.com/qyf404/p/5040593.html