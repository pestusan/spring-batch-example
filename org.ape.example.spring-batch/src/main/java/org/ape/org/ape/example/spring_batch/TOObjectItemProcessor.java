package org.ape.org.ape.example.spring_batch;

import org.ape.org.ape.example.spring_batch.domain.TOObject;
import org.springframework.batch.item.ItemProcessor;

public class TOObjectItemProcessor implements ItemProcessor<TOObject, TOObject>{

	public TOObject process(TOObject person) throws Exception {
		if(person.getName().equals("Boehnchen")) {
			return person;
		}
		return null;
	}

}
