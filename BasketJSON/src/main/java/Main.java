package BasketJSON.src.main.java;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static String[] products = {"1.Хлеб", "2.Яблоки", "3.Мороженка"};
    static int[] prices = {50, 100, 80};
    private static File textFile;
    static Scanner scanner = new Scanner(System.in);
    static File saveFiles = new File("basket.txt");

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        int productNumber = 0;
        int productCount = 0;

        SettingXML setting = new SettingXML(new File("shop.xml"));
        File loadFile = new File(setting.loadFile);
        File saveFile = new File(setting.saveFile);
        File logFile = new File(setting.logFile);

        /*if (saveFiles.exists()) {//проверяем наличие файла для восстановления данных о содержимом корзины
            basket = Basket.loadFromTxtFile(saveFiles);
        } else {// если файл неайден, создаем новый
            basket = new Basket(products, prices);
        }*/

        Basket basket = createBasket(loadFile, setting.isload, setting.saveFile);
        //basket = createBasket(loadFile, setting.isload, setting.saveFile);
        if (saveFile.exists()) {//проверяем наличие файла для восстановления данных о содержимом корзины
            basket = Basket.loadFromJSOnFile(saveFile);
        } else {// если файл неайден, создаем новый
            basket = new Basket(products, prices);
        }
        System.out.println("Продукты доступные для покупки:");

        ClientLog log = new ClientLog();
        showPrice();
        while (true) {
            System.out.println("Введите номер продукта и количество через пробел. " +
                    "Для завершения введите `end`");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                if (setting.isLog) {
                    log.exportAsCSV(logFile);
                }
                    break;
            }
            String[] parts = input.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;
            productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            if(setting.isLog){
                log.log(productNumber, productCount);
            }if(setting.isSave){
                switch (setting.saveFormat){
                    case "json" -> basket.saveJSON(saveFile);
                    case "txt" -> basket.saveTxt(saveFile);
                }
            }
        }
        basket.printCart(textFile);
    }
    private static Basket createBasket(File loadFile, boolean isload, String loadFormat) {
        Basket basket;
        if (isload && loadFile.exists()){
            switch (loadFormat){
                case "json":
                    basket = Basket.loadFromJSOnFile(loadFile);
                    break;
                case "txt":
                    basket = Basket.loadFromTxtFile(loadFile);
                    break;
                default:
                    basket = new Basket(products, prices);
            }
        }else {
            basket = new Basket(products,prices);
        } return basket;
    }

    public static void showPrice() {
        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i] + " " + prices[i] + " руб./шт.");
        }
    }
}