# M-Axe-L-inuxRemote
Restful service for a remote control of a Debian based linux machine.
Access the remote machine via ssh so no additional software has to be installed.

## Rest interface

### Account
Provides methods to connect and disconnect to a ssh session

##### Login
/account/login

initiates the ssh session

body {
    host: '<host>',
    user: '<username>',
    password: '<password'
}

#### Logout
/account/logout

closes the logout

### FileManager

#### List directory
/filemanager/ls/{directory}

directory: absolute encoded path to the directory to be listed

### Create directory
/filemanager/mkdir/{directory}

directory: absolute encoded path to the directory to be created

### Rename directory
/filemanager/rename/{oldName}/{newName}

oldName: absolute encoded path to the current directory/file
newName: absolute encoded path to the new directory/file

### Remove directory

