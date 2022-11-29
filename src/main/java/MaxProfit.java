import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
public class MaxProfit {

    public static void main(String[] args) {

    }

    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int currentProfit;
        int leastPrice = Integer.MAX_VALUE;
        for (int i=0; i<prices.length;i++) {
            int currentPrice = prices[i];
            currentProfit = currentPrice-leastPrice;
            if (currentProfit>maxProfit)
                maxProfit=currentProfit;

            if (currentPrice<leastPrice)
                leastPrice=currentPrice;
        }
        return maxProfit;
    }
}
