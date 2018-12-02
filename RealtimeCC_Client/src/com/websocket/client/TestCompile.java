package com.websocket.client;



import com.google.gson.Gson;
import com.hackerearth.heapi.sdk.client.HackerEarthAPI;
import com.hackerearth.heapi.sdk.options.CompileOptions;
import com.hackerearth.heapi.sdk.options.RunOptions;
import com.hackerearth.heapi.sdk.options.SupportedLanguages;
import com.hackerearth.heapi.sdk.responses.CompileResponse;
import com.hackerearth.heapi.sdk.responses.RunResponse;
//import org.junit.Assert;
//import org.junit.Test;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class TestCompile {

    private final String clientSecret = "f2e268a215afb28ac849aa1f30ea692e5e504536";

    //private final String testSource = getStringResource("C:/Users/panka/Documents/dev/apitest.cpp");
    private final String testSource=DashboardUI.textArea.getText();
   // @Test
    public void TestOptions(){
     //   Assert.assertNotNull(testSource);
        CompileOptions options = new CompileOptions(testSource, SupportedLanguages.CPP);
        String jsonOptions = options.getJson();
     //   Assert.assertNotNull(jsonOptions);
    }

   // @Test
    public RunResponse TestRunRequest() {
       // Assert.assertNotNull(testSource);
        HackerEarthAPI apiHandle = new HackerEarthAPI(clientSecret);
        RunOptions options = new RunOptions(testSource, SupportedLanguages.CPP);
        options.setInput(DashboardUI.userInputArea.getText());
        RunResponse response = apiHandle.Run(options);
        //Gson gson = new Gson();
       // System.out.println(gson.toJson(response, RunResponse.class));
        return response;
       // Assert.assertNotNull(response);
      
      //  System.out.println(response.getCompileStatus());
    }

    /*
    *
    * Takes filename as input and returns file Contents as String.
    * @param FileName String
    * @return String file contents
    *
    */
    private String getStringResource(String fileName) {
    	 File f = null;
         URI uri;
         boolean bool = false;
        try {
           File file = new File(getClass().getResource(fileName).toURI());
            bool = f.exists();
           
           // if file exists
           if(bool) {
           
              // returns the uri string
              uri = f.toURI();
              
              // print
              System.out.println("uri: "+uri);
           }
           else
        	   System.out.println("yo");
           
            FileInputStream fis = new FileInputStream(file);
         
           
            byte[] data = new byte[(int) file.length()];
           fis.read(data);
            
  
            fis.close();
            String str = new String(data);
           return str;
        }
        catch(Exception e){
        	//System.out.println("hahahah");
            e.printStackTrace();
            return null;
        }
    }

   // @Test
    public CompileResponse TestCompileRequest(){
     //   Assert.assertNotNull(testSource);
        HackerEarthAPI apiHandle = new HackerEarthAPI(clientSecret);
        CompileOptions options = new CompileOptions(testSource, SupportedLanguages.CPP);
        CompileResponse response = apiHandle.Compile(options);
        return response;
      //  Assert.assertNotNull(response);
        //Gson gson = new Gson();
       // System.out.println(gson.toJson(response, CompileResponse.class));
    }
}