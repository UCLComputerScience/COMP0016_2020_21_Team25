package backend.web.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegistrationCodeGenerator{
    private final String filePath="src/main/java/backend/web/util/dictionary.txt";
    private final int numOfWords=3;

    public ArrayList<String> generateCode () throws IOException
    {
        int listSize=numOfWords;
        int count=0;
        int randomNum;
        ArrayList<String> wordList= new ArrayList<String>(listSize);
        Random r = new Random();

        File dictionaryFile = new File(filePath).getAbsoluteFile();
        BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile));
        String currentLine;

        while ((currentLine = reader.readLine()) != null)
        {
            count++;
            if (count<=numOfWords)
            {
                wordList.add(currentLine);
            }
            else
            {
                randomNum = r.nextInt(count);
                if (randomNum<listSize)
                {
                    wordList.set(randomNum, currentLine);
                }
            }
        }
        return wordList;
    }
}