export GPG_TTY=$(tty)
nohup mvn clean install deploy -T8 -Prelease -Dgpg.passphrase=WWW123sss