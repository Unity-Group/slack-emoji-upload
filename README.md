How to build
============

You need Java Development Kit installed and JAVA_HOME
set correctly (or you need to have JDK executables on
you PATH).

To create unpacked executable in `./build/install/slack-emoji-upload/bin`:

```
./gradlew instDist
```

To create zip with app in `./build/distributions`:

```
./gradlew distZip
```

How to use
===========

Example usage:

```bash
build/install/slack-emoji-upload/bin/slack-emoji-upload \
    -d ~/Downloads/chromedriver -e ~/emojis/group/ \
    -s https://myworkspace.slack.com \
    -u myslack@login.com -p password
```

(it's good idea not to add your password directly in
command prompt, you can for example laod it from file:
`slack-emoji-upload ... -p $(cat /tmp/password)`)

You will need Google Chrome installed and selenium
driver file for chrome. Get driver here: 
http://chromedriver.chromium.org/downloads

Since slack failed to supply us with API for uploading
emojis, this tool uses selenium to automate uploading
emojis via slack web app ¯\_(ツ)_/¯

(so don't be surprised to see chrome opening and
acting on it's own)

How to import emojis from slack
===============================

You can use https://github.com/Unity-Group/hipchat-download-emoji to download all the hipchat emojis you have gathered

