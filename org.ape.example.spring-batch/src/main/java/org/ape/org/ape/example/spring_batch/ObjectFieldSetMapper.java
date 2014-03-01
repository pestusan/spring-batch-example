package org.ape.org.ape.example.spring_batch;

import org.ape.org.ape.example.spring_batch.domain.TOObject;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ObjectFieldSetMapper implements FieldSetMapper<TOObject>{

	@Override
	public TOObject mapFieldSet(FieldSet fieldSet) throws BindException {
 
		TOObject report = new TOObject();
		report.setId(fieldSet.readInt(0));
		report.setName(fieldSet.readString(1));
		report.setDescription(fieldSet.readString(2));
		return report;
 
	}
}
