//The model that contains the game related material-- doesn't care about how the information is conveyed to the user

import java.io.*;

public class MinesweeperModel implements Serializable{

    //An int array that stores the status of a tile
    //If 0, then the tile has no data (it is not a mine nor is it next to a mine) and is NOT clicked
    // If 1, then the tile is a mine and is NOT clicked
    // If 2, then the tile is next to a mine and is NOT clicked
    // If 3, then the tile has no data and IS clicked
    // If 4, then the tile is next to a mine and IS clicked
    // If 5, then the tile is a mine and IS clicked (Game over)
    // If 6, the user has placed a flag correctly
    // If 7, the user has placed a flag on a tile adjacent to a bomb
    // If 8, the user has placed a flag on a blank tile 
    public final int[][] board;

    //The size of the board
    public final int SIZE;

    //The number of mines on the board
    public final int numMines;

    // The constructor for the model that is given to the frame
    public MinesweeperModel (int s)
    {
        //Fills in the board randomly with mines depending on difficulty. A tile with a mine is given a status of 1.
        int minesFilled = 0;
        SIZE = s;
        if (SIZE == 9)
        {
            numMines = 10;
        } else if (SIZE == 16)
        {
            numMines = 40;
        } else{
            numMines = 90;
        }
        board = new int[SIZE][SIZE];
        while(minesFilled <numMines)
        {
            for (int row = 0; row < board.length; row++)
            {
                for (int col = 0; col < board[0].length; col++){
                    if (Math.random() < 0.1 && board[row][col]!= 1)
                    {
                        board[row][col] = 1;
                        minesFilled++;
                        
                    }
                    if (minesFilled >= numMines){
                        break;
                    }
                }
                if (minesFilled >= numMines){
                    break;
                }
            } 
        }

        //Fills in the tiles next to mines with a status of 2, meaning that the tile is next to a mine and isn't clicked.
        //The rest of the tiles default to zero, meaning they contain no game data.

        //For the "normal" operations that are not corner tiles or side tiles that would cause an out of bounds exception
        for (int row = 1; row<SIZE-1; row++)
        {
            for (int col = 1; col < SIZE-1; col++)
            {
                if (board[row][col] == 1){
                    for (int i = row - 1; i<=  row + 1; i++)
                    {
                        for (int j = col - 1; j <= col + 1; j++)
                        {
                            if (board[i][j] != 1 && !(i==row && j== col))
                            {
                                board[i][j] = 2;
                            }
                        }
                    }
                }
                
            }
        }

        //fills in tiles next to mines for row 0 that would otherwise cause an exception
        for (int col = 1; col < SIZE-1; col ++)
        {
            if (board[0][col] == 1)
            {
                for (int i = 0; i<=1; i++)
                {
                    for (int j = col - 1; j<= col + 1;  j++)
                    {
                        if (board[i][j] != 1 && !(i==0 && j== col))
                            {
                                board[i][j] = 2;
                            }
                    }
                }
            }
        }

        //fills in tiles next to mines for row SIZE-1 that would otherwise cause an exception
        for (int col = 1; col < SIZE-1; col ++)
        {
            if (board[SIZE-1][col] == 1)
            {
                for (int i = SIZE-2; i<=SIZE-1; i++)
                {
                    for (int j = col - 1; j<= col + 1;  j++)
                    {
                        if (board[i][j] != 1 && !(i==SIZE-1 && j== col))
                            {
                                board[i][j] = 2;
                            }
                    }
                }
            }
        }

        //fills in tiles next to mines for col 0 that would otherwise cause an exception
        for (int row = 1; row < SIZE-1; row ++)
        {
            if (board[row][0] == 1)
            {
                for (int i = row - 1; i<= row + 1; i++)
                {
                    for (int j = 0; j<= 1; j ++)
                    {
                        if (board[i][j] != 1 && !(i==row && j== 0))
                            {
                                board[i][j] = 2;
                            }
                    }
                }
            }
        }

        //fills in tiles next to mines for col SIZE-1 that would otherwise cause an exception
        for (int row = 1; row < SIZE-1; row ++)
        {
            if (board[row][SIZE-1] == 1)
            {
                for (int i = row - 1; i<= row + 1; i++)
                {
                    for (int j = SIZE-2; j<= SIZE-1; j ++)
                    {
                        if (board[i][j] != 1 && !(i==row && j== 0))
                            {
                                board[i][j] = 2;
                            }
                    }
                }
            }
        }

        //The corner test cases that would cause an exception with both base cases and side cases

        //fills in the top left corner that would otherwise cause an exception
        if (board[0][0] == 1)
        {
            if (board[0][1] != 1)
            {
                board[0][1] = 2;
            }
            if (board[1][1] != 1)
            {
                board[1][1] = 2;
            }
            if (board[1][0] != 1)
            {
                board[1][0] = 2;
            }
        }

        //fills in the top right corner that would otherwise cause an exception
        if (board[0][SIZE-1] == 1)
        {
            if (board[0][SIZE-2] != 1)
            {
                board[0][SIZE-2] = 2;
            }
            if (board[1][SIZE-2] != 1)
            {
                board[1][SIZE-2] = 2;
            }
            if (board[1][SIZE-1] != 1)
            {
                board[1][SIZE-1] = 2;
            }
        }

        //fills in the bottom right corner that would otherwise cause an exception
        if (board[SIZE-1][SIZE-1] == 1)
        {
            if (board[SIZE-2][SIZE-1] != 1)
            {
                board[SIZE-2][SIZE-1] = 2;
            }
            if (board[SIZE-2][SIZE-2] != 1)
            {
                board[SIZE-2][SIZE-2] = 2;
            }
            if (board[SIZE-1][SIZE-2] != 1)
            {
                board[SIZE-1][SIZE-2] = 2;
            }
        }

        //fills in the bottom left corner that would otherwise cause an exception
        if (board[SIZE-1][0] == 1)
        {
            if (board[SIZE-2][0] != 1)
            {
                board[SIZE-2][0] = 2;
            }
            if (board[SIZE-2][1] != 1)
            {
                board[SIZE-2][1] = 2;
            }
            if (board[SIZE-1][1] != 1)
            {
                board[SIZE-1][1] = 2;
            }
        }

    }

    // A method that the frame can ask the model for in order to get the status of a specific tile
    //Gets the status of the tile at [r][c]
    public int returnStatus (int r, int c)
    {
        return board[r][c];
    }

    //A method that can be called for a specific tile that will update the tile based on the user input
    //Updates the tile at [x][y] and takes in a string depending on if the left mouse button or
    //right mouse button was clicked
    public void updateSquare(int x, int y, String s)
    {
        if (s.equals("l")){
            if (board[x][y] == 1)
            {
                board[x][y] = 5;
            } else if (board[x][y] == 2) {
                board[x][y] = 4;
            } else if (board[x][y] == 0)
            {
                zeroFound(x, y);
            }
        } else if (s.equals("r"))
        {
            if (board[x][y] == 1)
            {
                board[x][y] = 6;
            } else if (board[x][y] == 2)
            {
                board[x][y] = 7;
            } else if (board[x][y] == 0)
            {
                board[x][y] = 8;
            }
            else if (board[x][y] == 6)
            {
                board[x][y] = 1;
            } else if (board[x][y] == 7)
            {
                board[x][y] = 2;
            } else if (board[x][y] == 8)
            {
                board[x][y] = 0;
            }
        }
        
        
        
    }

    //A recursive method that, if the tile has no data, will clear the tiles around it because they are safe
    //takes in the [x][y] position of the tile and clears the surrounding tiles
    //If another zero is found in those tiles, the method will be called again
    public void zeroFound (int x, int y)
    {
        board[x][y] = 3;
        //The "normal" operations that would not cause an out of bounds error
        if ((x>0 && y>0) && (x<SIZE-1 && y<SIZE-1))
        {
            for (int i = x-1; i<=x+1; i++)
            {
                for (int j = y-1; j<=y+1;j++)
                {
                    if (!(i == x && j==y))
                    {
                        if(board[i][j] == 2)
                        {
                            board[i][j] = 4;
                        } else if (board[i][j] == 0)
                        {
                            zeroFound(i, j);
                        }
                    }
                }
            }
        } else if (x == 0 && y == 0) //The top left corner
        {
            if (board[0][1] == 2)
            {
                board[0][1] = 4;
            } else if (board[0][1] == 0)
            {
                zeroFound(0, 1);
            }
            if (board[1][1] == 2)
            {
                board[1][1] = 4;
            } else if (board[1][1] == 0)
            {
                zeroFound(1, 1);
            }
            if (board[1][0] == 2)
            {
                board[1][0] = 4;
            } else if (board[1][0] == 0)
            {
                zeroFound(1, 0);
            }
        } else if (x == 0 && y == SIZE-1) //The top right corner
        {
            if (board[0][SIZE-2] ==2 )
            {
                board[0][SIZE-2] = 4;
            } else if (board[0][SIZE-2] == 0)
            {
                zeroFound(0, SIZE-2);
            }
            if (board[1][SIZE-2] == 2)
            {
                board[1][SIZE-2] = 4;
            } else if (board[1][SIZE-2] == 0)
            {
                zeroFound(1, SIZE-2);
            }
            if (board[1][SIZE-1] == 2)
            {
                board[1][SIZE-1] = 4;
            } else if (board[1][SIZE-1] == 0)
            {
                zeroFound(1, SIZE-1);
            }
        } else if ( x == SIZE-1 && y == 0) //The bottom left corner
        {
            if (board[SIZE-2][0] == 2)
            {
                board[SIZE-2][0] = 4;
            } else if (board[SIZE-2][0] == 0)
            {
                zeroFound(SIZE-2, 0);
            }
            if (board[SIZE-2][1] == 2)
            {
                board[SIZE-2][1] = 4;
            } else if (board[SIZE-2][1] == 0)
            {
                zeroFound(SIZE-2, 1);
            }
            if (board[SIZE-1][1] == 2)
            {
                board[SIZE-1][1] = 4;
            } else if (board[SIZE-1][1] == 0)
            {
                zeroFound(SIZE-1, 1);
            }
            
        } else if ( x == SIZE-1 && y == SIZE-1) //The bottom right corner
        {
            if (board[SIZE-2][SIZE-1] == 2)
            {
                board[SIZE-2][SIZE-1] = 4;
            } else if (board[SIZE-2][SIZE-1] == 0)
            {
                zeroFound(SIZE-2, SIZE-1);
            }
            if (board[SIZE-2][SIZE-2] == 2)
            {
                board[SIZE-2][SIZE-2] = 4;
            } else if (board[SIZE-2][SIZE-2] == 0)
            {
                zeroFound(SIZE-2, SIZE-2);
            }
            if (board[SIZE-1][SIZE-2] == 2)
            {
                board[SIZE-1][SIZE-2] = 4;
            } else if (board[SIZE-1][SIZE-2] == 0)
            {
                zeroFound(SIZE-1, SIZE-2);
            }
        } else if (x==0) //The top row
        {
            for (int row = 0; row <=1; row++)
            {
                for (int col = y-1; col <= y+1; col++)
                {
                    if (board[row][col] == 2)
                    {
                        board[row][col] = 4;
                    } else if (board[row][col] == 0)
                    {
                        zeroFound(row, col);
                    }
                }
            }
        } else if (x==SIZE-1) //The bottom row
        {
            for (int row = SIZE-2; row <=SIZE-1; row++)
            {
                for (int col = y-1; col <= y+1; col++)
                {
                    if (board[row][col] == 2)
                    {
                        board[row][col] = 4;
                    } else if (board[row][col] == 0)
                    {
                        zeroFound(row, col);
                    }
                }
            }
        } else if (y==0) // The left column
        {
            for (int row = x-1; row <=x+1; row++)
            {
                for (int col = 0; col <= 1; col++)
                {
                    if (board[row][col] == 2)
                    {
                        board[row][col] = 4;
                    } else if (board[row][col] == 0)
                    {
                        zeroFound(row, col);
                    }
                }
            }
        } else if (y==SIZE-1) // The right column
        {
            for (int row = x-1; row <=x+1; row++)
            {
                for (int col = SIZE-2; col <= SIZE-1; col++)
                {
                    if (board[row][col] == 2)
                    {
                        board[row][col] = 4;
                    } else if (board[row][col] == 0)
                    {
                        zeroFound(row, col);
                        
                    }
                }
            }
        }

    }

}
