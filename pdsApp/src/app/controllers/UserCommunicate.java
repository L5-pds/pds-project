package app.controllers;

import app.listeners.*;
import app.models.*;
import app.helpers.*;
import app.models.other.PaneSearchIndicator;
import app.models.other.dataSearchIndicator;
import app.models.other.datasetBarChart;
import app.models.other.datasetPieChart;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.jfree.data.general.DefaultPieDataset;

public class UserCommunicate implements Runnable {
  private Socket socket;
  private WelcomeListenerServer listener;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private Serialization gsonSerial;
  private String message = null;
  private int poolIndex=-1;
  private Advisor user = null;

  public UserCommunicate(Socket socket, WelcomeListenerServer l){
    this.socket = socket;
    this.listener = l;
    this.gsonSerial = new Serialization();
  }

  public void run() {
    String query;
    String[] splitedQuery;
    String method;
    String typeObject;
    String object;
    boolean userConnected = false;

    try {
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream());
    } catch (Exception e1) {
        try {
        socket.close();
        } catch (Exception e2) {
            listener.changeTextLog("WARNING - Erreur de fermeture de la socket");
        }
        listener.changeTextLog("WARNING - Erreur de déclaration des variables de communications (in/out)");
    }

    //Authentication process
    while((!socket.isClosed()) && (!userConnected)){
      try {
        query = in.readLine();
        splitedQuery = query.split("/");

        method = splitedQuery[0];
        typeObject = splitedQuery[1];
        object = splitedQuery[2];

        if (method.equals("AUTH")){
          if(typeObject.equals("User")){
            user = gsonSerial.unserializeUser(object);
            userConnected = getConnection(user.getLogin(), user.getPassword());
          }
        }
      } catch (Exception e) {
        listener.changeTextLog("CONNECT_WARNING - gone");
        if (poolIndex != -1){
          Server.connectionPool[poolIndex].use(false);
          listener.updateInfoLabel();
        }
        try {
          socket.close();
        } catch (IOException e1) {
          listener.changeTextLog("SERVER_WARNING - closing socket error");
        }
      }
    }//end of while loop

    //Communicate process
    while((!socket.isClosed()) && (userConnected)){
      try {
        query = in.readLine();
        splitedQuery = query.split("/");

        method = splitedQuery[0];
        typeObject = splitedQuery[1];
        object = splitedQuery[2];

        switch (method) {
            case "INSERT": //Case for insert into database
                switch (typeObject) {
                    case "Address": //Case for insert into database new address
                        Address newAddress = gsonSerial.unserializeAddress(object);
                        String request = "INSERT INTO t_adress VALUES ("
                                + newAddress.getId() + ", "
                                + newAddress.getStreetNb() + ", "
                                + "'" + newAddress.getStreetName() + "'" + ", "
                                + "'" + newAddress.getCityName() + "'" + ", "
                                + "'" + newAddress.getZipCode() + "'"
                                + ");";
                        String response = Server.connectionPool[poolIndex].requestWithoutResult(request);
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - add new address - " + response);
                        out.println(response);
                        out.flush();
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "UPDATE": //Case for update database
                switch (typeObject) {
                    case "Address": //Case for update existing address into database
                        //Coding
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "DELETE": //Case for delete from database
                switch (typeObject) {
                    case "Address": //Case for delete existing address from database
                        //Coding
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_1": //Spécifique THIBAULT DON'T TOUCHE !!!
                String request;
                ResultSet response;
                ArrayList<String> listString = new ArrayList();
                datasetPieChart returnDatasetPieChart = null;
                datasetBarChart returnDatasetBarChart = null;
                switch (typeObject) {
                    case "SelectSearchLoan":

                        String[] querySplited = object.split(";");

                        String idAgency = querySplited[0];
                        String dateBegin = querySplited[1];
                        String dateEnd = querySplited[2];
                        String typeAdvisor = querySplited[3];
                        String typeLoan = querySplited[4];
                        String typeCustomer = querySplited[5];
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat formatterQuery = new SimpleDateFormat("yyyy-MM-dd");
                        long val1 = Long.parseLong(dateBegin);
                        long val2 = Long.parseLong(dateEnd);
                        Date dateBegin1=new Date(val1);
                        Date dateEnd1=new Date(val2);

                        String addDateCondition = "AND t_loan.entry BETWEEN '" + formatterQuery.format(dateBegin1) + "' AND '" + formatterQuery.format(dateEnd1) + "' ";

                        String addAdvisorCondition = "";
                        if(!typeAdvisor.equals("TOUS")) {
                            addAdvisorCondition = "AND t_advisor.login = '" + typeAdvisor + "' ";
                        }
                        String addTypeLoanCondition = "";
                        if(!typeLoan.equals("TOUS")) {
                            addTypeLoanCondition = "AND t_type_loan.wording = '" + typeLoan + "' ";
                        }
                        String addCustomerCondition = "";
                        if(!typeCustomer.equals("TOUS")) {

                            String dateNow = formatter.format(Calendar.getInstance().getTime());
                            Calendar calendar = Calendar.getInstance();
                            String date30;
                            String date60;
                            switch (typeCustomer) {
                                case "Jeune (moins de 30 ans)":
                                    calendar.add(Calendar.YEAR, -30);
                                    date30 = formatter.format(calendar.getTime());

                                    addCustomerCondition = "AND t_client.birth BETWEEN '" + date30 + "' AND '" + dateNow + "' ";

                                    break;
                                case "Adulte (entre 30 ans et 60 ans)":
                                    calendar.add(Calendar.YEAR, -30);
                                    date30 = formatter.format(calendar.getTime());
                                    calendar.add(Calendar.YEAR, -30);
                                    date60 = formatter.format(calendar.getTime());

                                    addCustomerCondition = "AND t_client.birth BETWEEN '" + date60 + "' AND '" + date30 + "' ";

                                    break;
                                case "Senior (plus de 60 ans)":
                                    calendar.add(Calendar.YEAR, -60);
                                    date60 = formatter.format(calendar.getTime());

                                    addCustomerCondition = "AND t_client.birth > '" + date60 + "' ";

                                    break;
                                default:
                                    break;
                            }
                        }

                        request = "SELECT t_client.first_name, t_client.last_name, t_advisor.login, t_loan.amount, t_loan.length_loan, t_type_loan.wording, t_type_loan.rate, t_loan.entry " +
                                "FROM t_client, t_loan, t_type_loan, t_advisor " +
                                "WHERE t_loan.id_client = t_client.id_client " +
                                "AND t_loan.id_advisor = t_advisor.id_advisor " +
                                "AND t_loan.id_type_loan = t_type_loan.id_type_loan " +
                                addAdvisorCondition +
                                addTypeLoanCondition +
                                addCustomerCondition +
                                addDateCondition +
                                "AND t_advisor.id_agency = " + idAgency + ";";

                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)    {

                            int nbLoan = 0;
                            double moyAmount = 0;
                            int moyLenght = 0;
                            double allBenefit = 0;

                            PaneSearchIndicator tableInfo = new PaneSearchIndicator();
                            ArrayList<String> listing= new ArrayList();

                            while (response.next())  {
                                nbLoan++;
                                moyAmount = moyAmount + response.getDouble("amount");
                                moyLenght = moyLenght + response.getInt("length_loan");

                                double montant = response.getDouble("amount");
                                int mensualite = response.getInt("length_loan");
                                double thetaux = response.getDouble("rate");

                                allBenefit = allBenefit + ((((montant*(thetaux/100)*(Math.pow((1+thetaux/100),mensualite)))/(Math.pow((1+thetaux/100),mensualite)-1))*mensualite)-montant);

                                listing.add(response.getString("first_name") + " " + response.getString("last_name") + ";" +
                                        response.getString("login") + ";" +
                                        response.getString("amount") + ";" +
                                        response.getString("length_loan") + ";" +
                                        response.getString("wording") + ";" +
                                        response.getDouble("rate") + ";" +
                                        response.getString("entry"));
                            }

                            if(nbLoan == 0) {
                                out.println("no line");
                                out.flush();
                                break;
                            }else   {

                                moyAmount = (moyAmount/nbLoan)*100;
                                moyAmount = Math.round(moyAmount)/100;
                                moyLenght = Math.round(moyLenght/nbLoan);

                                tableInfo.setInfoValue(nbLoan, moyAmount, moyLenght, allBenefit);

                                listener.changeTextLog("Ligne - " + listing.size());
                                listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - specific search - " + nbLoan + " line");
                                String testing = gsonSerial.serializePaneSearchIndicator(tableInfo) + "root" + gsonSerial.serializeArrayList(listing);
                                out.println(testing);
                                out.flush();
                                break;
                            }
                        } else  {
                            out.println("ERROR");
                            out.flush();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - specific search - error");
                        }
                        break;
                    case "SelectDataSearch":

                        dataSearchIndicator dataComposent = new dataSearchIndicator();

                        request = "SELECT login FROM t_advisor WHERE id_agency = " + object + ";";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)    {
                            ArrayList<String> listAdvisor = new ArrayList();
                            listAdvisor.add("TOUS");
                            while (response.next())  {
                                listAdvisor.add(response.getString("login"));
                            }
                            dataComposent.setAdvisorList(listAdvisor);
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get all advisor - " + response.getRow() + " line");
                        } else  {
                            listString.add("ERROR");
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }
                        request = "SELECT wording FROM t_type_loan;";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)    {
                            ArrayList<String> listTypeLoan = new ArrayList();
                            listTypeLoan.add("TOUS");
                            while (response.next())  {
                                listTypeLoan.add(response.getString("wording"));
                            }
                            dataComposent.setTypeLoanList(listTypeLoan);
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get all type loan - " + response.getRow() + " line");
                        } else  {
                            listString.add("ERROR");
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }

                        out.println(gsonSerial.serializeDataSearchIndicator(dataComposent));
                        out.flush();
                        break;
                    case "LoanPerType":
                        request = "SELECT t_type_loan.wording AS name, COUNT(t_loan.id_loan) AS value " +
                                "FROM t_type_loan, t_loan, t_advisor " +
                                "WHERE t_loan.id_type_loan = t_type_loan.id_type_loan " +
                                "AND t_loan.id_advisor = t_advisor.id_advisor " +
                                "AND t_advisor.id_agency = " + object + " " +
                                "GROUP BY t_type_loan.wording;";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        returnDatasetPieChart = new datasetPieChart();
                        if(response != null)    {
                            while (response.next())  {
                                returnDatasetPieChart.addToCollect(response.getString("name"), response.getInt("value"));
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - " + response.getRow() + " line");
                        } else  {
                            returnDatasetPieChart.addToCollect("ERROR", 1);
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }
                        out.println(gsonSerial.serializedatasetPieChart(returnDatasetPieChart));
                        out.flush();
                        break;
                    case "LoanPerTypeByYear":
                        /*
                        request = "SELECT COUNT(t_loan.id_loan) AS value, t_type_loan.wording AS column, t_advisor.login AS row " +
                                "FROM t_loan, t_type_loan, t_advisor " +
                                "WHERE t_loan.id_type_loan = t_type_loan.id_type_loan " +
                                "AND t_loan.id_advisor = t_advisor.id_advisor " +
                                "AND t_advisor.id_agency = " + object + " " +
                                "GROUP BY t_type_loan.wording, t_advisor.login;";
                        */
                        request = "SELECT ((((t_loan.amount*(t_type_loan.rate/100)*(POW((1+t_type_loan.rate/100),t_loan.length_loan)))/(POW((1+t_type_loan.rate/100),t_loan.length_loan)-1))*t_loan.length_loan)-t_loan.amount) AS value, extract(year from t_loan.entry) AS column, t_type_loan.wording AS row " +
                                "FROM t_loan, t_type_loan, t_advisor " +
                                "WHERE t_loan.id_type_loan = t_type_loan.id_type_loan " +
                                "AND t_advisor.id_advisor = t_loan.id_advisor " +
                                "AND t_advisor.id_agency = " + object + " " +
                                "AND (extract(year from t_loan.entry)) > (extract(year from NOW()) - 10) " +
                                "GROUP BY extract(year from t_loan.entry), t_type_loan.wording, t_type_loan.rate, t_loan.amount, t_loan.length_loan " +
                                "ORDER BY extract(year from t_loan.entry);";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        returnDatasetBarChart = new datasetBarChart();
                        if(response != null)    {
                            while (response.next())  {

                                if(response.getString("row").equals("Immobilier"))   {
                                    returnDatasetBarChart.addToCollect(response.getDouble("value")/100, response.getString("row"), response.getString("column"));
                                }else   {
                                    returnDatasetBarChart.addToCollect(response.getDouble("value"), response.getString("row"), response.getString("column"));
                                }

                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - " + response.getRow() + " line");
                        } else  {
                            returnDatasetBarChart.addToCollect(1, "ERROR", "ERROR");
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }
                        out.println(gsonSerial.serializedatasetBarChart(returnDatasetBarChart));
                        out.flush();
                        break;
                    case "LoanPerAdvisor":
                        /*
                        request = "SELECT t_advisor.login AS name, COUNT(t_loan.id_loan) AS value " +
                                "FROM t_advisor, t_loan " +
                                "WHERE t_advisor.id_advisor = t_loan.id_advisor " +
                                "AND t_advisor.id_agency = " + object + " " +
                                "GROUP BY t_advisor.login;";
                        */
                        request = "SELECT t_advisor.login AS name, SUM((((t_loan.amount*(t_type_loan.rate/100)*(POW((1+(t_type_loan.rate/100)),t_loan.length_loan)))/(POW((1+(t_type_loan.rate/100)),t_loan.length_loan)-1))*t_loan.length_loan)-t_loan.amount) AS value " +
                                "FROM t_advisor, t_loan, t_type_loan " +
                                "WHERE t_advisor.id_advisor = t_loan.id_advisor " +
                                "AND t_type_loan.id_type_loan = t_loan.id_type_loan " +
                                "AND t_advisor.id_agency = " + object + " " +
                                "GROUP BY t_advisor.login, t_loan.length_loan;";

                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        returnDatasetPieChart = new datasetPieChart();
                        if(response != null)    {
                            while (response.next())  {
                                returnDatasetPieChart.addToCollect(response.getString("name"), response.getInt("value"));
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - " + response.getRow() + " line");
                        } else  {
                            returnDatasetPieChart.addToCollect("ERROR", 1);
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get statistic of loan per type - error");
                        }
                        out.println(gsonSerial.serializedatasetPieChart(returnDatasetPieChart));
                        out.flush();
                        break;
                    case "AdvisorClassement":
                        request = "SELECT first_name AS firstn, last_name AS lastn " +
                                "FROM t_advisor " +
                                "WHERE id_agency = " + object + ";";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);

                        if(response != null)    {
                            while (response.next())  {
                                listString.add(response.getString("firstn") + " " + response.getString("lastn"));
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get the advisor classement - " + response.getRow() + " line");
                        } else  {
                            listString.add("ERROR");
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get the advisor classement - error");
                        }
                        out.println(gsonSerial.serializeArrayList(listString));
                        out.flush();
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "GETCUSTOMERS": //specific select calls for CompareSimulationController
                ArrayList<String[]> objectList = new ArrayList<String[]>();
                switch (typeObject) {
                    //case getCustomers() : list of customers matching the desired name
                    case "Customer":
                        request = "SELECT * " +
                                "FROM t_client " +
                                "WHERE lower(first_name) LIKE lower('%" + object +"%') "+
                                "OR lower(mail) LIKE lower('%" + object +"%') "+
                                "OR lower(last_name) LIKE lower('%"+object+"%');";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)  {
                            //implment customers list
                            while (response.next()){
                                String[] customer = {response.getString("id_client"), response.getString("last_name"), response.getString("first_name"), response.getString("mail"), response.getString("salary")};
                                objectList.add(customer);
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get customers GETCUSTOMERS - " + response.getRow() + " line");
                        } else  {
                            //no results found
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get customers GETCUSTOMERS - error");
                        }
                        //serialize the list of customer array (list<String[]>)
                        out.println(gsonSerial.serializeListArray(objectList));
                        out.flush();
                    break;
                    case "Simulation" :
                    //case getSimulation() : retuns list of simulation for desired customer and loan type
                        String tmp = splitedQuery[3];
                        String type="";

                        //initializing types ids (should be from a select on t_loan_type table)
                        switch (tmp){
                            case "AUTOMOBILE" :
                                type = "1";
                            break;
                            case "CONSOMMATION" :
                                type = "3";
                            break;
                            case "IMMOBILIER" :
                                type = "2";
                            break;
                        }
                        request = "SELECT * " +
                                "FROM t_loan_simulation " +
                                "WHERE id_client ="+object+
                                " AND id_type_loan ="+type+
                                " ORDER BY entry ASC;";
                        String request2 = "SELECT rate FROM t_type_loan WHERE id_type_loan="+type;
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        if(response != null)  {
                            //implement simulations list
                            while (response.next()){
                                String[] sim = {response.getString("id_loan"), response.getString("entry"), response.getString("wording"),response.getString("amount"),response.getString("length_loan")};
                                objectList.add(sim);
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get simulation GETCUSTOMERS - " + response.getRow() + " line");
                        } else  {
                            //no results found
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get simulation GETCUSTOMERS - error");
                        }
                        response = Server.connectionPool[poolIndex].requestWithResult(request2);
                        if(response != null)  {
                            //add the rate at the end of the list to avoid sending two different objects
                            while (response.next()){
                                String[] sim = {response.getString("rate")};
                                objectList.add(sim);
                            }
                            response.last();
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get rate GETCUSTOMERS - " + response.getRow() + " line");
                        } else  {
                            //if the rate isn't found
                            listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - get rate GETCUSTOMERS - error");
                        }

                        out.println(gsonSerial.serializeListArray(objectList));
                        out.flush();
                    break;
                }
                break;
           case "SPECIF_3": //Spécifique RUBEN
                switch (typeObject) {
                    case "getAllLoanByCustomer":
                        request = "SELECT t_client.id_client,"
                                + " t_client.last_name,"
                                + " t_client.first_name,"
                                + " t_client.mail,"
                                + " t_type_loan.id_type_loan,"
                                + " t_type_loan.wording,"
                                + " t_type_loan.rate,"
                                + " t_loan_simulation.id_loan,"
                                + " t_loan_simulation.amount,"
                                + " t_loan_simulation.length_loan"
                                + " FROM t_loan_simulation,"
                                + " t_type_loan, "
                                + "t_client "
                                + "WHERE "
                                + "t_client.id_client= t_loan_simulation.id_client "
                                + " AND t_type_loan.id_type_loan =  t_loan_simulation.id_type_loan "
                                + " AND t_loan_simulation.id_loan=" + object + ";";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);
                        response.next();

                        listener.changeTextLog("Nom: " + response.getString("last_name"));
                        SpecifRuben tmp = new SpecifRuben(response.getInt("id_client"),
                        response.getString("last_name") ,
                        response.getString("first_name"),
                        response.getString("mail"),
                        response.getInt("id_type_loan"),
                        response.getString("wording"),
                        response.getDouble("rate"),
                        response.getInt("id_loan"),
                        response.getDouble("amount"),
                        response.getInt("length_loan")
                        );

                        //response.getInt("COUNTADRESS");
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - GeteSpecifRuben");
                        out.println(gsonSerial.serializeSpecifRuben(tmp));
                        out.flush();
                        //Coding
                        break;
                    default:
                        //Coding
                        break;
                }
                break;
            case "FIXEDRATE": // fixed rate loan simulation case
                String sqlQuery;
                ResultSet rs;
                switch (typeObject) {
                    // case : customers list
                    case "GetCustomers" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting customers");

                        String nom = object;
                        ArrayList<Customer> customersList = new ArrayList<>();

                        // get the loan types
                        sqlQuery = "SELECT id_client, last_name, first_name, mail "
                                + "FROM t_client "
                                + "WHERE first_name ilike '" + nom.replaceAll("'","''") + "%' "
                                + "OR last_name ilike '" + nom.replaceAll("'","''") + "%' "
                                + "OR first_name||' '||last_name ilike '" + nom.replaceAll("'","''") + "%' "
                                + "OR last_name|| ' '||first_name ilike '" + nom.replaceAll("'","''") + "%';";
                        rs = Server.connectionPool[poolIndex].requestWithResult(sqlQuery);

                        // fill an ArrayList with the insurances data taken from the database
                        while (rs.next()) {
                            Customer c = new Customer();
                            c.setId(rs.getInt("id_client"));
                            c.setLastName(rs.getString("last_name"));
                            c.setFirstName(rs.getString("first_name"));
                            c.setMail(rs.getString("mail"));
                            customersList.add(c);
                        }

                        // serialize and send the ArrayList to the client
                        out.println("SUCCESS/" + gsonSerial.serializeArrayList(customersList));
                        out.flush();

                        break;

                    // case : get loan types list
                    case "GetLoanTypes" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting loan types");

                        ArrayList<LoanType> loanTypesList = new ArrayList<>();

                        // get the loan types
                        sqlQuery = "SELECT id_type_loan, wording, rate, length_min, length_max, amount_min, amount_max "
                                + "FROM t_type_loan;";
                        rs = Server.connectionPool[poolIndex].requestWithResult(sqlQuery);

                        // fill an ArrayList with the insurances data taken from the database
                        while (rs.next()) {
                            loanTypesList.add(new LoanType(rs.getInt("id_type_loan"), rs.getString("wording"), rs.getDouble("rate"), rs.getInt("length_min"), rs.getInt("length_max"), rs.getInt("amount_min"), rs.getInt("amount_max")));
                        }

                        // serialize and send the ArrayList to the client
                        out.println("SUCCESS/" + gsonSerial.serializeArrayList(loanTypesList));
                        out.flush();

                        break;

                    // case : get the details of an insurance
                    case "GetInsurances" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting insurances");

                        String loanType = object;
                        ArrayList<Insurance> insurancesList = new ArrayList<>();

                        // get the insurances for the given loan type
                        sqlQuery = "SELECT id_insurance, i.id_type_loan, i.rate, i.wording "
                                + "FROM t_insurance i, t_type_loan t "
                                + "WHERE i.id_type_loan = t.id_type_loan "
                                + "AND i.id_type_loan = " + loanType + ";";
                        rs = Server.connectionPool[poolIndex].requestWithResult(sqlQuery);

                        // fill an ArrayList with the insurances data taken from the database
                        while (rs.next()) {
                            insurancesList.add(new Insurance(rs.getInt("id_insurance"), rs.getInt("id_type_loan"), rs.getDouble("rate"), rs.getString("wording")));
                        }

                        // serialize and send the ArrayList to the client
                        out.println("SUCCESS/" + gsonSerial.serializeArrayList(insurancesList));
                        out.flush();

                        break;

                    // case : simulate a fixed rate loan
                    case "CalculateLoan" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting fixed rate loan simulation");

                        // get the loan simulation data from the client
                        FixedRateSimulation frs = gsonSerial.unserializeFixedRateSimulation(object);

                        //System.out.println("montant pret : " + frs.getAmount());
                        //System.out.println("duree pret : " + frs.getDuration());
                        //System.out.println("taux assurance : " + frs.getInsurance().getRate());
                        //System.out.println("taux intérêt : " + frs.getInterestRate());

                        // calculate loan monthly payment
                        FixedRateSimulationControllerServer c = new FixedRateSimulationControllerServer(frs);
                        c.calculateFixedRateLoan();

                        // serialize and send the fixed rate loan simulation to the client
                        out.println("SUCCESS/" + gsonSerial.serializeFixedRateSimulation(frs));
                        out.flush();

                        break;
                        
                    // case : save loan simulation
                    case "SaveLoan" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - saving fixed rate loan simulation");
                        
                        String rep;
                        
                        // get the loan simulation data from the client
                        FixedRateSimulation frs1 = gsonSerial.unserializeFixedRateSimulation(object);
                        
                        // insert the loan simulation
                        sqlQuery = "INSERT INTO t_loan_simulation(wording, amount, length_loan, type_length_loan, type_rate_loan, id_type_loan, id_client, id_advisor, entry, id_insurance, rate, monthly_payment) VALUES ("
                                + "'" + frs1.getWording().replaceAll("'","''") + "',"
                                + frs1.getAmount() + ","
                                + frs1.getDuration() + ","
                                + "'M',"
                                + "'FIXE'," +
                                + frs1.getLoanType().getId() + ","
                                + frs1.getCustomer().getId() + ","
                                + user.getId() + ","
                                + "current_date,"
                                + frs1.getInsurance().getInsuranceId() + ","
                                + frs1.getInterestRate() + ","
                                + frs1.getMonthlyPayment()
                                + ");";
                        rep = Server.connectionPool[poolIndex].requestWithoutResult(sqlQuery);
                        System.out.println("query");
                        System.out.println(sqlQuery);
                        
                        if (rep == "success") {
                            // serialize and send the fixed rate loan simulation to the client
                            out.println("SUCCESS/.");
                        }
                        else {
                            out.println("FAILURE/.");
                        }
                        out.flush();
                        
                        break;
                    
                    // case : get customer simulations list
                    case "GetSimulations" :
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - requesting customer simulations");

                        ArrayList<FixedRateSimulation> simulationsList = new ArrayList<>();
                        FixedRateSimulation frs2;
                        Insurance ins;

                        // get the customer's loans
                        sqlQuery = "select id_loan, s.wording as swording, amount, length_loan, entry, s.id_insurance as sid_insurance, monthly_payment, s.rate as srate, i.rate as irate, i.wording as iwording, t.id_type_loan as tid_type_loan, t.rate as trate, t.wording as twording, length_min, length_max, amount_min, amount_max "
                        + "from t_loan_simulation s, t_insurance i, t_type_loan t "
                        + "where i.id_insurance = s.id_insurance "
                        + "and s.id_type_loan = t.id_type_loan "
                        + "and id_client=" + object + " "
                        + "and s.id_type_loan=" + splitedQuery[3] + ";";
                        rs = Server.connectionPool[poolIndex].requestWithResult(sqlQuery);
                        System.out.println("query :");
                        System.out.println(sqlQuery);

                        // fill an ArrayList with the insurances data taken from the database
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        while (rs.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {
                                if (i > 1) System.out.print(",  ");
                                String columnValue = rs.getString(i);
                                System.out.print(columnValue + " " + rsmd.getColumnName(i));
                            }
                            System.out.println("");
                            
                            frs2 = new FixedRateSimulation();
                            frs2.setId(rs.getInt("id_loan"));
                            frs2.setWording(rs.getString("swording"));
                            frs2.setAmount(rs.getInt("amount"));
                            frs2.setDuration(rs.getInt("length_loan"));
                            frs2.setInterestRate(rs.getDouble("srate"));
                            frs2.setMonthlyPayment(rs.getDouble("monthly_payment"));
                            
                            LoanType lt = new LoanType(rs.getInt("tid_type_loan"), rs.getString("twording"), rs.getDouble("trate"), rs.getInt("length_min"), rs.getInt("length_max"), rs.getInt("amount_min"), rs.getInt("amount_max"));
                            frs2.setLoanType(lt);
                            
                            ins = new Insurance();
                            ins.setId(rs.getInt("sid_insurance"));
                            ins.setRate(rs.getDouble("irate"));
                            ins.setWording(rs.getString("iwording"));
                            frs2.setInsurance(ins);
                            
                            frs2.setOwedAmount(frs2.getDuration() * frs2.getMonthlyPayment());
                            frs2.setTotalRate(frs2.getInterestRate() + frs2.getInsurance().getRate());
                            
                            simulationsList.add(frs2);
                        }
                        
                        System.out.println("fr :");
                        for (FixedRateSimulation fr : simulationsList) {
                            System.out.println(fr.getId() + " " + fr.getInterestRate() + " "+ fr.getWording() + " " + fr.getInsurance().getInsuranceId() + " " + fr.getInsurance().getWording());
                            System.out.println("base rate : " + fr.getLoanType().getRate());
                        }

                        // serialize and send the ArrayList to the client
                        out.println("SUCCESS/" + gsonSerial.serializeArrayList(simulationsList));
                        out.flush();
                        
                        break;
                        
                    // case : malformed query
                    default:
                        listener.changeTextLog("COMMUNICATE - " + user.getLogin() + " - bad query");
                        out.println("FAILURE/.");
                        out.flush();
                        break;
                }
                break;
            case "SPECIF_5": //Spécifique MARIAM
                switch (typeObject) {
                    case "Address":
                        //Coding
                    default:
                        //Coding
                        break;
                }
                break;
            case "SPECIF_6": //Spécifique LINDA
                switch (typeObject) {
                    case "toto":
                    	request = "SELECT login FROM t_client WHERE id_client = " + object + ";";
                        response = Server.connectionPool[poolIndex].requestWithResult(request);

                        response.next();
                        listener.changeTextLog("LINDA INFO - nom=" + response.getString("last_name"));

                        out.println(response.getString("last_name"));
                        out.flush();
                    	break;
                    	//Coding
                    default:
                        //Coding
                        break;
                }
                break;
            default:
                //coding
                break;
        }


      } catch (Exception e) {
        listener.changeTextLog("CONNECT_WARNING - " + user.getLogin() + " - gone");
        if (poolIndex != -1){
          Server.connectionPool[poolIndex].use(false);
          listener.updateInfoLabel();
        }
        try {
          socket.close();
        } catch (IOException e1) {
          listener.changeTextLog("SERVER_WARNING - closing socket error");
        }
      }
    }//end of while loop
  }

  public boolean getConnection(String login, String pwd){
    boolean userConnected = false;
    boolean userAuth = false;
    boolean userAlreadyUse = false;
    boolean userNoPool = true;

    userAuth=authentication(login, pwd);
    if(userAuth == false)   {
        out.println("Error/Authentification incorrecte");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - dismissed");
        return false;
    }

    user.getAdvisor();
    if(user.getError() == true)   {
        out.println("Error/Erreur de récupération des informations");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - dismissed");
        return false;
    }

    for (int i = 0; i < Server.poolSize; i++) {
        if (Server.connectionPool[i].getUser().equals(login)) {
            out.println("Error/Cet utilisateur est déja connecté (ressayer ultérieurement)");
            out.flush();
            listener.changeTextLog("CONNECT_WARNING - " + login + " - already connect");
            userAlreadyUse = true;
            return false;
        }
    }

    for (int i = 0; i < Server.poolSize; i++) {
        if (Server.connectionPool[i].isUsed() == false) {
            Server.connectionPool[i].use(true);
            Server.connectionPool[i].setUser(login);
            listener.updateInfoLabel();
            poolIndex = i;
            out.println("Success/" + gsonSerial.serializeUser(user));
            out.flush();
            listener.changeTextLog("CONNECT_WARNING - " + login + " - connected");
            userNoPool = false;
            userConnected = true;
            return true;
        }
    }

    if (userNoPool == true) {
        out.println("Error/Aucune connexion disponible (ressayer ultérieurement)");
        out.flush();
        listener.changeTextLog("CONNECT_WARNING - " + login + " - no connection available");
        return false;
    }

    return userConnected;
  }

  private boolean authentication(String login, String pass) {
    boolean authentic = false;
    Connection conn = null;
    Statement stat = null;
    ResultSet results = null;

    try {
      conn = Server.getConnection();
      stat = conn.createStatement();
      results = stat.executeQuery("SELECT * FROM t_advisor WHERE login = '" + login + "' AND password = '" + pass + "';");

      results.next();

      if(results.getRow() != 0)
        authentic = true;

      results.close();
      stat.close();
      conn.close();
    }
    catch (SQLException e) {
      listener.changeTextLog("ERREUR (SQL) pour l'authentification de " + login);
      authentic = false;
    }

    return authentic;
  }
}
