package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        BookLibrary bl = new BookLibrary("src/books.txt");

        boolean running = true;
        while (running) {
            System.out.println(" ");
            System.out.println("Skriv ett nummer av följande alternativ:");
            System.out.println("1. Se alla böcker som finns i lager");
            System.out.println("2. Sök på en bok");
            System.out.println("3. Se över din kundvagn.");
            System.out.println("4. Lägg till böcker i kundvagn");
            System.out.println("5. Ta bort böcker från din kundvagn.");
            System.out.println("6. Köp.");
            System.out.println("7. Lägg till böcker till biblioteket.");
            System.out.println("8. Stäng ner programmet.");
            int Message1 = in.nextInt();

            switch (Message1) {
                case 1:
                    bl.printBooks();
                    break;

                case 2:
                    System.out.println("Skriv sökterm:");
                    String searchTerm = in.next();
                    Book[] books = bl.list(searchTerm);
                    if(books.length == 0) {
                        System.out.println("Hittade ingen bok för sökterm: "+ searchTerm);
                    } else {
                        for (Book b: books) {
                            if (b.getQuantity() > 0) {
                                System.out.println(b.getTitle()+", "+b.getAuthor() + " finns i lager.");
                            } else {
                                System.out.println(b.getTitle()+", "+b.getAuthor() + " finns inte i lager.");
                            }
                        }
                    }
                    break;

                case 3:
                    bl.shoppingCart();
                    break;

                case 4:
                    bl.printBooks();
                    System.out.println("Ange nummer på den bok du vill köpa:");
                    int bokID = in.nextInt();
                    System.out.println("Ange hur många:");
                    int quantity = in.nextInt();

                    Book book = bl.getBookByIndex(bokID);
                    if(book==null){
                        System.out.println("Fel inmatning.");
                        break;
                    }
                    //Skickar in en siffra som hämtar ut rätt bok.
                    boolean success = bl.add(book, quantity);
                    if (success){
                        System.out.println("Böckerna i tillagda i din kundvagn");
                    }else{
                        System.out.println("Tyvärr det finns inte tillräckligt med böcker.");
                    }
                    break;

                case 5:
                    bl.removeBookFromShoppingCart();
                    break;

                case 6:
                    bl.buy(bl.shoppingCart());
                    break;

                case 7:
                    bl.addBookToLibrary();
                    break;

                case 8:
                    running=false;
                    break;
                default:
                    System.out.println("Fel, testa igen");
                    break;
            }
        }
    }
}
