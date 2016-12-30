package com.prosnav.ivms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.prosnav.ivms.model.Dictionary;
import com.prosnav.ivms.repository.ivm.DictionaryRepository;
import com.prosnav.ivms.service.DictionaryService;

@Service
public class DictionaryServiceImpl implements DictionaryService {
	@Autowired
	private DictionaryRepository dictionaryRepository;

	@Override
	public List<Dictionary> getAll() {
		return dictionaryRepository.findAll(new Sort(new Order("type"), new Order("order")));
	}

}
