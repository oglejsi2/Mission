/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.result.mission;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 * Initializes executor.
 * Stores requests and responses
 */
public class ExecutorCallPages {
    
        
        int cntCalls;
        int cntErr;
        
        ArrayList<Naloga> nalogaArray = new ArrayList<Naloga>();

        public ArrayList<Naloga> getNalogaArray() {
            return nalogaArray;
        }

        public int getCntErr() {
            return cntErr;
        }
        

        
       
        
        
    /**
    * Executor initializes CallPages class. CallPages calls url's  
    * @param  concur        specifies how many concurent calls will executor trigger
    * @param  callCounter   stores sequence value of call
    */        
    ExecutorCallPages(int concur, int callCounter) {
        
        if (concur>=1 && concur<=4 ) {
                Naloga result = null;
                CallPages locConcurrent = new CallPages("https://www.result.si/projekti/", callCounter);       
                //CallPages locConcurrent = new CallPages("https://zblj/", callCounter);  
                CallPages locConcurrent1 = new CallPages("https://www.result.si/o-nas/",callCounter);       
                CallPages locConcurrent2 = new CallPages("https://www.result.si/kariera/",callCounter);
                CallPages locConcurrent3 = new CallPages("https://www.result.si/blog/",callCounter);
                ExecutorService locexecutor = initializeExecutor(concur);

                Future<Naloga> future = locexecutor.submit(locConcurrent);
                Future<Naloga> future1 = locexecutor.submit(locConcurrent1);
                Future<Naloga> future2 = locexecutor.submit(locConcurrent2);
                Future<Naloga> future3 = locexecutor.submit(locConcurrent3);

                locexecutor.shutdown();
                while (!locexecutor.isTerminated()) {      

                }


                try {
                     result = future.get();
                     cntErr=cntErr+result.getIsError();
                     nalogaArray.add(result);

                     result = future1.get();
                     cntErr=cntErr+result.getIsError();
                     nalogaArray.add(result); 

                     result = future2.get();
                     cntErr=cntErr+result.getIsError();
                     nalogaArray.add(result);  

                     result = future3.get();
                     cntErr=cntErr+result.getIsError();
                     nalogaArray.add(result);              

                } catch (InterruptedException ex) {
                    Logger.getLogger(ExecutorCallPages.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(ExecutorCallPages.class.getName()).log(Level.SEVERE, null, ex);
                }            
        } else {
                 Naloga locNalogaErr1 = new Naloga(1, "Parameter must be between 1 and 4", "", 1);
                nalogaArray.add(locNalogaErr1);                          
        }
         


        
        


             

    }
    

    public ExecutorService initializeExecutor(int pPoolSize ) {
        ExecutorService executor = Executors.newFixedThreadPool(pPoolSize);
        return executor; 
    }       
    

}
