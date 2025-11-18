/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling and Trevor McQueen
 * @version    2025.11.17
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    public int numberOFAccesses()
    {
        int total = 0;
        for (int count : hourCounts) {
            total += count;
        }
        return total;
    }
    
    public int busiestHour()
    { 
        int busiest = 0;
        for (int hour = 1; hour < hourCounts.length; hour++) {
             if (hourCounts[hour] > hourCounts[busiest]) {
                 busiest = hour;
             }
            
        }
        return busiest;
    }
    
    public int quietestHour()
    {
        int quietest = 0;
        for (int hour = 1; hour < hourCounts.length; hour++) {
             if (hourCounts[hour] < hourCounts[quietest]) {
                 quietest = hour;
             }
        }
        return quietest;
    }
    
    public int busiestTwoHourPeriod()
    { 
        int bestStart = 0;
        int bestTotal = hourCounts[0] + hourCounts[1];
        
        for (int start = 1; start < 24; start++) {
            int next = (start + 1) % 24;
            int sum = hourCounts[start] + hourCounts[next];
            
            if (sum > bestTotal) {
                bestTotal = sum;
                bestStart = start;
            }
        }
        return bestStart;
    }
}       

