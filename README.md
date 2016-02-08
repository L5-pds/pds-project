L5 PDS Project
============

Welcome to our project source files :-P

Steps to follow :

 1. If you're a new GitHuber and your ssh key isn't set yet
  - [Generate your ssh key](https://help.github.com/articles/generating-a-new-ssh-key/)

 2. Install git :
 
  - `apt-get install git` for linux
  - `brew install git` for OS X

 3. Setup the GitHub host in your ~/.ssh/config by adding the following lines:

  ```
  Host github
      HostName github.com
      User git
      PreferredAuthentications publickey
      IdentityFile "~/.ssh/id_rsa"
  ```
  *P.s. make sure that you have the right IdentityFile path (change it if it's not "~/.ssh/id_rsa").*
 
 4. Clone this repository :
 
  - `git clone github:tarikouu/pds-project.git`
 
 5. Config your git :
 
  - `git config user.name "your_name_please"`
  - `git config user.email "your_email_please"`
 
 6. Good job ! You can now start coding (y) Have lots of fun !
  - *P.s. [Please check the github workflow guide](https://guides.github.com/introduction/flow/) (it takes 5 minutes)*
