#!/bin/bash

if ! command -v java &> /dev/null
then
    echo "Java is not installed! Installing Java..."
    
    sudo apt-get update
    
    sudo apt-get install -y openjdk-21-jdk
    
    if command -v java &> /dev/null
    then
        echo "Java successfully installed."
    else
        echo "An error occurred while installing Java."
        echo "Please check your internet connection or the package manager."
        echo "If the problem persists, please install Java manually."
        echo "You need to install Java 21 or higher."
        exit 1
    fi
else
    echo "Java allready installed."
fi

echo "Installing dvd animation for the therminal :)..."
sudo dpkg -i dvd.deb

echo "Installing dependencies..."

sudo apt-get install -f

echo "Installed!"

