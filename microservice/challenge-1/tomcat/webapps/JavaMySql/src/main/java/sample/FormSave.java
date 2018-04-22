package main.java.sample;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.sample.db.DbUtils;
import main.java.sample.util.Utility;
import main.java.sample.util.WebUtils;

/**
 * Servlet implementation class FormSave This class saves the data into database
 */
public class FormSave extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormSave() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     *
     *      This functions saves the data into user table
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // get request parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String description = request.getParameter("description");
        String submit = request.getParameter("submit");
        String clear = request.getParameter("clear");
        String x = "hello";

        try {

            Utility utility = new Utility();
            WebUtils webUtil = new WebUtils(request);

            String nextPage = webUtil.getHeader("origin") + request.getContextPath() + "/home";

            String dburl = utility.getDburl();
            String dbuser = utility.getDbuser();
            String dbpassword = utility.getDbpassword();
            String dbname = utility.getDbname();
            String dbport = utility.getDbPort();

            DbUtils db = new DbUtils(dburl, dbport, dbname, dbuser, dbpassword);


            if (submit != null)
                db.addUser(name, email, description);
            else if (clear != null)
                db.deleteUsers();

            db.disconnect();

            String exMsg = db.getExceptionMessage();

            if (exMsg.equals("")) {
                response.setStatus(response.SC_MOVED_PERMANENTLY);
                response.setHeader("Location", nextPage);
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.print("<!doctype html><html><head><meta charset='utf-8'><title>App42 Sample Java-MySql Application</title><link href='css/style-User-Input-Form.css' rel='stylesheet' type='text/css'></head><body><div class='App42PaaS_header_wrapper'><div class='App42PaaS_header_inner'><div class='App42PaaS_header'><div class='logo'><a href='http://app42paas.shephertz.com'><img border='0' alt='App42PaaS' src='images/logo.png'></img></a></div></div></div></div><div class='App42PaaS_body_wrapper'><div class='App42PaaS_body'><div class='App42PaaS_body_inner'><div class='contactPage_title'>");
                out.print("<h2 align='center'>Error occured. See Logs.</h2><br/><br/>");
                out.print("<br/><a href='javascript:history.back()' style='font-size: 18px;'>Go Back</a>");
                out.print("</div></div></div></div></body></html>");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("<!doctype html><html><head><meta charset='utf-8'><title>App42 Sample Java-MySql Application</title><link href='css/style-User-Input-Form.css' rel='stylesheet' type='text/css'></head><body><div class='App42PaaS_header_wrapper'><div class='App42PaaS_header_inner'><div class='App42PaaS_header'><div class='logo'><a href='http://app42paas.shephertz.com'><img border='0' alt='App42PaaS' src='images/logo.png'></img></a></div></div></div></div><div class='App42PaaS_body_wrapper'><div class='App42PaaS_body'><div class='App42PaaS_body_inner'><div class='contactPage_title'>");
            out.print("<h2 align='center'>Error occured. See Logs.</h2><br/><br/>");
            out.print("<br/><a href='javascript:history.back()' style='font-size: 18px;'>Go Back</a>");
            out.print("</div></div></div></div></body></html>");
        }
    }
}
