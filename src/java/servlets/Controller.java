/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.Reader;
import entity.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.ReaderFacade;
import session.UserFacade;
import util.Encription;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "Controller", urlPatterns = {
    "/showLogin",
    "/login",
    "/logout",
    "/showAddNewBook",
    "/addBook",})
public class Controller extends HttpServlet {

    @EJB
    BookFacade bookFacade;
    @EJB
    UserFacade userFacade;
    @EJB
    ReaderFacade readerFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        if (path != null) {
            switch (path) {
                case "/showLogin":
                    request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                    break;
                case "/login":
                    String login = request.getParameter("login");
                    String password = request.getParameter("password");
                    User regUser = userFacade.findUserByLogin(login);
                    if (regUser == null) {
                        request.setAttribute("info", "Неправильный логин или пароль!");
                        request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                    }
                    Encription encription = new Encription();
                    String encriptPassword = encription.getEncriptionPass(password);
                    if (!encriptPassword.equals(regUser.getPassword())) {
                        request.setAttribute("info", "Неправильный логин или пароль!");
                        request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                    }
                    HttpSession session = request.getSession(true);
                    session.setAttribute("regUser", regUser);
                    request.setAttribute("info", "Привет " + regUser.getName() + ",Вы вошли");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    break;

                case "/logout":
                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    String email = request.getParameter("email");
                    login = request.getParameter("login");
                    String password1 = request.getParameter("password1");
                    String password2 = request.getParameter("password2");
                    if (!password1.equals(password2)) {
                        request.setAttribute("info", "Нет такого пользователя!");
                        request.getRequestDispatcher("/showRegistartion.jsp").forward(request, response);

                    }
                    encriptPassword = setEncriptPass(password1, "");
                    Reader reader = new Reader(email, name, surname);
                    readerFacade.create(reader);
                    User user = new User(login, password, true, reader);

                    request.setAttribute("info", "Вы вошли");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);

                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    break;

                case "/showRegistration":
                    request.getRequestDispatcher("/showRegistartion.jsp").forward(request, response);
                    break;

                case "/showAddNewBook":
                    request.getRequestDispatcher("/showAddNewBook.jsp").forward(request, response);
                    break;

                case "/addBook":
                    String name = request.getParameter("name");
                    String author = request.getParameter("author");
                    String isbn = request.getParameter("isbn");
                    String count = request.getParameter("count");
                    Book book = new Book(isbn, name, author, Integer.parseInt(count));
                    bookFacade.create(book);
                    request.setAttribute("info", "Новая книга добавлена");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
