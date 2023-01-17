/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mcgill.cccs425.servlet;


import com.mcgill.cccs425A1.Book;
import com.mcgill.cccs425A1.BookDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


public class BookServlet extends HttpServlet {
    
    private BookDatabase database = BookDatabase.getInstance();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (request.getQueryString() != null){
            doGetBookById(request, response);
        }
        else {
            bookList(response);
           
            }
        processRequest(request, response);
    }
    
    protected void bookList(HttpServletResponse response) throws ServletException, IOException{
        List<Book> bookList = database.listAll();
        if(!bookList.isEmpty()){
            String allbookNames = "";
            // code here to populate allbookNames
            for(int i = 0; i<bookList.size(); i++){
                allbookNames += "Id: " + bookList.get(i).getId() + ", Title: " + bookList.get(i).getTitle() + ", Date:" + bookList.get(i).getDate() + " || ";
            }
            try ( PrintWriter out = response.getWriter()) {
                out.println(allbookNames);
            }
        }
    }
    
    protected void doGetBookById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1st get request from parameters, 
        String reqID = request.getParameter("id");//Returns the value of id
        Book respBook = database.read(Integer.parseInt(reqID));
        
        if(respBook == null){
            try ( PrintWriter out = response.getWriter()) {
                out.println("Book not found");
            }
        } else{
            try ( PrintWriter out = response.getWriter()) {
                out.println(respBook.getTitle());
            }
        }
    }
    

            
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        //User wants to add a new book, will increment id by one, and user will need to supply data and title
        Book newBook = new Book();
        
        String reqTitle = request.getParameter("title");
        String reqDate = request.getParameter("date");

        
        if(reqTitle != null){
            newBook.setTitle(reqTitle);
            } else {
            newBook.setTitle("null");
        }
              
        if(reqDate != null){
            newBook.setDate(reqDate);  
            } else {
            newBook.setDate("null");
        }
        
        database.create(newBook);
        
        bookList(response);
      
        processRequest(request, response);
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reqID = (Integer.parseInt(request.getParameter("id")));
        String reqTitle = request.getParameter("title");
        String reqDate = request.getParameter("date");
        
        database.update(database.read(reqID));
        if(reqTitle != null){
            database.read(reqID).setTitle(reqTitle);
            } else {
            database.read(reqID).setTitle("null");
        }
              
        if(reqDate != null){
            database.read(reqID).setDate(reqDate);  
            } else {
            database.read(reqID).setDate("null");
        }

        bookList(response);
      
        processRequest(request, response);
        
    }

   
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reqID = (Integer.parseInt(request.getParameter("id")));
         
        if(database.read(reqID) == null){
            try ( PrintWriter out = response.getWriter()) {
                out.println("Book not found");
            }
        } else{
            database.delete(reqID);
        }
         
        bookList(response);
      
        processRequest(request, response);
        
    }
    
    
}
