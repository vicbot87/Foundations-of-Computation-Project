//Written by Victor Botteicher
import static java.lang.System.out;
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.Integer.*;

public class Program1
{
  public static void main(String[] args) throws Exception
  {
    Scanner fileReader = new Scanner(new File(args[0]));
    String line1 = fileReader.nextLine();
    int numStates = findNumOfStates(line1);
    String line2 = fileReader.nextLine();
    int alphabetSize = findAlphabetSize(line2);
    String line3 = fileReader.nextLine();
    String line3Sub = line3.substring(18);
    int[] accStatesArray = findAcceptingStates(line3Sub);

    @SuppressWarnings("unchecked")
    numberList<Integer>[][] transArray = new numberList[numStates][alphabetSize + 1];

    for(int i = 0; i < numStates; i++)
    {
      for(int j = 0; j < alphabetSize + 1; j++)
      {
        transArray[i][j] = new numberList<Integer>();
      }
    }
    int lineCounter = 0;

    //populate arraylists, corresponds to transition table
    while(fileReader.hasNextLine())
    {
        String nextLine = fileReader.nextLine();
        String[] nextLineArray = nextLine.split("\\s+");
        for(int k = 0; k < alphabetSize + 1; k++)
        {
          int lengthOfTransition = nextLineArray[k].length();

          if(lengthOfTransition > 2)
          {
            nextLineArray[k] = nextLineArray[k].substring(1, lengthOfTransition - 1);
            String[] splitByCommaArray = nextLineArray[k].split(",");
            for(int l = 0; l < splitByCommaArray.length; l++)
            {
              int tempInt = Integer.parseInt(splitByCommaArray[l]);
              Integer tempInteger = new Integer(tempInt);
              transArray[lineCounter][k].add(tempInteger);
            }
          }
          else
          {
            nextLineArray[k] = null;
          }
        }
        lineCounter++;
    }

    while(true) //infinite loops to read strings from stdIn
    {
      Scanner scan = new Scanner(System.in);
      String s = scan.nextLine();
      int stringLength = s.length();
      if(stringLength == 0 && accStatesArray[0] == 0)
      {
        System.out.println("accept"); //empty string
      }
      else
      {
      int[] currStatesArray = new int[64];            //64 length array to hold the current possible states
      for(int l = 0; l < currStatesArray.length; l++)
      {
        currStatesArray[l] = -1;
      }
      currStatesArray[0] = 0;           //first state will always be 0
      for(int m = 0; m < stringLength; m++)   //loop to go through string provided from stdin
      {
        char let = s.charAt(m);
        int letter = convertCharToInt(let);
        int currStatesArrayLength = actualLength(currStatesArray);
        int offset = 0;
        for(int n = 0; n < currStatesArrayLength; n++) //loop to go through current Possible states
        {
          if(currStatesArray[offset] != -1)
          {
            int currentState = currStatesArray[offset];
            if(transArray[currentState][letter].getLength() == 0)
            {
              shiftLeft(currStatesArray, offset);
            }
            for(int o = 0; o < transArray[currentState][letter].getLength(); o++) //loop to go through corresponding possible transitions from current state and corresponding letter
            {
              if(o + 1 != transArray[currentState][letter].getLength())
              {
                shiftRight(currStatesArray, offset);
                currStatesArray[offset] = transArray[currentState][letter].get(o);
                offset++;
              }
              else
              {
                currStatesArray[offset] = transArray[currentState][letter].get(o);
                offset++;
              }
            }
          }
        }
      }
      boolean accOrRej = false;
      int currLength = actualLength(currStatesArray);
      int accLength = actualLength(accStatesArray);
      for(int y = 0; y < currLength; y++)
      {
        for(int z = 0; z < accLength; z++)
        {
          if(currStatesArray[y] == accStatesArray[z])
          {
            accOrRej = true;
          }
        }
      }
      if(accOrRej == true)
      {
        System.out.println("accept");
      }
      else
      {
        System.out.println("reject");
      }
      for(int ass = 0; ass < currStatesArray.length; ass++)
      {
      //  System.out.println(currStatesArray[ass]);
      }
    }
    }
  }

  public static int findNumOfStates(String s)
  {
    String numStates = s.substring(18);
    int finalNum = Integer.parseInt(numStates);
    return finalNum;
  }
  public static int findAlphabetSize(String s)
  {
    String alphSize = s.substring(15);
    int finalNum = Integer.parseInt(alphSize);
    return finalNum;
  }
  public static int[] findAcceptingStates(String s)
  {
    Scanner accStatesScanner = new Scanner(s);
    String[] accStatesArray = new String[64];
    int[] temp = new int[64];
    int i = 0;
    while(accStatesScanner.hasNext())
    {
      accStatesArray[i] = accStatesScanner.next();
      temp[i] = Integer.parseInt(accStatesArray[i]);
      i++;
    }
    for(int j = i; j < 64; j++)
    {
      temp[j] = -1;
    }
    return temp;
  }
  public static int convertCharToInt(char a) //a ridiculous method to convert letters to integers, definitely a dumb way to go about it
  {
    if(a == 'a')
    {
      return 1;
    }
    else if(a == 'b')
    {
      return 2;
    }
    else if(a == 'c')
    {
      return 3;
    }
    else if(a == 'd')
    {
      return 4;
    }
    else if(a == 'e')
    {
      return 5;
    }
    else if(a == 'f')
    {
      return 6;
    }
    else if(a == 'g')
    {
      return 7;
    }
    else if(a == 'h')
    {
      return 8;
    }
    else if(a == 'i')
    {
      return 9;
    }
    else if(a == 'j')
    {
      return 10;
    }
    else if(a == 'k')
    {
      return 11;
    }
    else if(a == 'l')
    {
      return 12;
    }
    else if(a == 'm')
    {
      return 13;
    }
    else if(a == 'n')
    {
      return 14;
    }
    else if(a == 'o')
    {
      return 15;
    }
    else if(a == 'p')
    {
      return 16;
    }
    else if(a == 'q')
    {
      return 17;
    }
    else if(a == 'r')
    {
      return 18;
    }
    else if(a == 's')
    {
      return 19;
    }
    else if(a == 't')
    {
      return 20;
    }
    else if(a == 'u')
    {
      return 21;
    }
    else if(a == 'v')
    {
      return 22;
    }
    else if(a == 'w')
    {
      return 23;
    }
    else if(a == 'x')
    {
      return 24;
    }
    else if(a == 'y')
    {
      return 25;
    }
    else if(a == 'z')
    {
      return 26;
    }
    else
    {
      return -1;
    }
  }
  public static int actualLength(int[] a) //returns length of arrays that use -1 as null storage values
  {
    for(int i = 0; i < a.length; i++)
    {
      if(a[i] == -1)
      {
        return i;
      }
    }
    return 64;
  }
  public static void shiftRight(int[] A, int b) //shifts array right from position b
  {
    for(int i = 63; i > b; i--)
    {
      A[i] = A[i-1];
    }
  }
  public static void shiftLeft(int[] A, int b) //shifts array left from position b
  {
    for(int i = b; i < 63; i++)
    {
      A[i] = A[i+1];
    }
  }
}
