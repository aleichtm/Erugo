PROJECT TITLE: Erugo
PURPOSE OF PROJECT: Fulfill final project requirements for Programming II
while building a crude roguelike with small references to Rogue and Moria
VERSION or DATE: 2019-12-19
HOW TO START THIS PROJECT: Run Main.main({});
AUTHORS: Ari
USER INSTRUCTIONS:
1. Open youtube on your default web browser
2. Watch a bunch of youtube videos while you forget about this
3. Leave youtube open and download BlueJ from https://bluej.org/ 
4. Install BlueJ
4. Run package.bluej
5. Click Compile
6. Right click on Main and click void main(String[] args)
7. Read all the text in the game and enjoy!


Info about project:
Main class is main game logic, Map class is main UI logic.
Character is superclass for all characters of which there are Players and NonPlayers.
Main creates the Player and NonPlayers and calls their move functions which then call the Map's move function
This is so that Character does not have to interface with the complicated way that Map is set up
CharactersDo is just to ensure that every Character/subclass of Character has a method to move, attack, and die