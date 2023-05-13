package BasketJSON.src.main.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Basket {
    int currentPrice;
    private String[] product;
    private int[] price;
    private int[] amountPr;

    public Basket() {
    }

    public Basket(String[] products, int[] prices) {
        this.product = products;
        this.price = prices;
        this.amountPr = new int[product.length];
    }

    public void addToCart(int productNum, int amount) {
        amountPr[productNum] += amount;
    }// метод добавления продуктов в корзину

    public void printCart(File textFile)   { //метод вывода корзины в консоль
        int summProduct = 0;
        System.out.println("Список покупок:");
        for (int i = 0; i < product.length; i++) {
            if (amountPr[i] > 0) {
                currentPrice = amountPr[i] * price[i];
                summProduct += currentPrice;
                System.out.printf("%s %d руб./шт. %d шт. %d руб.\n", product[i], price[i], amountPr[i], currentPrice);
            }
        }
        System.out.printf("Итого: %d руб.", summProduct);
    }

    public void saveJSON(File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            out.print(json);

        }
    }

    public static Basket loadFromJSOnFile(File file) {//throws IOException{
        Basket basket = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String string = null;
            while ((string = bufferedReader.readLine()) != null) {
                builder.append(string);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(), Basket.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}

