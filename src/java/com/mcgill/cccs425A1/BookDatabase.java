/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mcgill.cccs425A1;

import java.util.*;

//DAO = Data Access Object
public class BookDatabase {
    //Use arraylist to store the product
    private static BookDatabase databaseInstance;
    private static List<Book> bookList = new ArrayList<>();
    
    //Making mock data 
    static {
        bookList.add(new Book(1, "JavaBook", "1990"));
        bookList.add(new Book(2, "PHP", "1989"));
        bookList.add(new Book(3, "HTML5", "2000"));
    }
    
    private BookDatabase(){}
    
    public static BookDatabase getInstance(){
     if(databaseInstance == null){
         databaseInstance = new BookDatabase();
     } 
     return databaseInstance;
            
    }
    
    //list all my books
    public List<Book> listAll(){
        return new ArrayList<Book>(bookList);
    }
    
    //Add new Book
    public int create(Book book){
        //Increments Id by one as Books are added
        int newID = bookList.size()+1;
        book.setId(newID);
        bookList.add(book);
        return newID; 
    }
    
    //Retreive book with id
    public Book read(int id){
        // search for book in db 
        
        for (int i=0; i<bookList.size(); i++ ){
            if (id == bookList.get(i).getId()){
                return bookList.get(i);
            }
        }
        
        return null;
    }
    
    //Updtae Book
    public boolean update(Book book){
        int index = bookList.indexOf(book);
        if(index >=0){
            bookList.set(index, book);
            return true;
        }
        return false;
    }
  
    //Delete Book
    public boolean delete(int id) {
        Book findBookID = new Book(id);
        int index = bookList.indexOf(findBookID);
        if (index >= 0) {
            bookList.remove(index);
            return true;
        }
        return false;
    }
    
}
