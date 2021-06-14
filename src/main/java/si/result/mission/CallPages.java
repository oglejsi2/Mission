/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.result.mission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Callable class executed by ExecuterCallPages
 * @author Robert
 */
public class CallPages implements Callable {
    
        String glUrl; 
        long glCallCounter;

        public String getGlUrl() {
            return glUrl;
        }

        public void setGlUrl(String glUrl) {
            this.glUrl = glUrl;
        }
        


        public long getGlCallCounter() {
            return glCallCounter;
        }

        public void setGlCallCounter(long glCallCounter) {
            this.glCallCounter = glCallCounter;
        }
        



        
        
        
        CallPages (String pUrl, long pCallCounter) {
            glUrl=pUrl;
            glCallCounter=pCallCounter;
        }
    
        
        /**
        * Actual commands executed by ExecutorCallPages
        * 
        * 
        */            
        
        @Override
        public Object call() throws Exception {  
            
            String pParameters = null;
            String locTitle;       
            Naloga locNalogaErr = null;
            try {
                Naloga locContent = this.sendGet(glUrl, pParameters, 0, 0);
                
                locTitle=this.getTitle(locContent.getContent());
                Naloga locNaloga = new Naloga(glCallCounter, locTitle, locContent.getUrl(), 0);

                Thread.sleep(1000); 
                
                

                return locNaloga;
            } catch (Exception ex) {
                locNalogaErr = new Naloga(glCallCounter, "Error occured", glUrl, 1);
                Logger.getLogger(CallPages.class.getName()).log(Level.INFO, null, ex);
                   
            }
            return locNalogaErr;
        }


        
       private static class DefaultTrustManager implements X509TrustManager {

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        } 

	public  Naloga sendGet(String pUrl, String pParameters, int pConnectTimeout, int pReadTimeout ) {
            String locReturn = "";            
            String connType="";
            
            try {
                String url;
                if ((pParameters==null || pParameters.isEmpty()  )) {
                    url = pUrl;                                    
                } else {
                    url = pUrl +  "?par="+pParameters ;
                }
		
                
/*
                              URLConnection urlConnection = new URL(url).openConnection();                
                URL urlObj = new URL(url);  
*/                
                URL urlObj = new URL(url);
                
                
                        urlObj = new URL(url);
                        SSLContext ctx = SSLContext.getInstance("TLS");
                        ctx.init(new KeyManager[0], new TrustManager[] {new CallPages.DefaultTrustManager()}, new SecureRandom());
                        SSLContext.setDefault(ctx);                

                        try {
                            HttpsURLConnection con = (HttpsURLConnection) urlObj.openConnection();
                            connType="https";
                            con.setHostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String arg0, SSLSession arg1) {
                                    return true;
                                }
                            });                             
                        con.setConnectTimeout(pConnectTimeout);
                        con.setReadTimeout(pReadTimeout);     
                        // optional default is GET
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Accept-Charset", "UTF-8");

                        int responseCode = con.getResponseCode();
                        
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream(), "UTF-8"));
                        
                            String inputLine;   

                            StringBuffer response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();

                            //print result

                            locReturn=responseCode + ":" + response.toString();                
                            if (locReturn.length()>2000) {
                                locReturn=locReturn.substring(0, 1999);
                            }                                                              

                            
                        } catch (Exception ex)  {
                            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
                            connType="http";
                            con.setConnectTimeout(pConnectTimeout);
                            con.setReadTimeout(pReadTimeout);
                            // optional default is GET
                            con.setRequestMethod("GET");

                            int responseCode = con.getResponseCode();
                            
                            

                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));                                                     
                            String inputLine;   

                            StringBuffer response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();

                            //print result

                            locReturn=responseCode + ":" + response.toString();                
                            if (locReturn.length()>2000) {
                                locReturn=locReturn.substring(0, 1999);
                            }                                      
                        }
                        
              
               
                

                Naloga locNaloga = new Naloga(0,locReturn,pUrl, 0);

                return locNaloga;
            } catch (MalformedURLException ex) {                
                Logger.getLogger(CallPages.class.getName()).log(Level.SEVERE, null, ex);
                locReturn="-1:MalformedURLException:" +ex.toString();
                if (locReturn.length()>2000) {
                    locReturn=locReturn.substring(0, 1999);
                }                                
                Naloga locNalogaErr1 = new Naloga(glCallCounter, "Error occured", glUrl, 1);
                
                return locNalogaErr1;
            } catch (IOException ex) {
                Logger.getLogger(CallPages.class.getName()).log(Level.SEVERE, null, ex);
                locReturn="-2:IOException:"+ex.toString();
                if (locReturn.length()>2000) {
                    locReturn=locReturn.substring(0, 1999);
                }                
                Naloga locNalogaErr1 = new Naloga(glCallCounter, "Error occured", glUrl, 1);                
                return locNalogaErr1;                             
                
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(CallPages.class.getName()).log(Level.SEVERE, null, ex);
            } catch (KeyManagementException ex) {
                Logger.getLogger(CallPages.class.getName()).log(Level.SEVERE, null, ex);
            }
            Naloga locNalogaErr1 = new Naloga(glCallCounter, "Error occured", glUrl, 1);                
            return locNalogaErr1;
	}     


    /**
    * Extracts titel from page
    * 
    * 
    */                    
        
        
    public String getTitle(String pHtml) {
        String locTitle="";
        int startTitle=pHtml.indexOf("<title>");
        int stopTitle=pHtml.indexOf("</title>");
        locTitle=pHtml.substring(startTitle, stopTitle);
        locTitle=locTitle.replace("&ndash; ", "");
        locTitle=locTitle.replace("<title>", "");
        locTitle=locTitle.replace("RESULT", "");            

        
        return locTitle;
    }
    
    public static void main (String[] args) {
        CallPages locCallPages = new CallPages("https://www.result.si/projekti/",-1);        
        try {
            locCallPages.call();
        } catch (Exception ex) {
            Logger.getLogger(CallPages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
