package org.poo.cb;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;

// Strategy Pattern
public interface Stocks {
    void performAction(TreeMap<String, Stock> stockCollection);
}

class Stock {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String company;
    private TreeMap<Date, Double> stockPrices;

    public Stock(String company, TreeMap<Date, Double> stockPrices) {
        this.company = company;
        this.stockPrices = stockPrices;
    }

    public String getCompany() {
        return company;
    }

    public TreeMap<Date, Double> getStockPrices() {
        return stockPrices;
    }

    public Double getLatestPrice() {
        return stockPrices.lastEntry().getValue();
    }
}

class StockCollection {
    private final TreeMap<String, Stock> stocks;

    public StockCollection() {
        this.stocks = new TreeMap<>();
    }

    public TreeMap<String, Stock> getStocks() {
        return stocks;
    }

    public void addStock(Stock stock) {
        stocks.put(stock.getCompany(), stock);
    }

    public void setStockPricesFromFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + fileName));
            String line = br.readLine();

            //Getting the dates
            String[] vars = line.split(",");
            Date[] dates = new Date[vars.length - 1];
            for (int i = 1; i < vars.length; i++) {
                dates[i - 1] = Stock.sdf.parse(vars[i].split(" ")[0]);
            }
            line = br.readLine();

            while(line != null) {
                vars = line.split(",");
                TreeMap<Date, Double> stockPrices = new TreeMap<>();
                for (int i = 1; i < vars.length; i++) {
                    stockPrices.put(dates[i - 1], Double.parseDouble(vars[i]));
                }
                Stock stock = new Stock(vars[0], stockPrices);
                addStock(stock);

                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}

// Strategy Pattern
class BuyStock implements Stocks {
    private final String stockName;
    private Double latestPrice;

    public BuyStock(String stockName) {
        this.stockName = stockName;
    }

    public String getStockName() {
        return stockName;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public void modifyStocks(@NotNull Portofolio portofolio, String stockName, Double quantity, boolean statusPremium, ArrayList<String> recommendedStockList) throws Exceptions {
        portofolio.addStock(stockName, quantity);
        Account account = portofolio.getAccount("USD");
        double accountMoney = account.getMoney();

        if (accountMoney < quantity * getLatestPrice()) {
            throw new Exceptions.InsufficientFundsForStocks("Insufficient amount in account for buying stock");
        }

        if (statusPremium) {
            if (recommendedStockList.contains(stockName)) {
                account.addMoney(quantity * getLatestPrice() * -0.95);
            } else {
                account.addMoney(quantity * getLatestPrice() * -1.00);
            }
        } else {
            account.addMoney(quantity * getLatestPrice() * -1.00);
        }
    }

    @Override
    public void performAction(@NotNull TreeMap<String, Stock> stockCollection) {
        String stockName = getStockName();
        Double latestPrice = stockCollection.get(stockName).getLatestPrice();
        setLatestPrice(latestPrice);
    }
}

// Strategy Pattern
class RecommendStock extends StockCollection implements Stocks{
    private ArrayList<String> recommendedStockList = new ArrayList<>();

    public RecommendStock(@NotNull TreeMap<String, Stock> stockCollection) {
        ArrayList<String> recommendedStockList = new ArrayList<>();

        for (Stock stock : stockCollection.values()) {
            Double sma5 = calculateSMA(stock.getStockPrices(), 5);
            Double sma20 = calculateSMA(stock.getStockPrices(), 10);

            if (sma5 > sma20)
                recommendedStockList.add(stock.getCompany());
        }
        recommendedStockList.sort(Comparator.reverseOrder());
        setRecommendedStockList(recommendedStockList);
    }

    public ArrayList<String> getRecommendedStockList() {
        return recommendedStockList;
    }

    public void setRecommendedStockList(ArrayList<String> recommendedStockList) {
        this.recommendedStockList = recommendedStockList;
    }

    @NotNull
    private Double calculateSMA(@NotNull TreeMap<Date, Double> stockPrices, int days) {
        double sum = 0;

        for(int i = stockPrices.size() - 1; i >= stockPrices.size() - days; i--) {
            sum += stockPrices.get(stockPrices.keySet().toArray()[i]);
        }

        return sum / days;
    }

    @Override
    public void performAction(@NotNull TreeMap<String, Stock> stockCollection) {
        StringBuilder recommendedStock = new StringBuilder();
        for (String stock : getRecommendedStockList()) {
            recommendedStock.append("\"").append(stock).append("\",");
        }

        String output = "{\"stocksToBuy\":[" + recommendedStock.substring(0, recommendedStock.length() - 1) + "]}";
        System.out.println(output);
    }
}