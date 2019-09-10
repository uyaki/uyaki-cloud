# dockerpycreds.errors.InitializationError: docker-credential-desktop not installed or not available in PATH

## 原因

docker系统文件单词拼写错误

## 解决

```sh
$ vim ~/.docker/config.json
```

把`credsStore`改成`credSstore`

## 原文链接

[github.com/docker/for-mac/issues/3785](https://github.com/docker/for-mac/issues/3785#issuecomment-518328553)

