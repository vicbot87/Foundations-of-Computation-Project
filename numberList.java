//Written by Victor Botteicher
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class numberList<Element> {
    private Object numbers[];
    private int length = 0;
    private static final int totalSize = 64;

    public numberList() {
        numbers = new Object[totalSize];
    }

    public int getLength()
    {
      return length;
    }

    public void add(Element a) {

        numbers[length] = a;
        length++;
    }
    public boolean isEmpty()
    {
      if(length == 0)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    public void remove(int i)
    {
      if(length > 0)
      {
        for(int j = i; j < length; j++)
        {
          if(j == length - 1)
          {
            numbers[j] = null;
            System.gc();
          }
          else
          {
            numbers[j] = numbers[j+1];
          }
        }
        length--;
      }
    }
    public int indexOf(Element a)
    {
      if(length > 0)
      {
        for(int i = 0; i < length; i++)
        {
          if(numbers[i] == a)
          {
            return i;
          }
        }
      }
      return -1;
    }
    public void swap(int a, int b)
    {
      Object temp = numbers[a];
      numbers[a] = numbers[b];
      numbers[b] = temp;
    }
    @SuppressWarnings("unchecked")
   public Element get(int i) {
       if (i >= length || i < 0) {
           throw new IndexOutOfBoundsException();
       }
       return (Element) numbers[i];
   }
}
