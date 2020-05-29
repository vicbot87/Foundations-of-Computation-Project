//Written by Victor Botteicher
import static java.lang.System.out;
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.Integer.*;

public class Program
{
  public static void main(String[] args) throws Exception
  {
    Scanner fileReader = new Scanner(new File(args[0]));
    boolean movedSomething = false;
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

    //sort transition table
    boolean changedSomethingInTransTable = true;
    while(changedSomethingInTransTable == true) //close epsilon nfa
    {
      int numOfChanges = 0;
      for(int j = 0; j < numStates; j++) //row iterator
      {
        if(transArray[j][0].isEmpty() == false) //check epsilon column at row J
        {
          int lengthOfETransitions = transArray[j][0].getLength();
          int grabIndex = 0;
          for(int i = 0; i < lengthOfETransitions; i++) //epsilon transition iterator at row J
          {
            Integer a = new Integer(transArray[j][0].get(i)); //finds row to go check
            int row = a.intValue();
            if(contains(row, accStatesArray) == true && contains(j, accStatesArray) == false)// check to see if we should add accepting state
            {
              for(int z = 0; z < accStatesArray.length; z++)
              {
                if(accStatesArray[z] == -1)
                {
                  accStatesArray[z] = j;
                  break;
                }
              }
            }
            for(int k = 1; k < alphabetSize + 1; k++) //row iterator
            {
              if(transArray[row][k].isEmpty() == false)
              {
                movedSomething = true;
                int lengthOfNewETransitions = transArray[row][k].getLength();
                for(int l = 0; l < lengthOfNewETransitions; l++) //transition iterator in [row][k]
                {
                  Integer b = new Integer(transArray[row][k].get(l));
                  int tempNum = b.intValue();
                  boolean numAlreadyInTrans = false;
                  for(int m = 0; m < transArray[j][k].getLength(); m++) //checks to see if transition already exists in original row
                  {
                    Integer c = new Integer(transArray[j][k].get(m));
                    int tempNum1 = c.intValue();
                    if(tempNum1 == tempNum)
                    {
                      numAlreadyInTrans = true;
                    }
                  }
                  if(numAlreadyInTrans == false)
                  {
                    numOfChanges++;
                    transArray[j][k].add(b);
                  }
                }
              }
            }
          }
        }
      }
      if(numOfChanges == 0)   //transArray has been fully sorted
      {
        for(int epRemoverRow = 0; epRemoverRow < numStates; epRemoverRow++) //remove all epsilon transitions
        {
          int lengthofETransToRemove = transArray[epRemoverRow][0].getLength();
          for(int eTransRemove = 0; eTransRemove < lengthofETransToRemove; eTransRemove++)
          {
            transArray[epRemoverRow][0].remove(0);
          }
        }
        changedSomethingInTransTable = false; //exit while loop
      }
    }
    //sort Accepting states array
    boolean sortedAccStatesArray = false;
    while(sortedAccStatesArray == false)
    {
      int numberOfChanges = 0;
      for(int accStatesIterator = 0; accStatesIterator < accStatesArray.length; accStatesIterator++)
      {
        if(accStatesIterator != accStatesArray.length - 1)
        {
          if(accStatesArray[accStatesIterator + 1] != -1)
          {
            if(accStatesArray[accStatesIterator] > accStatesArray[accStatesIterator + 1])
            {
              int temp = accStatesArray[accStatesIterator];
              accStatesArray[accStatesIterator] = accStatesArray[accStatesIterator + 1];
              accStatesArray[accStatesIterator + 1] = temp;
              numberOfChanges++;
            }
          }
        }
      }
      if(numberOfChanges == 0)
      {
        sortedAccStatesArray = true;
      }
    }

    //sort transition table
    for(int aa = 0; aa < numStates; aa++)
    {
      for(int bb = 1; bb < alphabetSize + 1; bb++)
      {
        boolean sortedTransTableaabb = false;
        while(sortedTransTableaabb == false)
        {
          int changes = 0;
          for(int cc = 0; cc < transArray[aa][bb].getLength(); cc++)
          {
            if(cc != transArray[aa][bb].getLength() - 1)
            {
              Integer aInteger = new Integer(transArray[aa][bb].get(cc));
              int aInt = aInteger.intValue();
              Integer bInteger = new Integer(transArray[aa][bb].get(cc + 1));
              int bInt = bInteger.intValue();
              if(aInt > bInt)
              {
                transArray[aa][bb].swap(cc, cc + 1);
                changes++;
              }
            }
          }
          if(changes == 0)
          {
            sortedTransTableaabb = true;
          }
        }
      }
    }




    System.out.println(line1);
    System.out.println(line2);
    System.out.print("Accepting states: ");
    for(int p = 0; p < accStatesArray.length; p++)
    {
      if(accStatesArray[p] != -1)
      {
        System.out.print(accStatesArray[p] + " ");
      }
    }
    System.out.println("");
    for(int printRow = 0; printRow < numStates; printRow++)
    {
      for(int printColumn = 0; printColumn < alphabetSize+1; printColumn++)
      {
        System.out.print("{");
        for(int transIterator = 0; transIterator < transArray[printRow][printColumn].getLength(); transIterator++)
        {
          System.out.print(transArray[printRow][printColumn].get(transIterator));
          if(transIterator != transArray[printRow][printColumn].getLength() - 1)
          {
            System.out.print(",");
          }
        }
        System.out.print("}" + " ");
      }
      System.out.println("");
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
  public static boolean contains(int a, int[] b) //checks to see if an integer a is contained within a matrix b
  {
    for(int i = 0; i < b.length; i++)
      {
        if(a == b[i])
        {
          return true;
        }
    }
    return false;
  }
}
