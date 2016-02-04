L5 PDS Project
============

Welcome to our project source files :-P

Steps to follow :

 - Install git
 
  - `apt-get install git` for ![Linux](http://www.designosoft.com/icons/hosting/03%20Linux%20Web%20Hosting.png)
  - `brew install git` for ![OS X](http://www.belgium-gsm.com/BGimages/menu/apple.png)

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
