package logical;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {




    public static void main(String[] args) throws IOException, URISyntaxException, HttpStatusException {

         Document document = null;
         Elements p = null;
         Scanner userURL = new Scanner(System.in);
         boolean validURL = false;
         String urlInserted = " ";

            //URL Validation

            do {

                System.out.print("Insert a valid URL: ");
                urlInserted = userURL.nextLine();
                validURL = true;

                try{

                    document = Jsoup.connect(urlInserted).get();

                } catch (MalformedURLException | IllegalArgumentException e) {
                    validURL = false;
                }





            }while(!validURL);


            System.out.println("The URL is Valid!\n\n");



            //A
            System.out.println("---------A---------");
            System.out.print("Number of lines in HTML File: " + Jsoup.connect(urlInserted).execute().body().split("\n").length + "\n");

            //B
            System.out.println("\n\n---------B---------");
            p = document.getElementsByTag("p");

            System.out.print("Number of paragraphs in html file: " + p.size() + "\n");

            //C
            System.out.println("\n\n---------C---------");
            System.out.print("Number of total images: " + p.select("img").size() + "\n");
            int j = 0;

        for (Element pa:p) {

            j++;

            if(pa.select("img").size() > 0) {
                System.out.println("In <p> #" + j + " there is: " + pa.select("img").size());
            }

        }

            //D
            System.out.println("\n\n---------D---------");
            int cantPost = 0;
            int cantGet = 0;

           Elements forms = document.getElementsByTag("form");

           System.out.println("Number of total forms: "+ forms.size());

            for (Element forms1 :forms) {

                if(forms1.attr("method").equalsIgnoreCase("POST")){

                    cantPost++;

                } else if (forms1.attr("method").equalsIgnoreCase("GET")){

                    cantGet++;
                }

            }

            System.out.println("Number of total POST methods: "+ cantPost);
            System.out.println("Number of total GET methods: "+ cantGet);

            //E
            System.out.println("\n\n---------E---------");

            int numInput = 0;
            int numForm = 0;

            for (Element form : forms) {

                numForm++;
                System.out.println("Form #" + numForm + "\n");

                for(int i = 0; i < form.getElementsByTag("input").size(); i++) {

                    numInput++;
                    System.out.println("Input #"+(numInput)+ ":\n" + form.getElementsByTag("input").get(i));
                    System.out.println("Type: " + form.getElementsByTag("input").get(i).attr("type") + "\n");

                }
                    numInput = 0;
            }


            //F
             System.out.println("---------F---------");

            int numForm1 = 0;

            for(Element formAux: forms) {

                numForm1++;
                System.out.println("\nForm #" + numForm1 + "\n");

                if (formAux.attr("method").equalsIgnoreCase("POST")) {

                    Connection connection = ((FormElement) formAux).submit();
                    connection.data("Asignatura","Practica1");
                    connection.header("Matricula", "2016-1066");
                    Connection.Response response;
                    System.out.println("Request parameters: " + connection.request().data() + "\n" + "Request headers:\n" + connection.request().headers());
                    response = connection.execute();
                    System.out.println("\nResponse: " + response.statusMessage() + "\n" + "Response body:\n" + response.body());


                }
        }



        System.out.print("Goodbye! :)");


    }



}
