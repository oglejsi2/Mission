/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.result.mission;

/**
 *
 * @author Robert
 * 
 * Rest conroller called exposes functions to the world
 */
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Api(value = "/NalogaController", description = "This is basic controller for MissionApplication")
public class MisionController {

	private static final AtomicLong counter = new AtomicLong();

        int cnt=0;
        
        public int cntProc() {
            return cnt=cnt+1;
        }                
        
        public  ArrayList<Naloga> nalogaArrayAll = new ArrayList<Naloga>(); 
        
        public Statistics statistics = new Statistics(0,0,0);
        

	@GetMapping("/mission") 
        @ApiOperation(value = "Calls four pages and retrieves titles in json format",
            notes = "Calls four pages and retrieves titles in json format",
            response = MisionController.class,
            responseContainer = "List")        
        public ResponseEntity<ExecutorCallPages> mission(@ApiParam(value = "number of concurent sessions") @RequestParam @Max(4) @Min(1)  Integer concur) {
            System.out.println("ExecutorCallPages ");
            ExecutorCallPages locExecutorCallPagees = new ExecutorCallPages(concur, cntProc());
            statistics.setCntCalls(statistics.getCntCalls()+4);
            statistics.setCntErrors(statistics.getCntErrors() + locExecutorCallPagees.getCntErr());
            statistics.setCntSuccess(statistics.getCntSuccess() + 4 - locExecutorCallPagees.getCntErr());
            for (int i=0;  i<locExecutorCallPagees.getNalogaArray().size(); i++) {
                nalogaArrayAll.add(locExecutorCallPagees.getNalogaArray().get(i));
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(locExecutorCallPagees);  
               
            

        }
        
            
	@GetMapping("/mission/showall") 
        @ApiOperation(value = "Lists response from all calls made so far",
            notes = "Lists response from all calls made so far",
            response = MisionController.class,
            responseContainer = "List")         
            public ArrayList<Naloga> showall() {

            return nalogaArrayAll;
	}      
            
	@GetMapping("/mission/showstats") 
        @ApiOperation(value = "Show summary statistics for calls made so far",
                   notes = "Show summary statistics for calls made so far ",
                   response = MisionController.class,
                   responseContainer = "List")          
            public Statistics showstats() {

            return statistics;
	}               
            

}