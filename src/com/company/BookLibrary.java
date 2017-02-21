package com.company;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BookLibrary implements BookList {
    private ArrayList<Book> listOfBooks = new ArrayList<>();
    //Här ska alla böcker in som finns i text fil.
    private ArrayList<Book> shoppingCart = new ArrayList<>();
    BigDecimal totalPrice = new BigDecimal(0);

    public BookLibrary(String Filename) throws IOException {
        Scanner s = new Scanner(new File(Filename));

        while (s.hasNextLine()) {
            Book book = new Book();
            String line = s.nextLine();

            String[] wordString = line.split(";");

            book.setTitle(wordString[0]);
            book.setAuthor(wordString[1]);

            wordString[2] = wordString[2].replace(",", "");
            BigDecimal bigDecimal = new BigDecimal(wordString[2]);
            book.setPrice(bigDecimal);

            int quantity = new Integer(wordString[3]);
            book.setQuantity(quantity);

            listOfBooks.add(book);
        }
        s.close();
    }

    public Book getBookByIndex(int index) {
        if (index < 0 || index > listOfBooks.size()) {
            return null;
        }
        return listOfBooks.get(index);
    }

    public void printBooks() {
        for (int i = 0; i < listOfBooks.size(); i++) {

            String title = listOfBooks.get(i).getTitle();
            String author = listOfBooks.get(i).getAuthor();
            BigDecimal price = listOfBooks.get(i).getPrice();
            int quantity = listOfBooks.get(i).getQuantity();

            System.out.println("Nummer på bok: " + i);
            System.out.println("Titel: " + title + ".");
            System.out.println("Författare: " + author + ".");
            System.out.println("Pris: " + price + ".");
            System.out.println("Antal: " + quantity + "st.");
            System.out.println("---------------------------------------------------------------");
        }
    }


    public void addBookToLibrary() {
        Book book = new Book();

        Scanner in = new Scanner(System.in);
        System.out.println("Titel på bok: ");
        String title = in.nextLine();

        System.out.println("Namn på författare: ");
        String author = in.nextLine();

        System.out.println("Pris: ");
        BigDecimal price = in.nextBigDecimal();

        System.out.println("Antal: ");
        int quantity = in.nextInt();

        boolean booksExist = false;

        for (int i = 0; i < listOfBooks.size(); i++) {
            if (listOfBooks.get(i).getTitle().equals(title) && listOfBooks.get(i).getAuthor().equals(author) && listOfBooks.get(i).getPrice().equals(price)) {
                listOfBooks.get(i).setQuantity(listOfBooks.get(i).getQuantity() + quantity);
                booksExist = true;
            }
        }
        if (!booksExist) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setPrice(price);
            book.setQuantity(quantity);
            listOfBooks.add(book);

        }


    }


    public void removeBookFromShoppingCart() {

        System.out.println("I din kundvagn finns följande: ");
        for (int i = 0; i < shoppingCart.size(); i++) {
            System.out.println("Nummer: " + i + ". " + "Titel: " + shoppingCart.get(i).getTitle() + ".");
        }
        Scanner in = new Scanner(System.in);
        System.out.println("");
        System.out.println("Ta bort bok från kundvagn med nummer: ");
        int numberToRemove = in.nextInt();

        String bookTitleToRemove = shoppingCart.get(numberToRemove).getTitle();
        String bookAuthorToRemove = shoppingCart.get(numberToRemove).getAuthor();
        BigDecimal bookPriceToRemove = shoppingCart.get(numberToRemove).getPrice();
        shoppingCart.remove(numberToRemove);

        for (int i = 0; i < listOfBooks.size(); i++) {

            if (listOfBooks.get(i).getTitle().equals(bookTitleToRemove) && listOfBooks.get(i).getAuthor().equals(bookAuthorToRemove) && listOfBooks.get(i).getPrice().equals(bookPriceToRemove)) {
                listOfBooks.get(i).setQuantity(listOfBooks.get(i).getQuantity() + 1);
                break;
            }
        }
    }


    public Book[] shoppingCart() {
        totalPrice = new BigDecimal(0);

        if (shoppingCart.isEmpty()) {
            System.out.println("Kundvagnen är tom.");

        } else {
            System.out.println("Böcker i kundvagn: ");
            for (int i = 0; i < shoppingCart.size(); i++) {
                totalPrice = totalPrice.add(shoppingCart.get(i).getPrice());
                System.out.println(shoppingCart.get(i).getTitle());
            }
            System.out.println("Totalpris i kundvagn: " + totalPrice);
        }

        Book[] bookArray = new Book[shoppingCart.size()];
        return shoppingCart.toArray(bookArray);
    }


    @Override
    public Book[] list(String searchString) {

        List<Book> searchHits = new ArrayList<>();

        for (int i = 0; i < listOfBooks.size(); i++) {
            if (listOfBooks.get(i).getTitle().toLowerCase().contains(searchString.toLowerCase()) || listOfBooks.get(i).getAuthor().toLowerCase().contains(searchString.toLowerCase())) {
                searchHits.add(listOfBooks.get(i));
            }
        }
        Book[] bookArray = new Book[searchHits.size()];
        return searchHits.toArray(bookArray);

    }

    @Override
    public boolean add(Book book, int quantity) {

        boolean success = false;

        if (book.getQuantity() >= quantity) {
            for (int i = 0; i < quantity; i++) {
                shoppingCart.add(book);
                book.setQuantity(book.getQuantity() - 1);
                success = true;
            }
        }
        return success;
    }

    @Override
    public int[] buy(Book... books) {

        BigDecimal totalPrice = new BigDecimal(0);
        for (Book b : books) {
            System.out.println(b.getTitle());
            System.out.println(b.getPrice());
            totalPrice = totalPrice.add(b.getPrice());
        }
        System.out.println("Du handlade för: " + totalPrice + "kr.");

        System.out.println("Kundvagnen töms nu.");

        shoppingCart.clear();

        return new int[0];
    }
}

//    public void searchTitle() {
//        int quantity = listOfBooks.get(0).getQuantity();
//
//        System.out.println("Sök efter Titel: ");
//        Scanner in = new Scanner(System.in);
//        String inTitle = in.nextLine();
//
//        int i = 0;
//        boolean titelFinns = true;
//        for (i = 0; i < listOfBooks.size(); i++) {
//
//            if (inTitle.contains(listOfBooks.get(i).getTitle())) {
//                titelFinns = true;
//                break;
//            } else {
//                titelFinns = false;
//            }
//        }
//
//        if (titelFinns == true) {
//            System.out.println(inTitle + " finns i lager.");
//        } else {
//            System.out.println(inTitle + " finns inte i lager.");
//        }
//        //Gör så att den inte skriver ut allting 7 gngr. Och om quantity är mer än 0. -> && listOfBooks.get(i).getQuantity()>=0 INT?!
//        System.out.println(" ");
//
//    }
//
//    public void searchAuthor() {
//        System.out.println("Sök efter författare: ");
//        Scanner in = new Scanner(System.in);
//        String inTitle = in.nextLine();
//
//        boolean forfattareFinns = true;
//        for (int i = 0; i < listOfBooks.size(); i++) {
//
//            if (inTitle.contains(listOfBooks.get(i).getAuthor())) {
//                forfattareFinns = true;
//                break;
//            } else {
//                forfattareFinns = false;
//            }
//        }
//        if (forfattareFinns == true) {
//            System.out.println(inTitle + " finns i biblioteket.");
//        } else {
//            System.out.println(inTitle + " finns inte i biblioteket.");
//
//        }
//    }