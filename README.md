# clojure_research

Facts about Clojure:
1. A LISP dialect.
2. A functional programming language focuses on immutability.
3. Design for Concurrency
4. Symbiotic with an established Platform

Clojure lives on top of different VMs and host languages, with minimalistic syntax. Clojure focuses on solving problems.

## How to start?
We can follow the [official guide](https://clojure.org/guides/install_clojure)

for macOS:
`brew install clojure/tools/clojure`

For Linux:
```sh
curl -L -O https://github.com/clojure/brew-install/releases/latest/download/linux-install.sh
chmod +x linux-install.sh
sudo ./linux-install.sh
```

for Windows:
Use WSL2 and install with Linux commands.

Make sure you have it on the system.

An easier way for macOS, Linux, and Windows WSL2:
Install the SDKMAN JDK version manager.
`curl -s "https://get.sdkman.io" | bash`

or for macOS:
``` sh 
brew install --cask temurin@21

echo 'export PATH="/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home/bin:$PATH"' >> ~/.zshrc
```

To test if you have successfully installed Clojure in the terminal, type: 
`clj`

# Table of Contents

- [clojure\_research](#clojure_research)
	- [How to start?](#how-to-start)
- [Table of Contents](#table-of-contents)
	- [Project 1](#project-1)
	- [Project 2](#project-2)

## Project 1<a name="project-1-"></a>
- WSB:
  - [x] i. Create private remote repo named: CST8002_PracticalProject_030_LoyYeeKo
  - [x] ii. Invite Professor as collaborator
  - [x] iii. Create branch Project 1
  - [x] iv. Tag last commit as V1.0
  - [x] v. Create record object based on CSV Column header
  - [x] vi. Open and read with file-IO, parse CSV data and store as sequence data structure of record objects.
  - [x] vii. Handling exceptions
  - [x] viii. Iterate and print record data
  - [x] ix. Display full name
  - [x] x. Take screenshot of each task with full name
  - [x] xi. Comment on beginning of the file with course, student, and professor name, each header, constants, and methods
	
## Project 2<a name="project-2-"></a>
