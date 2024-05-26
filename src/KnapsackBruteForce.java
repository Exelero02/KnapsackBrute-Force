import java.util.*;
import java.io.*;

public class KnapsackBruteForce {
    private static int[] generateVector(int number, int length)
    {
        int[] vector = new int[length];
        for (int i = 0; i < length; i++)
        {
            vector[length - 1 - i] = (number >> i) & 1;
        }
        return vector;
    }
    static int capacity;
    static List<Item> items;
    static class Item {
        int weight;
        int value;
        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }
    public static void main(String[] args) {
        String filePath = "src\\data\\data.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            capacity = Integer.parseInt(reader.readLine().trim());
            items = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int weight = Integer.parseInt(parts[0]);
                int value = Integer.parseInt(parts[1]);
                items.add(new Item(weight, value));
            }
            reader.close();
            long startTime = System.currentTimeMillis();
            int n = items.size();
            int[] bestVector = null;
            int bestWeight = 0;
            int bestValue = 0;
            for (int i = 0; i < (1 << n); i++) {
                int[] vector = generateVector(i, n);
                int totalWeight = 0;
                int totalValue = 0;
                for (int j = 0; j < n; j++) {
                    if (vector[j] == 1) {
                        totalWeight += items.get(j).weight;
                        totalValue += items.get(j).value;
                    }
                }
                if (totalWeight <= capacity && totalValue > bestValue) {
                    bestVector = vector;
                    bestValue = totalValue;
                    bestWeight = totalWeight;
                }
            }
            long endTime = System.currentTimeMillis();
            if (bestVector != null) {
                System.out.print("Best characteristic vector: ");
                for (int bit : bestVector)
                {
                    System.out.print(bit);
                }
                System.out.println("\nTotal weight: "+bestWeight);
                System.out.println("Total value: "+bestValue);
            } else {
                System.out.println("No valid solution");
            }
            System.out.println("Execution time: "+(endTime - startTime) + "ms");
        } catch (IOException e) {
            System.out.println("Error has been detected!!");
        }
    }
}
