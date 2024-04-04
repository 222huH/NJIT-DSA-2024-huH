import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class BSTBookImplementation implements Book {
    private String bookFile;
    private String wordsToIgnoreFile;
    private BST<String, Integer> words = new BST<>();
    private int uniqueWordCount = 0;
    private int totalWordCount = 0;
    private int ignoredWordsTotal = 0;
    private int loopCount = 0;
    private Pair<String, Integer>[] sorted;
    private WordFilter filter;

    @Override
    public void read(String bookFileName, String ignoreFileName) {
        if (!checkFile(bookFileName) || !checkFile(ignoreFileName)) {
            System.out.println("Error: Invalid file names.");
            return;
        }

        bookFile = bookFileName;
        wordsToIgnoreFile = ignoreFileName;
        filter = new WordFilter(ignoreFileName);

        try (FileReader reader = new FileReader(bookFileName)) {
            char[] array = new char[1024];
            int currentIndex;
            while ((currentIndex = reader.read(array)) != -1) {
                if (currentIndex > 1) {
                    String word = new String(array, 0, currentIndex).toLowerCase(Locale.ROOT);
                    addToWords(word);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void addToWords(String word) {
        if (!filter.shouldFilter(word) && word.length() >= 2) {
            Integer currentCount = words.find(word);
            if (currentCount == null) {
                currentCount = 0;
                uniqueWordCount++;
            }
            words.add(word, currentCount + 1);
            totalWordCount++;
        } else {
            ignoredWordsTotal++;
        }
    }

    @Override
    public void report() {
        if (words.size() == 0) {
            System.out.println("*** No words to report! ***");
            return;
        }

        System.out.println("Listing words from a file: " + bookFile);
        System.out.println("Ignoring words from a file: " + wordsToIgnoreFile);
        System.out.println("Sorting the results...");
        sorted = words.toSortedArray();
        Algorithms.reverse(sorted);
        System.out.println("...sorted.");

        for (int index = 0; index < Math.min(100, sorted.length); index++) {
            String word = String.format("%-20s", sorted[index].getKey()).replace(' ', '.');
            System.out.format("%4d. %s %6d%n", index + 1, word, sorted[index].getValue());
        }

        System.out.println("Count of words in total: " + totalWordCount);
        System.out.println("Count of unique words:    " + uniqueWordCount);
        System.out.println("Count of words to ignore: " + filter.ignoreWordCount());
        System.out.println("Ignored words count:      " + ignoredWordsTotal);
        System.out.println("How many times the inner loop rolled: " + loopCount);
        System.out.println("\nInformation for BST implementation");
        System.out.println(words.getStatus());
    }

    @Override
    public void close() {
        bookFile = null;
        wordsToIgnoreFile = null;
        words = null;
        if (filter != null) {
            filter.close();
        }
        filter = null;
    }

    // Other methods remain the same

    private boolean checkFile(String fileName) {
        if (fileName != null) {
            File file = new File(fileName);
            return file.exists() && !file.isDirectory();
        }
        return false;
    }
}