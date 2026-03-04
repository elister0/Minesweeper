# Minesweeper

**Author:** Emily Lister  
**Course:** Computer Science II
**Language:** Java  

---
## Overview

This project is a fully functional **Minesweeper** game implemented in **Java using Swing**.  
The game follows a Model–View–Controller style structure and includes:

- Multiple difficulty levels
- Recursive zero-tile clearing
- Right-click flag placement
- Mine counter display
- Win/Loss detection
- Save and Load functionality using serialization

---

## Features

### Game Modes
- **Easy** – 9x9 board (10 mines)
- **Intermediate** – 16x16 board (40 mines)
- **Hard** – 20x20 board (90 mines)

### Controls
- **Left Click** - Reveal tile
- **Right Click** - Place or remove flag

### Save & Load
- Save your current game state to a `.dat` file
- Load a previously saved game
- Uses Java object serialization

### Game Feedback
- Displays remaining mine count
- Shows **"You Win!"** when all safe tiles are cleared
- Shows **"You Lost!"** when a mine is clicked

---

## How to Compile and Run

### 1. Compile

From the project directory:
javac *.java

### 2. Run
java Minesweeper

---

##  Project Structure
- Minesweeper.java               # Main runner class
- MinesweeperFrame.java          # Main application window
- MinesweeperModel.java          # Game logic and board state (Serializable)
- MinesweeperPanel.java          # Grid UI and tile rendering
- MinesweeperPanelBorder.java    # Border layout + mine counter + win/loss display
- MinesweeperFileChooser.java    # Save and load functionality
---
## Design Overview
Design Overview
### Model
MinesweeperModel
- Stores the board as a 2D integer array
- Handles mine placement
- Handles recursive zero clearing
- Updates tile states
- Implements Serializable for saving/loading
### View
MinesweeperPanel and MinesweeperPanelBorder
- Renders the grid
- Displays numbers with color coding
- Displays remaining mines
- Shows win/loss messages
### Controller
- Mouse listeners handle tile interaction
- Menu actions handle difficulty changes, saving, and loading

---
## Tile Status System
Each tile is represented internally by an integer value:
Value	Meaning
- 0	Empty, not clicked
- 1	Mine, not clicked
- 2	Adjacent to mine, not clicked
- 3	Empty, revealed
- 4	Adjacent to mine, revealed
- 5	Mine clicked (Game Over)
- 6	Correctly flagged mine
- 7	Flag on adjacent tile
- 8	Flag on empty tile
---
## Technologies Used
- Java
- Java Swing (GUI)
- Object Serialization
- Event-driven programming
- Recursive algorithms

