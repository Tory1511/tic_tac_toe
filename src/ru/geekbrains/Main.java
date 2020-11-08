package ru.geekbrains;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static char[][] field;
    public static int size = 3;
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
            while (!isCellValid(x,y));
            field[y][x] = dot_X;
    }

    public static void computerTurn()
    {
        int x,y;
        do
        {
           x = random.nextInt(size);
           y = random.nextInt(size);
        }
        while (!isCellValid(x,y));
        System.out.println("Computer's choosing " + (x +1) + " " + (y +1));
        field[y][x] = dot_O;
    }

    public static boolean isCellValid(int x, int y)
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
        System.out.println("Cell is already used");
        return false;
    }


    public static boolean checkWin(char dot)
    {
        if(checkVerticalAndHorizotal(dot)) return true;
        if(checkDiagonal(dot)) return true;

        return false;
    }

    private static boolean checkDiagonal(char dot)
    {
        boolean toRight = true, toLeft = true;
        for(int i = 0; i < size; i++)
        {
            toRight = toRight & (field[i][i] == dot);
            toLeft = toLeft & (field[size - i - 1][i] == dot);
        }

        return toLeft || toRight;
    }

    private static boolean checkVerticalAndHorizotal(char dot)
    {
        boolean toX = true, toY = true;
        for (int i = 0; i < size; i++)
        {
            toX = true;
            toY = true;
            for (int j = 0; j < size; j++)
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



}


//1. Полностью разобраться с кодом, попробовать переписать с нуля, стараясь не подглядывать в методичку;
//2. Переделать проверку победы, чтобы она не была реализована просто набором условий, например, с использованием циклов.
//3. * Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и победного ряда 4 (по вертикали/горизонтали/диагонали). Очень желательно не делать это просто набором условий для каждой из возможных ситуаций;
//4. *** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока.