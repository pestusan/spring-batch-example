package org.ape.example.spring_batch;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ape.example.spring_batch.domain.TOObject;
import org.springframework.batch.item.ItemProcessor;

public class TOObjectItemProcessor implements ItemProcessor<TOObject, TOObject>{

	private static Log log = LogFactory.getLog(TOObjectItemProcessor.class);
	
	public TOObject process(TOObject object) throws Exception {

		log.info("processing... " + object);
			
		if(checkCondition1(object)) {
			return object;
		}
		
		return null;
	}
	
	/**
	 * Checks condition if period is valid today
	 * 
	 * @param object
	 * @return
	 */
	private boolean checkCondition1(TOObject object) {
		Date now = new Date();
		boolean result = object.getValidFrom().before(now) && object.getValidTo().after(now);
		log.info("Condition successful: "+result);
		return result;
	}

}
