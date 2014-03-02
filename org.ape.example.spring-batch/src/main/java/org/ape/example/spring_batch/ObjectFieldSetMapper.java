package org.ape.example.spring_batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ape.example.spring_batch.domain.TOObject;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ObjectFieldSetMapper implements FieldSetMapper<TOObject>{
	private static Log log = LogFactory.getLog(ObjectFieldSetMapper.class);
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
	
	@Override
	public TOObject mapFieldSet(FieldSet fieldSet) throws BindException {
 
		Date validFrom = null;
		Date validTo = null;
		
		try {
			validFrom = formatter.parse(fieldSet.readString(3));
			validTo = formatter.parse(fieldSet.readString(4));
		} catch (ParseException e) {
			log.error("Could not parse validFrom and validTo of TOObject", e);
		}
		
		
		TOObject object = new TOObject();
		object.setId(fieldSet.readInt(0));
		object.setName(fieldSet.readString(1));
		object.setDescription(fieldSet.readString(2));
		object.setValidFrom(validFrom);
		object.setValidTo(validTo);		
		return object;
 
	}
}
