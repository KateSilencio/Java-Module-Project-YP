import java.lang.String;
import java.lang.System;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Double;
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static double sum = 0;
    public static void main(String[] args) {

        ProcessCreateProduct goods = new ProcessCreateProduct();
        goods.calculateAndPrint();

    }//main
//******************************************************************************************
/*класс ввода количества человек,
* содержит единственный метод enterPerson()*/
 static class NumPerson {

        int person = 0;

        public int enterPerson(){
            System.out.println("Введите количество человек (целое, больше 1): ");

            while (true){
                if (scanner.hasNextInt()){
                    person = scanner.nextInt();
                    if (person<= 1){
                        System.out.println("Число должно быть больше 1! Введите еще раз: ");
                    } else {
                        return person;
                    }
                } else {
                    System.out.println("Число должно быть целым! Введите еще раз: ");
                    scanner.next();
                }
            }
        }//enterPerson
    }//NumPerson
//**********************************************************************
/*Основной класс работы с товаром
* Содержит след методы:
* createProduct() - ввод товаров, цены, форматир рублей и проверок ввода
* getPrice() - проверка правильности введеной цены
* calculateAndPrint() - метод, содержащий всю структуру программы,
* от ввода данных, подсчета суммы, печати товаров и стоимости */
static class ProcessCreateProduct{
        ArrayList<Product>productsList = new ArrayList<>();

        ArrayList createProduct(){
            String newName = "";
            double newPrice = 0;
            String newRubles = "";

            while (true){
                System.out.println("Введите название товара: ");
                newName = scanner.next();
                boolean flPutProduct = false; //флаг уже положенного товара
                //Если уже есть данный товар в списке
                if (!productsList.isEmpty()){
                    //ищем его в списке
                    for (Product item: productsList){
                        if (item.name.equalsIgnoreCase(newName)){
                            //кладем в список вместе с ценой
                            productsList.add(item);
                            System.out.println("Товар "+ item.name + " успешно добавлен в корзину");
                            sum+= item.price;
                            flPutProduct = true;
                            break;
                        }
                    }
                }
                if (!flPutProduct) {
                    System.out.println("Введите цену товара в рублях (формат рр,кк): ");
                    newPrice = getPrice();
                    //тк склонение слово рубль - отдельный класс
                    FormatRubles format = new FormatRubles();
                    newRubles = format.formatRubles(newPrice);
                    //Товар прошел все проверки
                    //создаем объект товара, кладем в список
                    Product newProduct = new Product(newName,newPrice,newRubles);
                    productsList.add(newProduct);
                    System.out.println("Товар "+ newProduct.name + " успешно добавлен в корзину");
                    sum+= newProduct.price;
                }
                System.out.println("Хотите добавить следующий товар?");
                System.out.println("Для прекращения ввода введите команду: Завершить ");
                System.out.println("Для продолжения работы нажмите любую клавишу ");
                String command = scanner.next();
                if (command.equalsIgnoreCase("Завершить")){
                    return productsList;
                }
            }//while
        }//createProduct

    private double getPrice(){
            double number = 0;

            while (true){
                if (scanner.hasNextDouble()){
                    number = scanner.nextDouble();
                    if (number <= 0){
                        System.out.println("Цена должна быть больше 0! Введите еще раз: ");
                    } else return number;
                } else {
                    System.out.println("Число должно быть в формате: рубли,копейки! Введите еще раз: ");
                    scanner.next();
                }
            }
        }//getPrice

    void calculateAndPrint(){

            NumPerson countPerson = new NumPerson();
            int number = countPerson.enterPerson();

            ArrayList<Product> list = new ArrayList<>(createProduct());

            System.out.println("Добавленные товары: ");
            String message = "Товар: %s\tцена: %.2f %s";
            for (int i = 0; i<list.size();i++) {
                System.out.println(String.format(message,list.get(i).name,list.get(i).price,list.get(i).rubles));
            }
            double amount = (double) (sum/number);
            FormatRubles format = new FormatRubles();
            String rubl = format.formatRubles(amount);

            System.out.println("Общая цена: " + sum);
            System.out.println(String.format("Каждый человек должен заплатить: %.2f %s",amount,rubl));
    }//calculateAndPrint

}//ProcessCreateProduct
//********************************************************************
/*Класс склонения слова "рубль"
* содержит метод formatRubles(double price)*/
static class FormatRubles{

        public String formatRubles(double price){
            //отбросим дробную часть
            int digit = Double.valueOf(price).intValue();

            if(((digit % 100)>=11)&&((digit % 100)<=14)){
                return " рублей";
            } else {
                switch (digit % 10) {
                    case 1:
                        return " рубль";
                    case 2:
                    case 3:
                    case 4:
                        return " рубля";
                    default:
                        return " рублей";
                }
            }
        }
    }//FormatRubles
//****************************************************************
/*Класс структуры товара, который добавляем в список*/
 static class Product {
        String name;        //название товара
        double price;       //цена
        String rubles;      //формат валюты

        public Product(String name,double price, String rubles){
            this.name = name;
            this.price = price;
            this.rubles = rubles;
        }
    }//Product
}