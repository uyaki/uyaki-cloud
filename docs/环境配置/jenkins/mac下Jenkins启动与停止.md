# mac下Jenkins启动与停止
启动
```bash
sudo launchctl load /Library/LaunchDaemons/org.jenkins-ci.plist
```
停止
```bash
sudo launchctl unload /Library/LaunchDaemons/org.jenkins-ci.plist
```
