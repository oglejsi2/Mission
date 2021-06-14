/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.result.mission;

/**
 *
 * @author Robert
 * This class is used to store statistics for all calls
 */
public class Statistics {
    long cntCalls;
    long cntSuccess;
    long cntErrors;
    
    public Statistics (long pCalls, long pSuccess, long pErrors) {
        this.cntCalls=pCalls;
        this.cntSuccess=pSuccess;
        this.cntErrors = pErrors;
    }

    public long getCntCalls() {
        return cntCalls;
    }

    public void setCntCalls(long cntCalls) {
        this.cntCalls = cntCalls;
    }

    public long getCntSuccess() {
        return cntSuccess;
    }

    public void setCntSuccess(long cntSuccess) {
        this.cntSuccess = cntSuccess;
    }

    public long getCntErrors() {
        return cntErrors;
    }

    public void setCntErrors(long cntErrors) {
        this.cntErrors = cntErrors;
    }
    
    
}
