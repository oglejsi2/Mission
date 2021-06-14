/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.result.mission;

import java.util.Date;

/**
 *
 * @author Robert
 * This class is used to store parameters and contents
 */


public class Naloga {

	private final long id;
	private final String content;
        private final String url;
        private int isError;
        private final Date sysdate;

	public Naloga(long pid, String pcontent, String purl, int pisError) {
		this.id = pid;
		this.content = pcontent;
                this.url = purl;
                this.isError=pisError;
                this.sysdate=new Date();
	}
        

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

        public String getUrl() {
            return url;
        }

        public int getIsError() {
            return isError;
        }

        public void setIsError(int isError) {
            this.isError = isError;
        }

        public Date getSysdate() {
            return sysdate;
        }


        
        
        
        
}