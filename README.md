L5 PDS Project
============

Welcome to our project source files :-P

Steps to follow :

 - Install git
 
  - `apt-get install git` for ![Linux][linux_logo]
  - `brew install git` for ![Windows][windows_logo] ![OS X][osx_logo]

 - Setup the GitHub host in your ~/.ssh/config by adding the following lines:

  ```
  Host github
      HostName github.com
      User git
      PreferredAuthentications publickey
      IdentityFile "~/.ssh/id_rsa"
  ```
 
 - Clone this repository :
 
  - `git clone github:tarikouu/pds-project.git`
