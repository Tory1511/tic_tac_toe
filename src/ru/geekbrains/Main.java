package ru.geekbrains;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static char[][] field;
    public static int size = 5;
    public static int block = 4;
    public static char  toWin;
    public static char dot_empty = '.';
    public static char dot_X = 'x';
    public static char dot_O = 'o';
    public static int x,y;
    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();



    public static void main(String[] args) {
        makeField();
        printField();
       while (true)
       {
           playerTurn();
           printField();
           if (checkWin(dot_X))
           {
               System.out.println("A human has won");
               break;
           }
           computerTurn();
           printField();
           if (checkWin(dot_O))
           {
               System.out.println("Computer has won");
               break;
           }
           if (isMapFull())
           {
               System.out.println("No winners today");
               break;
           }

       }
        System.out.println("Game over");

    }

    public static void makeField()
    {

        field = new char [size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                field[i][j] = dot_empty;
            }
        }
    }

    public static void printField()
    {
        // print x coordinates
        for(int i = 0; i <= size; i++)
        {
            System.out.print(i + " ");
        }
        System.out.println();


        for(int i = 0; i < size; i++)
        {
            //print y coordinates
            System.out.print((i +1) + " ");

            // print field
            for (int j = 0; j < size; j++)
            {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void playerTurn()
    {
        int x,y;
        do
        {
           System.out.println("Enter coordinates in X-Y format");
           x = scanner.nextInt() - 1;
           y = scanner.nextInt() - 1;
        }
            while (!isCellValid(x,y,true));
            field[y][x] = dot_X;
    }

    public static void computerTurn()
    {
        int x = 0,y = 0;
        int[] proposedXY = new int[2];
        //iteration through the given blocks
        for(int i = 0; i <= size - block; i++)
        {
            for(int j = 0; j <= size - block; j++)
            {
                proposedXY = whereToTurn(i, j);

                if (proposedXY != null)
                {
                    y = proposedXY[0];
                    x = proposedXY[1];
                    System.out.println(x + " " + y);
                    recordComputerTurn(x, y);
                    return;
                }
            }
        }

        if (proposedXY == null && x == 0 && y == 0)
        {
            do
            {

                x = random.nextInt(size);
                y = random.nextInt(size);
            }
            while (!isCellValid(x,y,false));
        }

        recordComputerTurn(x, y);
    }

    public static void recordComputerTurn(int x, int y) {
        System.out.println("Computer's choosing " + (x +1) + " " + (y +1));
        field[y][x] = dot_O;
    }

    public static boolean isCellValid(int x, int y, boolean isUserTurn)
    {

        if(x < 0 || x >= size || y < 0 || y >= size)
        {
            System.out.println("Coordinates out of range");
            return false;
        }
        if (field[y][x] == dot_empty)
        {
            return true;
        }
        if (isUserTurn) System.out.println("Cell is already used");
        return false;
    }


    public static boolean checkWin(char dot)
    {
        //iteration through all the possible squares within the given size array of given block size
        for(int i = 0; i <= size - block; i++)
        {
            for(int j = 0; j <= size - block; j++)
            {
                if(checkVerticalAndHorizontal(dot,i,j)) return true;
                if(checkDiagonal(dot,i,j)) return true;
            }
        }

        return false;
    }

    private static boolean checkDiagonal(char dot,int offSetX, int offSetY)
    {
        boolean toRight = true, toLeft = true;
        for(int i = 0; i < block; i++)
        {
            toRight = toRight & (field[i + offSetX][i + offSetY] == dot);
            toLeft = toLeft & (field[block + offSetX - i - 1][i + offSetY] == dot);
        }

        return toLeft || toRight;
    }

    private static boolean checkVerticalAndHorizontal(char dot, int offSetX, int offSetY)
    {
        boolean toX,toY;
        for (int i = offSetX; i < block + offSetX; i++)
        {
            toX = true;
            toY = true;
            for (int j = offSetY; j < block + offSetY; j++)
            {
                toX = toX & (field[i][j] == dot);
                toY = toY & (field[j][i] == dot);
            }

            if (toX || toY) return true;
        }
        return false;
    }


    public static boolean isMapFull()
    {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == dot_empty) return false;
            }
        }
        return true;
    }


    public static int[] whereToTurn(int offSetX, int offSetY)
    {
        int counter_X;
        int counter_Y;
        int[] cord_X;
        int[] cord_Y;
        boolean cord_X_IsAssigned;
        boolean cord_Y_IsAssigned;
        for (int i = offSetX; i < block + offSetX; i++)
        {
            counter_X = 0;
            counter_Y = 0;

            cord_X = new int[2];
            cord_Y = new int[2];

            cord_X_IsAssigned = false;
            cord_Y_IsAssigned = false;

            for (int j = offSetY; j < block + offSetY; j++)
            {
                //Store the first available empty cell
                if (field[i][j] == dot_empty)
                {
                    cord_X[0] = i;
                    cord_X[1] = j;
                    cord_X_IsAssigned = true;
                }

                //increase counter if a call marked with X
                if (field[i][j] == dot_X) counter_X ++;


                if (field[j][i] == dot_empty)
                {
                    cord_Y[0] = j;
                    cord_Y[1] = i;
                    cord_Y_IsAssigned = true;
                }
                if (field[j][i] == dot_X) counter_Y ++;
            }

            //if the block is to complete in one turn or complete(?), return the last stored empty cell if it has a value
            //if the the last stored empty cell is null that would mean that the O cell is already represented in the column/row
            if (counter_X >= block - 1 && cord_X_IsAssigned)
            {
                return cord_X;
            }
            if (counter_Y >= block - 1 && cord_Y_IsAssigned)
            {
                return cord_Y;
            }
        }

        counter_X = 0;
        counter_Y = 0;

        cord_X = new int[2];
        cord_Y = new int[2];

        cord_X_IsAssigned = false;
        cord_Y_IsAssigned = false;

        for(int i = 0; i < block; i++)
        {
            if (field[i + offSetX][i + offSetY] == dot_empty)
            {
                cord_X[0] = i + offSetX;
                cord_X[1] = i + offSetY;
                cord_X_IsAssigned = true;
            }
            if (field[i + offSetX][i + offSetY] == dot_X) counter_X ++;

            if (field[block + offSetX - i - 1][i + offSetY] == dot_empty)
            {
                cord_Y[0] = block + offSetX - i - 1;
                cord_Y[1] = i + offSetY;
                cord_Y_IsAssigned = true;
            }
            if (field[block + offSetX - i - 1][i + offSetY] == dot_X) counter_Y ++;

        }

        if (counter_X >= block - 1 && cord_X_IsAssigned)
        {
            return cord_X;
        }
        if (counter_Y >= block - 1 && cord_Y_IsAssigned)
        {
            return cord_Y;
        }

        return null;
    }

}


//1. Полностью разобраться с кодом, попробовать переписать с нуля, стараясь не подглядывать в методичку;
//2. Переделать проверку победы, чтобы она не была реализована просто набором условий, например, с использованием циклов.
//3. * Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и победного ряда 4 (по вертикали/горизонтали/диагонали). Очень желательно не делать это просто набором условий для каждой из возможных ситуаций;
//4. *** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока.