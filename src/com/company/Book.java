package com.company;

import java.math.BigDecimal;


public class Book {


        private String title;
        private String author;
        private BigDecimal price;
        private int quantity;
//        private int id;

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public void setPrice(BigDecimal price) {
                this.price = price;
        }


        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }


//        public int getId() {
//                return id;
//        }
//
//        public void setId(int id) {
//                this.id = ++id;
//        }
}
