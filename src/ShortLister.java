import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;

public class ShortLister
{
    public static void main(String args[])
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        ArrayList<String> allWords = new ArrayList<>();
        ShortWordFilter filter = new ShortWordFilter();

        try
        {
            File workingDirectory = new File(System.getProperty("user.dir")+"/src");
            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                InputStream input =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(input));

                while(reader.ready())
                {
                    rec = reader.readLine();
                    allWords.addAll(List.of(rec.split("\s")));
                }
                reader.close();
                System.out.println("Data file read!");

                for (int i = 0; i < allWords.size(); i++)
                {
                    allWords.get(i).trim();
                    if (allWords.get(i).length() == 0)
                    {
                        allWords.remove(i);
                    }
                }
                System.out.println("Short Words:");

                for (String word : allWords)
                {
                    boolean isShortWord = false;
                    isShortWord = filter.accept(word);
                    if(isShortWord == true)
                    {
                        System.out.println(word.trim());
                        Collector.collectsAll(word);
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }



    }
}