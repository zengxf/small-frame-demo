## 参考

```bash
Hazelcast 5.6.0
Usage: hz-cli [-hvV] [--ignore-version-mismatch] [-f=<config>] [-t=
              [<cluster-name>@]<hostname>:<port>[,<hostname>:<port>]] [COMMAND]
Utility to perform operations on a Hazelcast cluster.
By default it uses the file config/hazelcast-client.xml to configure the client
connection.

Global options are:

      --ignore-version-mismatch
                          Ignore check between client and server versions (by
                            default they must have matching minor version)
  -t, --targets=[<cluster-name>@]<hostname>:<port>[,<hostname>:<port>]
                          The cluster name and addresses to use if you want to
                            connect to a cluster other than the one configured
                            in the configuration file. At least one address is
                            required. The cluster name is optional.
  -h, --help              Show this help message and exit.
  -V, --version           Print version information and exit.
  -f, --config=<config>   Optional path to a client config XML/YAML file. The
                            default is to use config/hazelcast-client.xml.
  -v, --verbosity         Show verbose logs and full stack trace of errors
Commands:
  help             Display help information about the specified command.
  cancel           Cancels a running job
  cluster          Shows current cluster state and information about members
  console          Starts the console application for trying out in-memory data
                     structures of Hazelcast. It is not recommended for use in
                     production.
  delete-snapshot  Deletes a named snapshot
  list-jobs        Lists running jobs on the cluster
  list-snapshots   Lists exported snapshots on the cluster
  restart          Restarts a running job
  resume           Resumes a suspended job
  save-snapshot    Exports a named snapshot from a job and optionally cancels it
  sql              Starts the SQL shell [BETA]
  submit           Submits a job to the cluster
  suspend          Suspends a running job
```

---

## 测试

```bash
# 查看帮助
hz-cli -h

# 查看版本
hz-cli -V

# -----------------------------------
# -----------------------------------

# 查看集群成员和状态
hz-cli -t=zxf_dev@127.0.0.1:5701 cluster

# 列出集群上正在运行的 Job
hz-cli -t=zxf_dev@127.0.0.1:5701 list-jobs
```

### SQL - 测试

```bash
# 启动 SQL shell
hz-cli -t=zxf_dev@127.0.0.1:5701 sql

# -----------------------------------
# -----------------------------------

sql> select * from a;
Object 'a' not found, did you forget to CREATE MAPPING?
```

### 控制台 - 测试

```bash
# 进入控制台
hz-cli -t=zxf_dev@127.0.0.1:5701 console

# -----------------------------------
# -----------------------------------

hazelcast[default] > m.put a2 bbb222
null
hazelcast[default] > m.keys
aaa
Total 1
hazelcast[default] > m.get aaa
bbb111
```